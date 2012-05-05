/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos.interceptor;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.StackRequestElement;

/**
 *
 * @author Brandao
 */
public interface ConfigurableInterceptorHandler extends InterceptorHandler{
    
    /**
     * @deprecated 
     * @param URI 
     */
    void setURI(String URI);

    void setResourceAction(ResourceAction resourceAction);

    void setResource(Object resource);

    void setRequestId(String requestId);

    void setContext(ApplicationContext context);

    void setParameters(Object[] value);
    
    void setResult(Object value);

    void setRequestInstrument(RequestInstrument requestInstrument);
    
    RequestInstrument getRequestInstrument();
    
    void setStackRequestElement(StackRequestElement stackRequestElement);
    
    StackRequestElement getStackRequestElement();
    
}
