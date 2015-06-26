package org.brandao.brutos.mapping;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.ValidatorException;

public class MetaBean extends Bean{

    private Type type;

    private ScopeType scopeType;
    
    private String name;
    
	private Map<Object, String> metaValues;
	
	private Controller controller;
	
	public MetaBean(Controller controller){
		super(controller);
		this.metaValues = new HashMap<Object,String>();
		this.controller = controller;
	}

    public void putMetaValue(String value, String mapping){
    	Object metaValue = this.type.convert(value);
    	
    	if(metaValue == null)
    		throw new MappingException("invalid meta value: " + value);
    		
    	this.putMetaValue(metaValue, mapping);
    }
	
    public void putMetaValue(Object value, String mapping){
    	if(this.metaValues.put(value, mapping) != null)
    		throw new MappingException("duplicate meta value: " + value);
    }

    public void removeMetaValue(Object value){
    	this.metaValues.remove(value);
    }

    public void removeMetaValue(String value){
    	Object metaValue = this.type.convert(value);
    	this.metaValues.remove(metaValue);
    }
    
    @Override
    public Object getValue( Object instance, String prefix, long index, 
            ValidatorException exceptionHandler, boolean force ){
    	
        String pre = prefix != null? prefix : "";
        String key = pre + this.name;

        Object metaValue = getScope().get(key);
        metaValue = this.type.convert(metaValue);

        if(metaValue == null)
        	return null;
        
        String beanName = this.metaValues.get(metaValue);
        Bean bean = this.controller.getBean(beanName);
        
        if(bean == null)
        	throw new MappingException("bean not found: " + metaValue);
        
        return bean.getValue(instance, prefix, index, exceptionHandler, force);
    }
    
    public Scope getScope() {
        return Scopes.getCurrentScope(scopeType);
    }

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ScopeType getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeType scopeType) {
		this.scopeType = scopeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
