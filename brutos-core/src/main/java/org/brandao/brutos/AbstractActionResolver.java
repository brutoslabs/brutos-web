package org.brandao.brutos;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.mapping.Action;

public abstract class AbstractActionResolver 
	implements ActionResolver{

	protected ConcurrentMap<ActionType, ActionTypeResolver>
		actionTypeResolver;
	
	public AbstractActionResolver(){
		this.actionTypeResolver = 
				new ConcurrentHashMap<ActionType, ActionTypeResolver>();
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
