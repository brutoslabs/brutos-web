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

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Afonso Brandao
 */
public class FieldBuilder {
    
    Controller webFrame;
    private WebFrameBuilder webFrameManager;
    
    public FieldBuilder( Controller webFrame ) {
        this.webFrame = webFrame;
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, ScopeType scope, EnumerationType enumProperty ){
        return addProperty( propertyName, name, scope, enumProperty, null, null, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, ScopeType scope, String temporalProperty ){
        return addProperty( propertyName, name, scope, EnumerationType.ORDINAL, temporalProperty, null, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, ScopeType scope, Type type ){
        return addProperty( propertyName, name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, EnumerationType enumProperty ){
        return addProperty( propertyName, name, ScopeType.PARAM, enumProperty, null, null, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, ScopeType scope ){
        return addProperty( propertyName, name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, String temporalProperty ){
        return addProperty( propertyName, name, ScopeType.PARAM, EnumerationType.ORDINAL, temporalProperty, null, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, Type type ){
        return addProperty( propertyName, name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }
    
    public WebFrameBuilder addPropertyMapping( String propertyName, String mapping ){
        return addProperty( propertyName, null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    public WebFrameBuilder addPropertyMapping( String propertyName, String name, String mapping ){
        return addProperty( propertyName, name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name ){
        return addProperty( propertyName, name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }
    
    public WebFrameBuilder addProperty( String propertyName, String name, ScopeType scope, EnumerationType enumProperty, 
            String temporalProperty, String mapping, Type type ){
        
        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
        propertyName = 
            propertyName == null || propertyName.replace( " ", "" ).length() == 0?
                null :
                propertyName;

        temporalProperty = 
            temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0?
                null :
                temporalProperty;

        mapping =
            mapping == null || mapping.replace( " ", "" ).length() == 0?
                null :
                mapping;
        
        if( name == null )
            throw new BrutosException( "name is required: " +
                    webFrame.getClassType().getName() );

        if( propertyName == null )
            throw new BrutosException( "property name is required: " +
                    webFrame.getClassType().getName() );

        UseBeanData useBean = new UseBeanData();
        useBean.setNome( name );
        useBean.setScopeType( scope );

        PropertyController fieldBean = new PropertyController();
        fieldBean.setBean( useBean );
        fieldBean.setName(propertyName);

        
        BeanInstance bean = new BeanInstance( null, webFrame.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                webFrame.getClassType().getName() + "." + propertyName );
        
        
        if( mapping != null ){
            if( webFrame.getBean( mapping ) != null )
                useBean.setMapping( webFrame.getBean( mapping ) );
            else
                throw new BrutosException( "mapping not found: " + mapping );
                
        }
        else
        if( type != null )
            useBean.setType( type );
        else{
            try{
                useBean.setType(
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
        }
               
        if( webFrame.containsProperty( fieldBean.getName() ) )
            throw new BrutosException( "property already defined: " +
                    webFrame.getClassType().getName() + "." + propertyName );
        
        webFrame.addProperty( fieldBean );
        
        return getWebFrameManager();
    }

    public WebFrameBuilder getWebFrameManager() {
        return webFrameManager;
    }

    public void setWebFrameManager(WebFrameBuilder webFrameManager) {
        this.webFrameManager = webFrameManager;
    }
    
}
