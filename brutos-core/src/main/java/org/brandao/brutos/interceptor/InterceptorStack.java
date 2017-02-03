package org.brandao.brutos.interceptor;


public interface InterceptorStack {
    
    public void next( InterceptorHandler handler ) throws InterceptedException;
    
}
