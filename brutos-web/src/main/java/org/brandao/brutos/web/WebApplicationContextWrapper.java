/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import javax.servlet.ServletContext;
import org.brandao.brutos.ApplicationContextWrapper;

/**
 *
 * @author Brandao
 */
public class WebApplicationContextWrapper 
        extends ApplicationContextWrapper
        implements WebApplicationContext{

    public WebApplicationContextWrapper(WebApplicationContext app){
        super(app);
    }

    public ServletContext getContext() {
        return ((WebApplicationContext)applicationContext).getContext();
    }
}
