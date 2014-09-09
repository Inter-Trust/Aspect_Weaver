package uma.caosd.DynamicSpringAOP;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import uma.caosd.AspectualKnowledge.Advice;
import uma.caosd.AspectualKnowledge.Advisor;
import uma.caosd.AspectualKnowledge.Pointcut;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicAspect;

/**
 * Generate an .xml SpringAOP configuration file for an aspect with the provided configuration.
 *  
 * @author UMA
 * @date   24/01/2014
 *
 */
public class XMLAspectConfigurator {
	// Namespaces
	private static final String XMLNS_VALUE = "http://www.springframework.org/schema/beans";
	private static final String XMLNS_XSI = "xsi";
	private static final String XMLNS_XSI_VALUE = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String XMLNS_AOP = "aop";
	private static final String XMLNS_AOP_VALUE = "http://www.springframework.org/schema/aop";
	
	// Atributtes
	private static final String XSI_SCHEMALOCATION = "schemaLocation";
	private static final String XSI_SCHEMALOCATION_VALUE = "http://www.springframework.org/schema/beans " +
			"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd " +
			"http://www.springframework.org/schema/aop " +
			"http://www.springframework.org/schema/aop/spring-aop-3.0.xsd";
	
	// Elements
	private static final String ROOT_ELEMENT = "beans";
	private static final String BEAN = "bean";
	private static final String ID = "id";
	private static final String CLASS = "class";
	private static final String AOP_CONFIG = "config";
	private static final String AOP_POINTCUT = "pointcut";
	private static final String AOP_ADVISOR = "advisor";
	private static final String EXPRESSION = "expression";
	private static final String ADVICE_REF = "advice-ref";
	private static final String POINTCUT_REF = "pointcut-ref";
	
	private static Namespace xsi;
	private static Namespace aop;
	
	public static void generateConfigXML(DynamicAspect aspect, String filepath) {
		try {
			Element root = generateRootElement();
			Document doc = new Document(root);
			
			generateBeans(doc, aspect);
			Element config = new Element(AOP_CONFIG, aop);
			generatePointcuts(config, aspect);
			generateAdvisors(config, aspect);
			doc.getRootElement().addContent(config);

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(filepath)); // filepath example: "c:\\file.xml"
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private static Element generateRootElement() {
		generateNamespaces();
		Element beans = new Element(ROOT_ELEMENT, XMLNS_VALUE);

		beans.addNamespaceDeclaration(xsi);
		beans.addNamespaceDeclaration(aop);

		beans.setAttribute(XSI_SCHEMALOCATION, XSI_SCHEMALOCATION_VALUE, xsi);
		return beans;
	}
	
	private static void generateNamespaces() {
		xsi = Namespace.getNamespace(XMLNS_XSI, XMLNS_XSI_VALUE);
		aop = Namespace.getNamespace(XMLNS_AOP, XMLNS_AOP_VALUE);
	}
	
	private static void generateBeans(Document doc, DynamicAspect aspect) {
		Advice advice = aspect.getAdvice();
		Element bean = new Element(BEAN, XMLNS_VALUE);
		bean.setAttribute(new Attribute(ID, advice.getId()));
		bean.setAttribute(new Attribute(CLASS, advice.getClassname()));
		doc.getRootElement().addContent(bean);
	}
	
	private static void generatePointcuts(Element config, DynamicAspect aspect) {
		Pointcut pointcut = aspect.getPointcut();
		Element p = new Element(AOP_POINTCUT, aop);
		p.setAttribute(new Attribute(ID, pointcut.getId()));
		p.setAttribute(new Attribute(EXPRESSION, pointcut.getExpression()));
		config.addContent(p);
	}
	
	private static void generateAdvisors(Element config, DynamicAspect aspect) {
		Advisor advisor = aspect.getAdvisor();
		Element a = new Element(AOP_ADVISOR, aop);
		a.setAttribute(new Attribute(ID, advisor.getId()));
		a.setAttribute(new Attribute(POINTCUT_REF, advisor.getPointcutRef()));
		a.setAttribute(new Attribute(ADVICE_REF, advisor.getAdviceRef()));
		config.addContent(a);
	}
}
