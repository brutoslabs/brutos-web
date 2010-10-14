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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brandao
 */
public class MethodBuilder {
    
    Form webFrame;
    MethodForm methodForm;
    
    public MethodBuilder( MethodForm methodForm, Form webFrame ) {
        this.webFrame = webFrame;
        this.methodForm = methodForm;
    }

    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty ){
        return addParameter( name, scope, enumProperty, null, null, null );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, String temporalProperty ){
        return addParameter( name, scope, EnumerationType.ORDINAL, temporalProperty, null, null );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, Type type ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }
    
    public ParameterBuilder addParameter( String name, EnumerationType enumProperty ){
        return addParameter( name, ScopeType.REQUEST, enumProperty, null, null, null );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null );
    }
    
    public ParameterBuilder addParameter( String name, String temporalProperty ){
        return addParameter( name, ScopeType.REQUEST, EnumerationType.ORDINAL, temporalProperty, null, null );
    }
    
    public ParameterBuilder addParameter( String name, Type type ){
        return addParameter( name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }
    
    public ParameterBuilder addParameterMapping( String mapping ){
        return addParameter( null, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                mapping, null );
    }
    
    public ParameterBuilder addParameter( String name ){
        return addParameter( name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null );
    }


    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type type ){
        
        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;
        
        if( methodForm.getParameters().size() > methodForm.getParametersType().size() )
            throw new BrutosException( "index > " + methodForm.getParametersType().size() );
        
        Class classType = methodForm.
                    getParametersType().get( methodForm.getParamterSize() );

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();

        useBean.setNome( name );
        useBean.setScopeType( scope );
        useBean.setValidate( BrutosContext
                    .getCurrentInstance().getValidatorProvider()
                        .getValidator( validatorConfig ) );
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
                useBean.setType( Types.getType( methodForm.getGenericParameterType( methodForm.getParamterSize() ), enumProperty, temporalProperty ) );
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
        pmm.setParameterName( methodForm.getParameters().size() + 1 );
        
        methodForm.getParameters().add( pmm );
        return new ParameterBuilder( validatorConfig, methodForm, webFrame );
    }

    public MethodBuilder addThrowable( Class target, String parameterName ){
        return addThrowable( target, null, parameterName, false );
    }

    public MethodBuilder addThrowable( Class target, String uri, String parameterName, boolean redirect ){
        uri = uri == null || uri.replace( " ", "" ).length() == 0? null : uri;
        parameterName = parameterName == null || parameterName.replace( " ", "" ).length() == 0? null : parameterName;

        if( target == null )
            throw new BrutosException( "target is required: " + webFrame.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(parameterName);
        thr.setTarget(target);
        thr.setUri(uri);
        thr.setRedirect( redirect );
        methodForm.setThrowsSafe(thr);
        return this;
    }

}
