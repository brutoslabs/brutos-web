

package org.brandao.brutos.web;

import org.brandao.brutos.*;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.web.util.WebUtil;


public class WebControllerBuilder extends ControllerBuilder{
    
    public WebControllerBuilder(ControllerBuilder builder, ControllerManager.InternalUpdate internalUpdate){
        super( builder, internalUpdate );
    }
    
    public WebControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext, ControllerManager.InternalUpdate internalUpdate ){
        super( controller, controllerManager, interceptorManager, 
                validatorFactory, applicationContext, internalUpdate );
    }
    
    public ControllerBuilder addAlias( String id ){
        WebUtil.checkURI(id, true);
        return super.addAlias(id);
    }
    
    public ActionBuilder addAction( String id, String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
        
    	
        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type)){
            WebUtil.checkURI(id, true);
            
            
        }
        
        ActionBuilder builder =
            super.addAction(id, resultId, resultRendered, view, 
            dispatcher, resolvedView, executor);
        
        WebUtil.checkURI(builder.getView(), resolvedView && view != null);
        
        return new WebActionBuilder(builder);
    }
    
    public ControllerBuilder addThrowable( Class<?> target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){
        
        
        
		ControllerBuilder builder = super.addThrowable(target, view, id, dispatcher, resolvedView);

        ThrowableSafeData thr = this.controller.getThrowsSafe(target);
		
        WebUtil.checkURI(thr.getView(), resolvedView && view != null);

        return builder;
    }
    
    public ControllerBuilder setDefaultAction( String id ){
        WebUtil.checkURI(id,true);
        return super.setDefaultAction(id);
    }
    
    public ControllerBuilder setId(String value){
        WebUtil.checkURI(value,true);
        return super.setId(value);
    }
    
    public ControllerBuilder setView(String value, boolean resolvedView){
        
        //if(this.controller.isResolvedView())
        //    WebUtil.checkURI(value,true);
     
    	WebUtil.checkURI(value, resolvedView && value != null);
    	
        return super.setView(value, resolvedView);
    }
    
}
