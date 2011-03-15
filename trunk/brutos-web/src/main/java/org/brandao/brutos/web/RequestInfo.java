/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Brandao
 */
public class RequestInfo {

    private static final ThreadLocal currentRequestInfo = new ThreadLocal();

    private ServletRequest request;
    private ServletResponse response;

    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public void setResponse(ServletResponse response) {
        this.response = response;
    }

    public static void setCurrent( RequestInfo requestInfo ){
        currentRequestInfo.set(requestInfo);
    }

    public static RequestInfo getCurrentRequestInfo(){
        return (RequestInfo) currentRequestInfo.get();
    }

    public static void removeCurrent(){
        currentRequestInfo.remove();
    }
}
