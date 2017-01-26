

package org.brandao.brutos.web;

import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.MvcResponseFactory;


public class WebMvcResponseFactory implements MvcResponseFactory{

    public MvcResponse getResponse() {
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        return new WebMvcResponseImp( requestInfo.getResponse() );
    }

}
