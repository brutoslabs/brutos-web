

package org.brandao.brutos.web;

import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcRequestFactory;


public class WebMvcRequestFactory implements MvcRequestFactory{

    public MvcRequest getRequest() {
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        return new WebMvcRequestImp( requestInfo.getRequest() );
    }

}
