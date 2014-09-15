package uma.caosd.DynamicSpringAOP;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import uma.caosd.errorHandling.DeploymentStatusSingleton;
import uma.caosd.errors.Module;
import uma.caosd.errors.Type;

/**
 * Dynamic URL ClassLoader.
 * It allows the addition of new files to the classpath at runtime, by using reflection.
 * 
 * @author UMA
 *
 */
public class DynamicURLClassLoader {
	
	/**
	 * Adds additional file or path to classpath during runtime.
     * @see #addUrlToClassPath(java.net.URL, ClassLoader)
	 * @param path
	 * @param classloader
	 */
	public static void addFileToClassPath(File path, ClassLoader classloader) {
		try {
			addUrlToClassPath(path.toURI().toURL(), classloader);
		} catch (MalformedURLException e) {
			String desc = "Error adding file to classpath. File: " + path;
			DeploymentStatusSingleton.getStatus().addError(desc, Module.ASPECT_WEAVER, Type.SPRING_AOP_WEAVER);
			
			System.out.println("Error adding file to classpath. File: " + path);
			e.printStackTrace();
		}
	}

	/**
	 * Adds the content pointed by the URL to the classpath during runtime.
     * Uses reflection since <code>addURL</code> method of
     * <code>URLClassLoader</code> is protected.
	 * @param url
	 * @param classloader
	 */
	public static void addUrlToClassPath(URL url, ClassLoader classloader) {
		try {
			invokeDeclared(URLClassLoader.class, classloader, "addURL",
									   new Class[]{URL.class}, new Object[]{url});
		} catch (IllegalAccessException e) {
			String desc = "Error adding url to classpath. URL: " + url + ". IllegalAccessException.";
			DeploymentStatusSingleton.getStatus().addError(desc, Module.ASPECT_WEAVER, Type.SPRING_AOP_WEAVER);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			String desc = "Error adding url to classpath. URL: " + url + ". NoSuchMethodException.";
			DeploymentStatusSingleton.getStatus().addError(desc, Module.ASPECT_WEAVER, Type.SPRING_AOP_WEAVER);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			String desc = "Error adding url to classpath. URL: " + url + ". InvocationTargetException.";
			DeploymentStatusSingleton.getStatus().addError(desc, Module.ASPECT_WEAVER, Type.SPRING_AOP_WEAVER);
			e.printStackTrace();
		}
	}
	
    /**
     * Invokes any method of a class, even private ones.
     *
     * @param c            class to examine
     * @param obj          object to inspect
     * @param method       method to invoke
     * @param paramClasses parameter types
     * @param params       parameters
     */
    public static Object invokeDeclared(Class<?> c, Object obj, String method, Class<?>[] paramClasses, Object[] params) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            Method m = c.getDeclaredMethod(method, paramClasses);
            m.setAccessible(true);
            return m.invoke(obj, params);
    }
}
