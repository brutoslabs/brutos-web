


package org.brandao.brutos.web;

import javax.servlet.ServletContext;
import org.brandao.brutos.ApplicationContext;


public interface WebApplicationContext extends ApplicationContext{

    ServletContext getContext();
    
}
