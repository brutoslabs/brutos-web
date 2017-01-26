

package org.brandao.brutos.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManagerImp;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.web.util.WebUtil;


public class WebControllerManager extends ControllerManagerImp{
 
    public WebControllerManager(){
        super();
    }
    
    public ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class classType, String actionId ){
            return addController( id, view, resolvedView,
                    dispatcherType, name, classType, actionId, 
                    ActionType.HIERARCHY);
    }
    
    public ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType, String name, 
            Class classType, 
            String actionId, ActionType actionType ){
        
        if(!ActionType.DETACHED.equals(actionType))
            WebUtil.checkURI(id, true);
        
        if(resolvedView && view != null)
            WebUtil.checkURI(view, true);
        
        ControllerBuilder builder =  
            super.addController( id, view,
                resolvedView,
                dispatcherType, name, classType, actionId, actionType );
        
        return new WebControllerBuilder(builder, this.internalUpdate);
    }
    
}
