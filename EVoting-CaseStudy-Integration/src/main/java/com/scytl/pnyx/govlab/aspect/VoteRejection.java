package com.scytl.pnyx.govlab.aspect;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import uma.caosd.intertrust.aspects.IntertrustAspect;

public class VoteRejection extends IntertrustAspect implements
        MethodInterceptor {
    public static final String VOTE_REJECTION_ADVISOR_ID = "VoteRejection";

    List<Object> blockedIps;

    public void setBlockedIps(final List<Object> blockedIps) {
        this.blockedIps = blockedIps;
    }

    public void addBlockedIp(final String newIp) {
        blockedIps.add(newIp);
    }

    /**
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     * @pointcut void
     *           com.scytl.pnyx.server.PnyxProtocolController.clientCastVote
     *           (HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    public Object invoke(final MethodInvocation i) throws Throwable {

        System.out.println("(VoteRejectionAspect) Pointcut: "
            + i.getMethod());

        Object[] args = i.getArguments();

        HttpServletRequest request = (HttpServletRequest) args[0];
        HttpServletResponse response = (HttpServletResponse) args[1];

        // Warning: it does not take NAT into account
        if (isCastVote(request.getRequestURI())
            && isBlocked(request.getRemoteAddr())) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            try {
                response.sendRedirect("http://www.scytl.com/index.html");
            } finally {
                out.close();
            }
            return null;
        }

        return i.proceed();
    }

    private boolean isCastVote(final String uri) {
        System.out.println("(VoteRejectionAspect) URI: " + uri);

        if (uri.equals("/portal-webapp/client_voting.html")) {
            System.out.println("(VoteRejectionAspect) Checking Vote");
            return true;
        }
        // Testing the aspect
        // return false;
        return true;
    }

    private boolean isBlocked(final String ip) {
        System.out.println("(VoteRejectionAspect) Checking IP:" + ip);

        for (Object blocked : blockedIps) {
            if (ip.equals(blocked)) {
                System.out.println("(VoteRejectionAspect) Vote Rejected");
                return true;
            }
        }

        // return false;
        return true;
    }
}
