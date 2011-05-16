/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import java.util.Properties;
import javax.servlet.ServletContext;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.Scopes;

/**
 *
 * @author Brandao
 */
public class WebApplicationContextWrapper 
        extends AbstractWebApplicationContext{

    protected WebApplicationContext applicationContext;

    public WebApplicationContextWrapper(WebApplicationContext app){
        this.applicationContext = app;
    }

    public void configure( Properties config ){
        this.applicationContext.configure(config);
    }

    public void destroy(){
        this.applicationContext.destroy();
    }

    public Object getController( Class controllerClass ){
        return this.applicationContext.getController(controllerClass);
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

    public void configure() {
         this.applicationContext.configure();
    }

    public ServletContext getContext(){
        return this.applicationContext.getContext();
    }

    public void setServletContext(ServletContext servletContext){
        
        if( applicationContext instanceof ConfigurableWebApplicationContext )
            ((ConfigurableWebApplicationContext)this.applicationContext).
                    setServletContext(servletContext);

    }

}
