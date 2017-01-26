


package org.brandao.brutos;

import java.util.Properties;


public class ApplicationContextWrapper 
        extends AbstractApplicationContext{

    protected ConfigurableApplicationContext applicationContext;

    public ApplicationContextWrapper( ConfigurableApplicationContext app ){
        this.applicationContext = app;
    }

    public void destroy(){
        this.applicationContext.destroy();
    }

    public Properties getConfiguration(){
        return this.applicationContext.getConfiguration();
    }

    public MvcResponse getMvcResponse() {
        return this.applicationContext.getMvcResponse();
    }

    public MvcRequest getMvcRequest() {
        return this.applicationContext.getMvcRequest();
    }

    public Scopes getScopes() {
        return this.applicationContext.getScopes();
    }

    public Object getController(Class clazz) {
        return this.applicationContext.getController(clazz);
    }

    protected void loadDefinitions(ComponentRegistry registry) {
        throw new UnsupportedOperationException();
    }
    
}
