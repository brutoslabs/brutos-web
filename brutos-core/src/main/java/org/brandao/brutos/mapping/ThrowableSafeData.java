

package org.brandao.brutos.mapping;

import org.brandao.brutos.DispatcherType;


public class ThrowableSafeData {
    
    private Class target;
    
    private String view;
    
    private String originalView;
    
    private boolean resolvedView;
    
    private String parameterName;

    private boolean redirect;

    private DispatcherType dispatcher;

    public ThrowableSafeData() {
    }

    public Class getTarget() {
        return target;
    }

    public void setTarget(Class target) {
        this.target = target;
    }

    public String getView() {
        return view;
    }

    public void setView(String uri) {
        this.view = uri;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public DispatcherType getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherType dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean isResolvedView() {
        return resolvedView;
    }

    public void setResolvedView(boolean resolvedView) {
        this.resolvedView = resolvedView;
    }

    public String getOriginalView() {
        return originalView;
    }

    public void setOriginalView(String originalView) {
        this.originalView = originalView;
    }

}
