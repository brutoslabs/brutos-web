

package org.brandao.brutos.web.test;


import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.WebApplicationContextWrapper;


public class MockWebApplicationContext extends WebApplicationContextWrapper{

    private static ConfigurableWebApplicationContext app;

    public MockWebApplicationContext(){
        super( app );
    }

    public static void setCurrentApplicationContext( ConfigurableWebApplicationContext apps ){
        app = apps;
    }

}
