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

import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 *
 * @author Afonso Brandao
 */
public class ActionBuilder {
    
    Form webFrame;
    MethodForm methodForm;
    ValidatorProvider validatorProvider;

    public ActionBuilder( MethodForm methodForm, Form controller, ValidatorProvider validatorProvider ) {
        this.webFrame = controller;
        this.methodForm = methodForm;
        this.validatorProvider = validatorProvider;
    }

    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty, Class classType ){
        return addParameter( name, scope, enumProperty, null, null, null, classType );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, String temporalProperty, Class classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, temporalProperty, null, null, classType );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, Type type ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type, type.getClassType() );
    }
    
    public ParameterBuilder addParameter( String name, EnumerationType enumProperty, Class classType ){
        return addParameter( name, ScopeType.REQUEST, enumProperty, null, null, null, classType );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, Class classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null, classType );
    }
    
    public ParameterBuilder addParameter( String name, String temporalProperty, Class classType ){
        return addParameter( name, ScopeType.REQUEST, EnumerationType.ORDINAL, temporalProperty, null, null, classType );
    }
    
    public ParameterBuilder addParameter( String name, Type type ){
        return addParameter( name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type, type.getClassType() );
    }
    
    public ParameterBuilder addParameterMapping( String mapping, Class classType ){
        return addParameter( null, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                mapping, null, classType );
    }
    
    public ParameterBuilder addParameter( String name, Class classType ){
        return addParameter( name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null, classType );
    }


    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type type, Class classType ){
        
        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;
        
        //if( methodForm.getParameters().size() > methodForm.getParametersType().size() )
        //    throw new BrutosException( "index > " + methodForm.getParametersType().size() );
        
        /*Class classType = methodForm.
                    getParametersType().get( methodForm.getParamterSize() );*/

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();

        useBean.setNome( name );
        useBean.setScopeType( scope );
        useBean.setValidate( validatorProvider.getValidator( validatorConfig ) );
        
        if( mapping != null ){
            if( webFrame.getMappingBeans().containsKey( mapping ) )
                useBean.setMapping( webFrame.getMappingBean( mapping ) );
            else
                throw new BrutosException( "mapping name " + mapping + " not found!" );
                
        }
        else
        if( type != null ){
            useBean.setType( type );
            if( !classType.isAssignableFrom( useBean.getType().getClassType() ) )
                throw new BrutosException( "expected " + classType.getName() + " found " + type.getClassType().getName() );
        }
        else{
            try{
                /*useBean.setType( Types.getType( methodForm.getGenericParameterType( methodForm.getParamterSize() ), enumProperty, temporalProperty ) );*/
                useBean.setType( Types.getType( classType ));
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException( 
                        String.format( "%s.%s(...) index %d : %s" ,
                            methodForm.getMethod().getDeclaringClass().getName(),
                            methodForm.getMethod().getName(),
                            methodForm.getParamterSize(),
                            e.getMessage() ) );
            }
            
            if( useBean.getType() == null )
                throw new UnknownTypeException( classType.getName() );
        }

        ParameterMethodMapping pmm = new ParameterMethodMapping();
        pmm.setBean( useBean );
        pmm.setParameterId( 0 );
        
        methodForm.addParameter( pmm );
        return new ParameterBuilder( validatorConfig );
    }

    public ActionBuilder addThrowable( Class target, String name ){
        return addThrowable( target, null, name, DispatcherType.FORWARD );
    }

    public ActionBuilder addThrowable( Class target, String view, String name, DispatcherType dispatcher ){
        view = view == null || view.replace( " ", "" ).length() == 0? null : view;
        name = name == null || name.replace( " ", "" ).length() == 0? null : name;

        if( target == null )
            throw new BrutosException( "target is required: " + webFrame.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(name);
        thr.setTarget(target);
        thr.setUri(view);
        thr.setRedirect( false );
        thr.setDispatcher(dispatcher);
        methodForm.setThrowsSafe(thr);
        return this;
    }

}
