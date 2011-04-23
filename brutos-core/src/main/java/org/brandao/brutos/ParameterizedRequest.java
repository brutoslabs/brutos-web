/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos;

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Brandao
 */
public class ParameterizedRequest {

    private Throwable objectThrow;
    private ThrowableSafeData throwableSafeData;
    private Object[] parameters;
    private String view;
    private DispatcherType dispatcherType;
    private ApplicationContext context;
    private Form controller;
    private ResourceAction action;
    private boolean hasViewProcessed;
    private Object resultAction;
    private InterceptorHandler handler;
    private IOCProvider iocProvider;
    private ViewProvider viewProvider;

    public Throwable getObjectThrow() {
        return objectThrow;
    }

    public void setObjectThrow(Throwable objectThrow) {
        this.objectThrow = objectThrow;
    }

    public ThrowableSafeData getThrowableSafeData() {
        return throwableSafeData;
    }

    public void setThrowableSafeData(ThrowableSafeData throwableSafeData) {
        this.throwableSafeData = throwableSafeData;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public DispatcherType getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public Form getController() {
        return controller;
    }

    public void setController(Form controller) {
        this.controller = controller;
    }

    public ResourceAction getAction() {
        return action;
    }

    public void setAction(ResourceAction action) {
        this.action = action;
    }

    public boolean isHasViewProcessed(){
        return hasViewProcessed;
    }

    public void setHasViewProcessed(boolean hasViewProcessed) {
        this.hasViewProcessed = hasViewProcessed;
    }

    public Object getResultAction() {
        return resultAction;
    }

    public void setResultAction(Object resultAction) {
        this.resultAction = resultAction;
    }

    public InterceptorHandler getHandler() {
        return handler;
    }

    public void setHandler(InterceptorHandler handler) {
        this.handler = handler;
    }

    public IOCProvider getIocProvider() {
        return iocProvider;
    }

    public void setIocProvider(IOCProvider iocProvider) {
        this.iocProvider = iocProvider;
    }

    public ViewProvider getViewProvider() {
        return viewProvider;
    }

    public void setViewProvider(ViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }
    

}
