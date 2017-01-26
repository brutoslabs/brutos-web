


package org.brandao.brutos.web;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.web.util.WebUtil;


public class WebActionBuilder extends ActionBuilder{
    
    public WebActionBuilder(ActionBuilder builder){
        super(builder);
    }
    
    public WebActionBuilder( Action methodForm, 
            Controller controller, ValidatorFactory validatorFactory,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext) {
        super(methodForm, controller, validatorFactory, 
                controllerBuilder, applicationContext);
    }
    
    public ActionBuilder addAlias(String value){
        
        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type))
            WebUtil.checkURI(value, true);
        
        return super.addAlias(value);
    }

    public ActionBuilder addThrowable( Class<?> target, String view, 
            String id, DispatcherType dispatcher, boolean resolvedView ){

    	
    	
        ActionBuilder builder = super.addThrowable(target, view, id, dispatcher, resolvedView);
        
        ThrowableSafeData thr = this.action.getThrowsSafeOnAction(target);
		
        WebUtil.checkURI(thr.getView(), resolvedView && view != null);

        return builder;
        
    }
    
    public ActionBuilder setView(String value, boolean viewResolved){

    	
    	
        WebUtil.checkURI(value, viewResolved && value != null);
        
        return super.setView(value, viewResolved);
    }
    
}
