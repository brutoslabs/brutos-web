

package org.brandao.brutos.web;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.StringUtil;


public class ContextLoader {

    public static final String CONTEXT_CLASS = "context_class";
    
    public static final String[] APPLICATION_CONTEXT = 
            new String[]{
                "org.brandao.brutos.annotation.web.AnnotationWebApplicationContext",
                "org.brandao.brutos.web.XMLWebApplicationContext"};
    
    private Logger logger;

    private static final ConcurrentHashMap<ClassLoader,WebApplicationContext>
            currentWebApplicationContext;

    static{
        currentWebApplicationContext =
                new ConcurrentHashMap<ClassLoader,WebApplicationContext>();
    }

    public void init( ServletContext servletContext ){

        if( servletContext.getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE ) != null ){
            throw new IllegalStateException(
                    "Multiple ContextLoader definitions has been detected. "
                    + "Check your web.xml!");
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ConfigurableWebApplicationContext app =
                    createApplicationContext(servletContext);
        
        Properties config = app.getConfiguration();
        
        initConfiguration(servletContext, app.getConfiguration());
        initLogger(config);

        logger.info( "Initializing Brutos root WebApplicationContext" );
        
        String configContext = 
                servletContext
                        .getInitParameter(
                                ConfigurableWebApplicationContext.contextConfigName);
        configContext = 
                configContext == null? 
                    ConfigurableWebApplicationContext.defaultConfigContext : 
                    configContext;
        
        String[] contextLocations = 
            StringUtil.getArray(
                    configContext, 
                    BrutosConstants.COMMA);
        
        app.setServletContext(servletContext);
        app.setLocations(contextLocations);
        
        logger.info( "Configuration: " + config.toString() );
        app.flush();
        
        currentWebApplicationContext
                    .put(classLoader, app);

        
    }

    private void initLogger(Properties config){
        LoggerProvider loggerProvider = LoggerProvider.getProvider(config);
        LoggerProvider.setCurrentLoggerProvider(loggerProvider);
        this.logger = loggerProvider.getLogger( ContextLoader.class.getName() );
    }

    @SuppressWarnings("unchecked")
	private void initConfiguration( ServletContext servletContext, Properties config ){
        Enumeration<String> initParameters = 
        		servletContext.getInitParameterNames();

        while( initParameters.hasMoreElements() ){
            String name = initParameters.nextElement();
            config.setProperty( name, servletContext.getInitParameter( name ) );
        }
    }

    private ConfigurableWebApplicationContext createApplicationContext(
            ServletContext servletContext){

        Class<?> clazz = getApplicationContextClass(servletContext);

        if(ConfigurableWebApplicationContext.class.isAssignableFrom(clazz)){
            try{
                ConfigurableWebApplicationContext app =
                        (ConfigurableWebApplicationContext) ClassUtil.getInstance(clazz);


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

    private Class<?> getApplicationContextClass(ServletContext servletContext){
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS);

        if( contextClassName != null )
            return this.getContextClass(contextClassName);
        else{
            for(int i=0;i<APPLICATION_CONTEXT.length;i++){
                String applicationContextClassName = APPLICATION_CONTEXT[i];
                if(ClassUtil.existClass(applicationContextClassName))
                    return this.getContextClass(applicationContextClassName);
            }
        }
        throw new BrutosException("not launch context!");
    }

    private Class<?> getContextClass( String contextClassName ){
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
