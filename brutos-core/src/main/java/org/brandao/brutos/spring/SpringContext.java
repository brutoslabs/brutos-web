

package org.brandao.brutos.spring;

import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContext implements ApplicationContextAware{

    public static final String DEFAULT_SPRING_CONTEXT = "DEFAULT_SPRING_CONTEXT";

    public static final String SPRING_CONTEXT_NAME    = "org.brandao.brutos.spring.context_name";
    
    private static final Map currentApplicationCopntext;
    
    static{
        currentApplicationCopntext = new HashMap();
    }
    
    private String name;

    public SpringContext(String name){
        this.name = name;
    }
    
    public SpringContext(){
        this.name = DEFAULT_SPRING_CONTEXT;
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        registerApplicationContext(this.name, applicationContext);
    }
    
    private static synchronized void registerApplicationContext(String name,
            ApplicationContext applicationContext) throws BeansException {

        if( currentApplicationCopntext.containsKey(name)){
            throw new IllegalStateException(
                    "Multiple application context definitions has been detected.");
        }
        else
            currentApplicationCopntext.put(name, applicationContext);
    }
    
    public static ApplicationContext getApplicationContext(String name){
        ApplicationContext applicationContext 
                = (ApplicationContext) currentApplicationCopntext.get(name);
    
        if(applicationContext == null)
            throw new BrutosException("application context not found!");
        else
            return applicationContext;
    }
    
}
