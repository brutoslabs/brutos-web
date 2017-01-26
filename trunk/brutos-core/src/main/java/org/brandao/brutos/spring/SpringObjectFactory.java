

package org.brandao.brutos.spring;

import org.brandao.brutos.ObjectFactory;
import java.util.Properties;
import org.springframework.context.ApplicationContext;


public class SpringObjectFactory 
    implements ObjectFactory{

    private String name;
    
    private boolean initialized;

    private ApplicationContext context;
    
    public SpringObjectFactory(){
        this.initialized = false;
    }
    
    public Object getBean(String name) {
        
        if(!this.initialized)
            this.init();
        
        return context.containsBeanDefinition(name)? 
                context.getBean(name) : 
                null;
    }

    public Object getBean(Class clazz) {
        
        if(!this.initialized)
            this.init();
        
        return context.getBean(clazz);
    }

    private synchronized void init(){
        
        if(initialized)
            return;
        
        this.context     = SpringContext.getApplicationContext(this.name);
        this.initialized = true;
    }
    
    public void configure(Properties properties) {
        this.name = 
            properties.getProperty(
                    SpringContext.SPRING_CONTEXT_NAME,
                    SpringContext.DEFAULT_SPRING_CONTEXT);
    }

    public void destroy() {
    }

}
