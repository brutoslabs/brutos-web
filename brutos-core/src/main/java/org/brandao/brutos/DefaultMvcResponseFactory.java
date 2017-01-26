


package org.brandao.brutos;


public class DefaultMvcResponseFactory implements MvcResponseFactory{

    public MvcResponse getResponse() {
        return new DefaultMvcResponse();
    }

}
