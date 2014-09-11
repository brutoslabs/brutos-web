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
import org.brandao.brutos.Configuration;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.type.UnknownTypeException;

/**
 *
 * @author Afonso Brandao
 */
public final class MappingBeanUtil {
    
    public static final int CONSTRUCTOR_ARG = 0;
    
    public static final int PROPERTY        = 1;
    
    public static final int DEPENDENCY      = 2;
    
    public static DependencyBean createDependencyBean( 
            String name, String propertyName,
            EnumerationType enumProperty,
            String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, Type typeDef, 
            Object type, int dependencyType, Bean mappingBean,
            ValidatorFactory validatorFactory,
            Controller controller){

        name             = StringUtil.adjust(name);
        propertyName     = StringUtil.adjust(propertyName);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping          = StringUtil.adjust(mapping);
        
        if( dependencyType == PROPERTY ){
            if( propertyName == null )
                throw new BrutosException( "the property name is required!" );
            else
            if( mappingBean.getFields().containsKey( propertyName ) )
                throw new BrutosException( "duplicate property name: " + propertyName );
        }

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
        if(StringUtil.isEmpty(name) && StringUtil.isEmpty(mapping)){
            if(!nullable && value == null)
                throw new IllegalArgumentException("bean name is required");
        }
            
        dependencyBean.setTemporalType( temporalProperty );
        dependencyBean.setValue(value);
        dependencyBean.setScopeType(scope);
        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );

        if( propertyName != null && !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );

        if( mapping != null )
            dependencyBean.setMapping( mapping );

        if( typeDef != null ){
            if(type != null){
                Class rawType = TypeManager.getRawType(type);
                if(!typeDef.getClassType().isAssignableFrom(rawType)){
                    throw new IllegalArgumentException(
                            String.format(
                                "expected %s found %s",
                                new Object[]{
                                    rawType.getName(),
                                    typeDef.getClassType().getName()
                                }
                            )
                    );
                }
            }
            
            dependencyBean.setType( typeDef );
        }
        else{
            try{
                if( dependencyType == PROPERTY ){
                    dependencyBean.setType(
                            TypeManager.getType(
                                bean.getGenericType(propertyName),
                                enumProperty,
                                temporalProperty ) );
                }
                else
                if( type != null ){
                    dependencyBean
                            .setType(
                                TypeManager.getType(
                                    type, 
                                    enumProperty, 
                                    temporalProperty));
                }
                
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException(
                        String.format( "%s.%s : %s" ,
                            new Object[]{
                                controller.getClassType().getName(),
                                propertyName,
                                e.getMessage()} ) );
            }
        }
        Configuration validatorConfig = new Configuration();
        dependencyBean.setValidator( validatorFactory.getValidator(validatorConfig) );
        return dependencyBean;
    }
    
}
