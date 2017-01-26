


package org.brandao.brutos;


public class DefaultMvcRequestFactory implements MvcRequestFactory{

    public MvcRequest getRequest() {
        return new DefaultMvcRequest();
    }

}
