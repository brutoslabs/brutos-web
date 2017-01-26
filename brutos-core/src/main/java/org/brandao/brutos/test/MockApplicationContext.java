

package org.brandao.brutos.test;


import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.ApplicationContextWrapper;




public class MockApplicationContext extends ApplicationContextWrapper{

    private static AbstractApplicationContext app;

    public MockApplicationContext(){
        super( app );
    }

    public static void setCurrentApplicationContext( AbstractApplicationContext apps ){
        app = apps;
    }
}
