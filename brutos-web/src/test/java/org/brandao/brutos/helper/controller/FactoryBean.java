

package org.brandao.brutos.helper.controller;


public class FactoryBean {

    public SimpleBean getInstance(){
        return new SimpleBean();
    }

    public SimpleBean getInstance( String arg ){
        if( arg != null )
            return new SimpleBean(arg);
        else
            return null;
    }

    public static SimpleBean getInstanceByStaticMethod(){
        return new SimpleBean();
    }
}
