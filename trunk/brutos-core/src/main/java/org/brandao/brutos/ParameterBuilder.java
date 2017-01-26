

package org.brandao.brutos;

import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;


public class ParameterBuilder 
	extends RestrictionBuilder
	implements GenericBuilder{

    private ParameterAction parameter;
    
    private ParametersBuilder parametersBuilder;
    
    private ValidatorFactory validatorFactory;
    
    public ParameterBuilder(ParameterAction value, 
    		ParametersBuilder parametersBuilder, ValidatorFactory validatorFactory){
        super( value.getValidate().getConfiguration() );
        this.parameter = value;
        this.parametersBuilder = parametersBuilder;
        this.validatorFactory = validatorFactory;
    }

    public RestrictionBuilder addRestriction( RestrictionRules ruleId, Object value ){
        
        if(ruleId == null)
            throw new NullPointerException();
        
        return super.addRestriction( ruleId, value );
    }

    public RestrictionBuilder setMessage( String message ){
        return super.setMessage(message);
    }

    public ParametersBuilder getParametersBuilder(){
    	return this.parametersBuilder;
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

    	MetaBean metaBean = this.parameter.getMetaBean();
    	
    	if(metaBean == null)
    		throw new MappingException("can't add meta bean");
    	
		this.parameter.setMetaBean(metaBean);
					
    	MetaBeanBuilder builder = 
    			new MetaBeanBuilder(
    					metaBean,
    					name, 
    					scope, 
    					enumProperty, 
    					temporalProperty, classType, 
    					type,
    					this.validatorFactory,
    					this.parametersBuilder.getControllerBuilder(),
    					this.parameter.getName());
    	
    	return builder; 
    }
    
}
