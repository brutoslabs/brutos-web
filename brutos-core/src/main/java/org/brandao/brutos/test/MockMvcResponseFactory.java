

package org.brandao.brutos.test;

import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.MvcResponseFactory;


public class MockMvcResponseFactory implements MvcResponseFactory{

    public MvcResponse getResponse() {
        return new MockMvcResponse();
    }

}
