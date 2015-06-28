/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.mapping;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.type.AnyType;
import org.brandao.brutos.type.NullType;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

/**
 *
 * @author Afonso Brandao
 */
public final class MappingBeanUtil {
    
    public static final int CONSTRUCTOR_ARG = 0;
    
    public static final int PROPERTY        = 1;
    
    public static final int DEPENDENCY      = 2;
    
    @SuppressWarnings("unchecked")
	public static DependencyBean createDependencyBean( 
            String name, String propertyName,
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, 
            Object classType, int dependencyType, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){

        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );
    	
        if( dependencyType == PROPERTY ){
            if( StringUtil.isEmpty(propertyName) )
                throw new MappingException( "the property name is required!" );
            else
            if( mappingBean.getFields().containsKey( propertyName ) )
                throw new MappingException( "duplicate property name: " + propertyName );
        }
    	
        classType = 
    		dependencyType == PROPERTY && classType == null? 
				bean.getGenericType(propertyName) : classType;
    	
        name             = StringUtil.adjust(name);
        propertyName     = StringUtil.adjust(propertyName);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping          = StringUtil.adjust(mapping);
        Class<?> rawType = TypeUtil.getRawType(classType);
        
        DependencyBean dependencyBean;
        
        switch(dependencyType){
            case CONSTRUCTOR_ARG:{
                dependencyBean = new ConstructorArgBean(mappingBean);
                break;
            }
            case PROPERTY:{
                dependencyBean = new PropertyBean(mappingBean);
                break;
            }
            default:
                dependencyBean = new InnerBean(mappingBean);
        }
        
        dependencyBean.setEnumProperty( enumProperty );
        dependencyBean.setParameterName( name );
        dependencyBean.setNullable(nullable);
        
        if( dependencyType == PROPERTY )
            ((PropertyBean)dependencyBean).setName(propertyName);
        else
        if(StringUtil.isEmpty(name) && !nullable && value == null)
            throw new IllegalArgumentException("bean name is required");
            
        dependencyBean.setTemporalType( temporalProperty );
        dependencyBean.setValue(value);
        dependencyBean.setScopeType(scope);

        if( propertyName != null && !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );

        if(typeDef == null){
        	if(nullable){
        		if(classType == null)
                	throw new MappingException("type must be informed");
        			
            	typeDef = new NullType((Class)classType);
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
        	dependencyBean.setMetaBean(new MetaBean(controller));
        	
        	if(dependencyBean.getType() == null)
        		dependencyBean.setType(new AnyType(rawType));
        	
        }
        else
        if(!StringUtil.isEmpty(mapping)){
            if( controller.getBean(mapping) != null )
            	dependencyBean.setMapping(mapping);
            else
                throw new MappingException( "mapping name " + mapping + " not found!" );
        }
        else{
            Type definedType = dependencyBean.getType();
            
            if(definedType != null && definedType.getClass() == ObjectType.class && rawType != Object.class)
            	throw new MappingException("unknown type: " + rawType.getSimpleName());
        }
        
        Configuration validatorConfig = new Configuration();
        dependencyBean.setValidator( validatorFactory.getValidator(validatorConfig) );
        return dependencyBean;
    }
    
}
