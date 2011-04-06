/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;

/**
 *
 * @author Brandao
 */
public class ContextLoader {

    public static final String CONTEXT_CLASS = "context_class";

    private static final ConcurrentHashMap<ClassLoader,WebApplicationContext>
            currentWebApplicationContext;

    static{
        currentWebApplicationContext =
                new ConcurrentHashMap<ClassLoader,WebApplicationContext>();
    }

    public void init( ServletContext servletContext ){

        if( servletContext.getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE ) != null ){
            throw new IllegalStateException(
                            "Cannot initialize context because there is already "
                            + "a root application context present - " +
                            "check whether you have multiple ContextLoader "
                            + "definitions in your web.xml!");
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        WebApplicationContext app =
                createApplicationContext(servletContext);

        app.initApplicationContext(servletContext);

        app.configure();
        
        currentWebApplicationContext
                    .put(classLoader, app);

        
    }

    private WebApplicationContext createApplicationContext(ServletContext servletContext){

        Class clazz = getApplicationContextClass(servletContext);

        if(WebApplicationContext.class.isAssignableFrom(clazz)){
            try{
                WebApplicationContext app = 
                        (WebApplicationContext) clazz.newInstance();


                return app;
            }
            catch( Exception e ){
                throw new BrutosException("unable to create instance: " +
                        clazz.getName(),e);
            }
        }
        else
            throw new BrutosException("web application is not valid:"+
                    clazz.getName());
    }

    private Class getApplicationContextClass(ServletContext servletContext){
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS);

        if( contextClassName != null )
            return this.getContextClass(contextClassName);
        else
            return this.getContextClass(contextClassName);
    }

    private Class getContextClass( String contextClassName ){
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(contextClassName);
        } catch (ClassNotFoundException ex) {
            throw new BrutosException( "Failed to load: " + contextClassName, ex );
        }
    }

    public static WebApplicationContext getCurrentWebApplicationContext(){
        
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();

        if( currentWebApplicationContext.containsKey(classLoader) )
            return currentWebApplicationContext.get(classLoader);
        else
            return null;
    }

    public void destroy( ServletContext servletContext ){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if( currentWebApplicationContext.containsKey(classLoader) ){
            try{
                currentWebApplicationContext.get(classLoader).destroy();
            }
            finally{
                currentWebApplicationContext.remove(classLoader);
            }
        }
    }
}
