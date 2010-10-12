/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos.programatic;

import java.lang.reflect.Field;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.mapping.FieldBean;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MappingBean;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brandao
 */
public class BeanBuilder {
    Form webFrame;
    WebFrameBuilder webFrameBuilder;
    MappingBean mappingBean;
    
    public BeanBuilder( MappingBean mappingBean, Form webFrame, WebFrameBuilder webFrameBuilder ) {
        this.webFrameBuilder = webFrameBuilder;
        this.mappingBean = mappingBean;
        this.webFrame = webFrame;
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty ){
        return addProperty( name, propertyName, enumProperty, null, null, ScopeType.REQUEST, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            String temporalProperty ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, temporalProperty, null, ScopeType.REQUEST, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName,
            Type type ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null,ScopeType.REQUEST, type );
    }
    
    public BeanBuilder addMappedProperty( String name, String propertyName, String mapping ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.REQUEST, null );
    }
    
    public BeanBuilder addProperty( String name, String propertyName ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, ScopeType.REQUEST, null );
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

        FieldBean fieldBean = new FieldBean();
        fieldBean.setEnumProperty( enumProperty );
        fieldBean.setParameterName( name );
        fieldBean.setName(propertyName);
        fieldBean.setTemporalType( temporalProperty );
        fieldBean.setScopeType( scope );
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
                        Types.getType(
                            bean.getGenericType(propertyName),
                            enumProperty,
                            temporalProperty ) );
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException(
                        String.format( "%s.%s : %s" ,
                            webFrame.getClassType().getName(),
                            propertyName,
                            e.getMessage() ) );
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
