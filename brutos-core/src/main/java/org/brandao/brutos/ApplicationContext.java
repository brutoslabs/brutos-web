


package org.brandao.brutos;

import java.util.Properties;


public interface ApplicationContext {

    
    void destroy();

    
    Properties getConfiguration();

    
    MvcRequest getMvcRequest();

    
    MvcResponse getMvcResponse();

    
    Scopes getScopes();

    
    Object getController(Class<?> clazz);
    
    
    Object getBean(Class<?> clazz);

    
    Object getBean(String name);
    
}
