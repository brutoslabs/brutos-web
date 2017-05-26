package org.brandao.brutos;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

public abstract class AbstractActionResolver 
	implements ActionResolver{

	protected ConcurrentMap<ActionType, ActionTypeResolver>
		actionTypeResolver;
	
	public AbstractActionResolver(){
		this.actionTypeResolver = 
				new ConcurrentHashMap<ActionType, ActionTypeResolver>();
	}
	
	protected ScopeType getScope() {
		return ScopeType.PARAM;
	}
	
	public ResourceAction getResourceAction(ControllerManager controllerManager,
			MutableMvcRequest request) throws ActionResolverException{
		
        Iterator<Controller> controllers = controllerManager.getAllControllers();
        Scope paramScope =
                request.getApplicationContext().getScopes().get(this.getScope());
        
        while(controllers.hasNext()){
            Controller controller = controllers.next();
            ActionType actionType = controller.getActionType();
            
            ActionTypeResolver actionTyperesolver = this.actionTypeResolver.get(actionType);
            ResourceAction action = 
            		actionTyperesolver.getResourceAction(controller, paramScope, request);
            
            if(action != null){
            	Set<DataType> requestTypes = action.getMethodForm().getRequestTypes();
            	
            	if(requestTypes.isEmpty()){
            		requestTypes = action.getController().getRequestTypes();
            	}
            	
            	if(!requestTypes.isEmpty() && requestTypes.contains(request.getType())){
            		throw new ActionResolverException("request type not supported");
            	}
            	
            	return action;
            }
            
        }
        
        return null;
	}
	
    public ResourceAction getResourceAction(Controller controller,
    		MutableMvcRequest request) throws ActionResolverException {

        if( controller.getId() != null ){
            Scope scope = request.getApplicationContext().getScopes()
                    .get(this.getScope());

            return getResourceAction( 
                    controller,
                    String.valueOf(
                            scope.get( controller.getActionId() ) ),
                    request);
        }
        else
            return getResourceAction( controller, request.getRequestId(), request );
        
    }
	
    public ResourceAction getResourceAction(Controller controller, String actionId, 
    		MutableMvcRequest request) throws ActionResolverException {

        Action method = controller.getAction(new ActionID(actionId));
        return method == null? null : getResourceAction( method );
    }
    
	public void addActionTypeResolver(ActionType key, ActionTypeResolver value) {
		if(this.actionTypeResolver.containsKey(key)){
			throw new IllegalStateException("action type has been registered!");
		}
		this.actionTypeResolver.put(key, value);
	}

	public void removeActionTypeResolver(ActionType key) {
		if(!this.actionTypeResolver.containsKey(key)){
			throw new IllegalStateException("action type not registered!");
		}
		this.actionTypeResolver.remove(key);
	}

	public ResourceAction getResourceAction(Action action) throws ActionResolverException {
		return new DefaultResourceAction( action.getController(), action );
	}

}
