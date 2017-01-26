

package org.brandao.brutos;

import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;


public class PropertyBuilder 
	extends RestrictionBuilder 
	implements GenericBuilder{

    private Object propertyBean;
    
    private ControllerBuilder controllerBuilder;
    
    private BeanBuilder beanBuilder;
    
    private ValidatorFactory validatorFactory;
    
    public PropertyBuilder(PropertyBean propertyBean, BeanBuilder beanBuilder,
    		ValidatorFactory validatorFactory){
        super(propertyBean.getValidator().getConfiguration() );
        this.propertyBean = propertyBean;
        this.controllerBuilder = beanBuilder.getControllerBuilder();
        this.beanBuilder = beanBuilder;
        this.validatorFactory = validatorFactory;
    }

    public PropertyBuilder(PropertyController propertyBean, ControllerBuilder controllerBuilder,
    		ValidatorFactory validatorFactory){
        super( propertyBean.getValidate().getConfiguration() );
        this.propertyBean = propertyBean;
        this.controllerBuilder = controllerBuilder;
        this.validatorFactory = validatorFactory;
    }
    
    public ControllerBuilder getControllerBuilder(){
    	return this.controllerBuilder;
    }

    public BeanBuilder getBeanBuilder(){
    	return this.beanBuilder;
    }
    
    public RestrictionBuilder addRestriction( RestrictionRules ruleId, Object value ){
        return super.addRestriction( ruleId, value );
    }

    public RestrictionBuilder setMessage( String message ){
        return super.setMessage(message);
    }

    public MetaBeanBuilder buildMetaBean(String name, Class<?> classType){
    	return this.buildMetaBean(name, ScopeType.PARAM, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
    			BrutosConstants.DEFAULT_TEMPORALPROPERTY, classType, null);
    }

    public MetaBeanBuilder buildMetaBean(String name, 
            EnumerationType enumProperty, String temporalProperty, 
            Class<?> classType){
    	return this.buildMetaBean(name, ScopeType.PARAM, enumProperty, 
    			temporalProperty, classType, null);
    }

    public MetaBeanBuilder buildMetaBean(String name, Type type ){
    	return this.buildMetaBean(name, ScopeType.PARAM, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
    			BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, type);
    }

    public MetaBeanBuilder buildMetaBean(String name, ScopeType scope, Class<?> classType){
    	return this.buildMetaBean(name, scope, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
    			BrutosConstants.DEFAULT_TEMPORALPROPERTY, classType, null);
    }

    public MetaBeanBuilder buildMetaBean(String name, ScopeType scope, Type type){
    	return this.buildMetaBean(name, scope, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
    			BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, type);
    }
    
    public MetaBeanBuilder buildMetaBean(String name, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            Class<?> classType, Type type ){


    	String propertyName = 
				this.propertyBean instanceof PropertyBean?
					((PropertyBean)this.propertyBean).getName() :
					((PropertyController)this.propertyBean).getName();
    	
    	Bean parent = 
				this.propertyBean instanceof PropertyBean?
					((PropertyBean)this.propertyBean).getParent() :
					null;
    	
    	MetaBean metaBean = 
				this.propertyBean instanceof PropertyBean?
					((PropertyBean)this.propertyBean).getMetaBean() :
					((PropertyController)this.propertyBean).getMetaBean();

		if(metaBean == null)
    		throw new MappingException("can't add meta bean");

		if(this.propertyBean instanceof PropertyBean)
			((PropertyBean)this.propertyBean).setMetaBean(metaBean);
		else
			((PropertyController)this.propertyBean).setMetaBean(metaBean);
					
    	MetaBeanBuilder builder = 
    			new MetaBeanBuilder(
    					metaBean,
    					name, 
    					scope, 
    					enumProperty, 
    					temporalProperty, 
    					classType, 
    					type,
    					validatorFactory,
    					this.controllerBuilder,
    					(parent == null? "" : parent.getName() + "#") + propertyName);
    	
    	return builder; 
    }
    
}
