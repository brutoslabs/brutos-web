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

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.mapping.FieldBean;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MappingBean;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 *
 * @author Afonso Brandao
 */
public class BeanBuilder {

    Form webFrame;
    ControllerBuilder webFrameBuilder;
    MappingBean mappingBean;
    ValidatorProvider validatorProvider;

    public BeanBuilder( MappingBean mappingBean, Form webFrame, ControllerBuilder controllerBuilder,
            ValidatorProvider validatorProvider ) {
        this.webFrameBuilder = controllerBuilder;
        this.mappingBean = mappingBean;
        this.webFrame = webFrame;
    }

    public BeanBuilder setFactory( String factory ){
        mappingBean.setFactory(factory);
        return this;
    }

    public BeanBuilder setMethodfactory( String methodFactory ){
        mappingBean.getConstructor().setMethodFactory(methodFactory);
        return this;
    }

    public PropertyBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty ){
        return addProperty( name, propertyName, enumProperty, null, null, ScopeType.REQUEST, null, null );
    }
    
    public PropertyBuilder addProperty( String name, String propertyName,
            String temporalProperty ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, temporalProperty, null, ScopeType.REQUEST, null, null );
    }
    
    public PropertyBuilder addProperty( String name, String propertyName,
            Type type ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null,ScopeType.REQUEST, null, type );
    }
    
    public PropertyBuilder addMappedProperty( String name, String propertyName, String mapping ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.REQUEST, null, null );
    }
    
    public PropertyBuilder addProperty( String name, String propertyName ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, ScopeType.REQUEST, null, null );
    }

    public PropertyBuilder addProperty( String name, String propertyName, ScopeType scope ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, scope, null, null );
    }

    public PropertyBuilder addStaticProperty( String name, String propertyName, Object value ){
        return addProperty( null, propertyName,
            EnumerationType.ORDINAL, "dd/MM/yyyy", null, ScopeType.REQUEST, value, null );
    }

    public PropertyBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty, String temporalProperty, String mapping, 
            ScopeType scope, Object value, Type type ){

        /*
        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        propertyName = propertyName == null || propertyName.replace( " ", "" ).length() == 0? null : propertyName;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;

        if( propertyName == null )
            throw new BrutosException( "the property name is required!" );
        else
        if( this.mappingBean.getFields().containsKey( propertyName ) )
            throw new BrutosException( "duplicate property name: " + propertyName );

        /*
        if( name == null )
            throw new BrutosException( "name is required: " +
                    mappingBean.getClassType().getName() );
        */
        /*
        FieldBean fieldBean = new FieldBean();
        fieldBean.setEnumProperty( enumProperty );
        fieldBean.setParameterName( name );
        fieldBean.setName(propertyName);
        fieldBean.setTemporalType( temporalProperty );
        fieldBean.setValue(value);
        fieldBean.setScopeType( scope );

        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );

        if( mapping != null ){
            fieldBean.setMapping( mapping );
                
        }
        else
        if( type != null ){
            fieldBean.setType( type );
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
        }
        */

        FieldBean fieldBean = this.createFieldBean(name, propertyName, enumProperty,
                temporalProperty, mapping, scope, value, type);

        Configuration validatorConfig = new Configuration();
        fieldBean.setValidator( validatorProvider.getValidator( validatorConfig ) );
        this.mappingBean.getFields().put( propertyName, fieldBean );

        return new PropertyBuilder( validatorConfig );
    }

    public BeanBuilder addContructorArg( String name,
            EnumerationType enumProperty ){
        return addContructorArg( name, enumProperty, null, null, ScopeType.REQUEST, null, null );
    }

    public BeanBuilder addContructorArg( String name,
            String temporalProperty ){
        return addContructorArg( name, EnumerationType.ORDINAL, temporalProperty, null, ScopeType.REQUEST, null, null );
    }

    public BeanBuilder addContructorArg( String name,
            Type type ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null,ScopeType.REQUEST, null, type );
    }

    public BeanBuilder addStaticContructorArg( String name, String mapping ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.REQUEST, null, null );
    }

    public BeanBuilder addContructorArg( String name ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, ScopeType.REQUEST, null, null );
    }

    public BeanBuilder addContructorArg( String name, ScopeType scope ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, scope, null, null );
    }

    public BeanBuilder addContructorArg( String name, Object value ){
        return addContructorArg( name,
            EnumerationType.ORDINAL, "dd/MM/yyyy", null, ScopeType.REQUEST, value, null );
    }

    public BeanBuilder addContructorArg( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, Type type ){

        FieldBean fieldBean = this.createFieldBean(name, name, enumProperty,
                temporalProperty, mapping, scope, value, type);

        Configuration validatorConfig = new Configuration();
        fieldBean.setValidator( validatorProvider.getValidator( validatorConfig ) );
        this.mappingBean.getConstructor().getArgs().add(fieldBean);
        return this;
    }

    private FieldBean createFieldBean( String name, String propertyName,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, Type type ){

        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        propertyName = propertyName == null || propertyName.replace( " ", "" ).length() == 0? null : propertyName;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;

        if( propertyName == null )
            throw new BrutosException( "the property name is required!" );
        else
        if( this.mappingBean.getFields().containsKey( propertyName ) )
            throw new BrutosException( "duplicate property name: " + propertyName );

        /*
        if( name == null )
            throw new BrutosException( "name is required: " +
                    mappingBean.getClassType().getName() );
        */
        FieldBean fieldBean = new FieldBean();
        fieldBean.setEnumProperty( enumProperty );
        fieldBean.setParameterName( name );
        fieldBean.setName(propertyName);
        fieldBean.setTemporalType( temporalProperty );
        fieldBean.setValue(value);
        fieldBean.setScopeType( scope );

        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );

        if( mapping != null ){
            fieldBean.setMapping( mapping );

        }
        else
        if( type != null ){
            fieldBean.setType( type );
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
        }

        Configuration validatorConfig = new Configuration();
        fieldBean.setValidator( validatorProvider.getValidator( validatorConfig ) );
        return fieldBean;
    }
}
