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

package org.brandao.brutos.old.programatic;

import java.lang.reflect.Field;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Afonso Brandao
 */
public class BeanBuilder {
    Controller webFrame;
    WebFrameBuilder webFrameBuilder;
    Bean mappingBean;
    
    public BeanBuilder( Bean mappingBean, Controller webFrame, WebFrameBuilder webFrameBuilder ) {
        this.webFrameBuilder = webFrameBuilder;
        this.mappingBean = mappingBean;
        this.webFrame = webFrame;
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty ){
        return addProperty( name, propertyName, enumProperty, null, null, ScopeType.PARAM, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            String temporalProperty ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, temporalProperty, null, ScopeType.PARAM, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            Type type ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null,ScopeType.PARAM, type );
    }
    
    public BeanBuilder addMappedProperty( String name, String propertyName, String mapping ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.PARAM, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, ScopeType.PARAM, null );
    }

    public BeanBuilder addProperty( String name, String propertyName, ScopeType scope ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, scope, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty, String temporalProperty, String mapping, 
            ScopeType scope, Type type ){

        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        propertyName = propertyName == null || propertyName.replace( " ", "" ).length() == 0? null : propertyName;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;

        if( propertyName == null )
            throw new BrutosException( "the property name is required!" );
        else
        if( this.mappingBean.getFields().containsKey( propertyName ) )
            throw new BrutosException( "duplicate property name: " + propertyName );

        if( name == null )
            throw new BrutosException( "name is required: " +
                    mappingBean.getClassType().getName() );

        PropertyBean fieldBean = new PropertyBean(null);
        fieldBean.setEnumProperty( enumProperty );
        fieldBean.setParameterName( name );
        fieldBean.setName(propertyName);
        fieldBean.setTemporalType( temporalProperty );
        //fieldBean.setScopeType( scope );
        /*
        Field f = null;
        
        try{
            f = mappingBean.getClassType().getDeclaredField( propertyName );
            fieldBean.setField( f );
        }
        catch( Exception e ){
            throw new BrutosException( "no such field: " + mappingBean.getClassType().getName() + "." + propertyName );
        }
        */

        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );

        if( mapping != null ){
            fieldBean.setMapping( mapping );
            //if( webFrame.getMappingBeans().containsKey( mapping ) )
            //    fieldBean.setMapping( webFrame.getMappingBean( mapping ) );
            //else
            //    throw new BrutosException( "mapping name " + mapping + " not found!" );
                
        }
        else
        if( type != null ){
            fieldBean.setType( type );
            //if( !f.getType().isAssignableFrom( fieldBean.getType().getClassType() ) )
            //    throw new BrutosException( "expected " + f.getType().getName() + " found " + type.getClassType().getName() );
        }
        else{
            try{
                fieldBean.setType(
                        TypeManager.getType(
                            bean.getGenericType(propertyName),
                            enumProperty,
                            temporalProperty ) );
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException(
                        String.format( "%s.%s : %s" ,
                            new Object[]{webFrame.getClassType().getName(),
                            propertyName,
                            e.getMessage()} ) );
            }
            /*
            fieldBean.setType( Types.getType( f.getType(), enumProperty, temporalProperty ) );
            
            if( fieldBean.getType() == null )
                throw new UnknownTypeException( f.getType().getName() );
            */
        }
        
        this.mappingBean.getFields().put( propertyName, fieldBean );
        return this;
    }
}