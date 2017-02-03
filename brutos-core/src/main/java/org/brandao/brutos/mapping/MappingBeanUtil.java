package org.brandao.brutos.mapping;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.type.NullType;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;


public final class MappingBeanUtil {
    
    public static final int CONSTRUCTOR_ARG = 0;
    
    public static final int PROPERTY        = 1;
    
    public static final int DEPENDENCY      = 2;

	public static DependencyBean createProperty( 
            String name, String propertyName,
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, 
            Object classType, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){
    	
    	PropertyBean dependencyBean = new PropertyBean(mappingBean);
        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );
    	
        if( StringUtil.isEmpty(propertyName) )
            throw new MappingException( "the property name is required!" );
        else
        if( mappingBean.getFields().containsKey( propertyName ) )
            throw new MappingException( "duplicate property name: " + propertyName );
    
        if(!bean.containProperty(propertyName))
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );
        
        if(StringUtil.isEmpty(name) && !nullable && value == null)
            throw new IllegalArgumentException("bean name is required");
        
        name = StringUtil.isEmpty(name)? propertyName : StringUtil.adjust(name);
        
        classType = classType == null? bean.getGenericType(propertyName) : classType;
        
        dependencyBean.setName(propertyName);
        
        return createDependencyBean( 
                name, enumProperty, temporalProperty, mapping, 
                scope, value, nullable, generic, typeDef, 
                classType, dependencyBean, mappingBean, validatorFactory, controller);        
    }

	public static DependencyBean createConstructorArg( 
            String name,
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, 
            Object classType, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){
    	
		ConstructorArgBean dependencyBean = new ConstructorArgBean(mappingBean);
    	
        if(StringUtil.isEmpty(name) && !nullable && value == null)
            throw new IllegalArgumentException("bean name is required");
        
        return createDependencyBean( 
                name, enumProperty, temporalProperty, mapping, 
                scope, value, nullable, generic, typeDef, 
                classType, dependencyBean, mappingBean, validatorFactory, controller);        
    }

	public static DependencyBean createMetaBeanValue( 
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, 
            Object classType, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){
    	
		ConstructorArgBean dependencyBean = new ConstructorArgBean(mappingBean);
    	
        return createDependencyBean( 
                null, enumProperty, temporalProperty, mapping, 
                scope, value, nullable, generic, typeDef, 
                classType, dependencyBean, mappingBean, validatorFactory, controller);        
    }
	
	public static DependencyBean createDependencyBean( 
            String name,
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, 
            Object classType, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){
		
		InnerBean dependencyBean = new InnerBean(mappingBean);
		
        return createDependencyBean( 
                name, enumProperty, temporalProperty, mapping, 
                scope, value, nullable, generic, typeDef, 
                classType, dependencyBean, mappingBean, validatorFactory, controller);        
	}
	
	@SuppressWarnings("unchecked")
	private static DependencyBean createDependencyBean( 
            String name,
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, 
            Object classType, DependencyBean dependencyBean, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){
		
        name             = StringUtil.adjust(name);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping          = StringUtil.adjust(mapping);
        Class<?> rawType = TypeUtil.getRawType(classType);
        
        dependencyBean.setEnumProperty( enumProperty );
        dependencyBean.setParameterName( name );
        dependencyBean.setNullable(nullable);
        
        dependencyBean.setTemporalType( temporalProperty );
        dependencyBean.setValue(value);
        dependencyBean.setScopeType(scope);

        if(typeDef == null){
        	if(nullable){
        		if(classType == null)
                	throw new MappingException("type must be informed");
        			
            	typeDef = new NullType((Class<?>)classType);
        	}
        	else
        	if(classType != null){
	            try{
	                typeDef = 
	                		((ConfigurableApplicationContext)controller.getContext()).getTypeManager()
		                		.getType(classType, enumProperty, temporalProperty );
	                
	            }
	            catch( UnknownTypeException e ){
	                throw new MappingException(e);
	            }
	            
	            if(typeDef == null)
	            	typeDef = new ObjectType(rawType);
	            
	        }
        }
        else
    	if(classType != null){
            if(!typeDef.getClassType().isAssignableFrom(rawType)){
                throw new MappingException(
                        String.format(
                            "expected %s found %s",
                            new Object[]{
                                rawType.getSimpleName(),
                                typeDef.getClassType().getSimpleName()
                            }
                        )
                );
            }
        }
        
        dependencyBean.setType(typeDef);
        
        if(generic){
        	MetaBean metaBean = new MetaBean(controller);
        	metaBean.setClassType(rawType);
        	//metaBean.setType(typeDef);
        	dependencyBean.setMetaBean(metaBean);
        }
        else
        if(!StringUtil.isEmpty(mapping)){
            if( controller.getBean(mapping) != null )
            	dependencyBean.setMapping(mapping);
            else
                throw new MappingException( "mapping name " + mapping + " not found!" );
        }
        
        
        Configuration validatorConfig = new Configuration();
        dependencyBean.setValidator( validatorFactory.getValidator(validatorConfig) );
        return dependencyBean;
    }

	    
}
