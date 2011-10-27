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

package org.brandao.brutos.mapping;

import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Afonso Brandao
 */
public class MapBean extends CollectionBean{

    private Bean mappingKey;

    /**
     * @deprecated
     */
    private Class collectionType;

    /**
     * @deprecated
     */
    private Bean bean;

    /**
     * @deprecated
     */
    private String key;

    /**
     * @deprecated
     */
    private Type keyType;

    /**
     * @deprecated
     */
    private ScopeType keyScopeType;

    public MapBean( Controller form ){
        super(form);
    }

    public void setMappingKey( Bean mappingKey ){
        this.mappingKey = mappingKey;
    }

    public void setKey( String name, Type type, ScopeType scope ){
        this.key = name;
        this.keyType = type;
        this.keyScopeType = scope;
    }

    public Class getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Class collectionType) {
        this.collectionType = collectionType;
    }
    /*
    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }
    */
    /*
    private Object get( HttpServletRequest request, String prefix, long index ){
        if( bean == null )
            return super.getValue(request, null, prefix, index );
        else
            return bean.getValue(request, null, prefix, index );
    }
    */
    
    /*
    private Object get( HttpSession session, long index ){
        if( bean == null )
            return super.getValue( session, index );
        else
            return bean.getValue( session, index );
    }

    private Object get( ServletContext context, long index ){
        if( bean == null )
            return super.getValue(context, index );
        else
            return bean.getValue(context, index );
    }
    */
    private String getKeyName( long index, String prefix ){
        return (prefix != null? prefix : "") + key + ( index < 0? "" : "[" + index + "]" );
    }
    
    private Object getKey( long index, String prefix,
            ValidatorException exceptionHandler ){
        /*return keyType.getValue(
            request,
            request.getSession().getServletContext(),
            getKeyScope().get( getKeyName( index, prefix ) ) );*/
        /*
         * A partir da vers�o 2.0 mappingKey sempre ser� diferente de null.
         */
        if( mappingKey != null )
            return mappingKey.getValue( null, prefix, index, exceptionHandler, false );
        else
        if( keyType != null )
            return keyType.getValue( getKeyScope().get( getKeyName( index, prefix ) ) );
        else
            throw new BrutosException(
                String.format("key mapping not defined: %s", new Object[]{this.getName()} ) );
    }
    /*
    private Object getKey( HttpServletRequest request, long index ){
        return keyType.getValue(
            request,
            request.getSession().getServletContext(),
            request.getParameter( getKeyName( index ) ) );
    }

    private Object getKey( HttpSession session, long index ){
        return session.getAttribute( getKeyName( index ) );
    }

    private Object getKey( ServletContext context, long index ){
        return context.getAttribute( getKeyName( index ) );
    }
    */

    public Object getValue(){
        return getValue( null );
    }

    public Object getValue( Object instance ){
        return getValue( instance, null, -1, null, false );
    }

    public Object getValue( boolean force ){
        return getValue( null, null, -1, null, force );
   }
    
    public Object getValue( Object instance, String prefix, long otherIndex, 
            ValidatorException exceptionHandler, boolean force ){
        try{

            ValidatorException vex = new ValidatorException();

            instance = getInstance( instance, prefix, otherIndex,
                        vex, force);
            
            Map map = (Map)instance;

            long index = 0;
            Object beanInstance;
            
            while( (beanInstance = get( prefix, index, vex )) != null ){

                Object keyInstance = getKey( index, prefix, vex );

                if( keyInstance != null )
                    map.put( keyInstance, beanInstance );
                
                index++;
            }


            if(!map.isEmpty() || force){
                if( exceptionHandler == null){
                    if( !vex.getCauses().isEmpty() )
                        throw vex;
                    else
                        return map;
                }
                else {
                    exceptionHandler.addCauses(vex.getCauses());
                    return map;
                }
            }
            else
                return null;

            //return force || !map.isEmpty()? map : null;
        }
        catch( ValidatorException e ){
            throw e;
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    public Scope getKeyScope() {
        Scopes scopes = Invoker.getApplicationContext().getScopes();

        Scope objectScope = scopes
                .get( keyScopeType.toString() );

        if( objectScope == null )
            throw new BrutosException( "scope not allowed in context: " + keyScopeType );

        return objectScope;
    }

    public void setScopeType(ScopeType scope) {
        this.keyScopeType = scope;
    }

    public ScopeType getkeyScopeType() {
        return this.keyScopeType;
    }

    public boolean isBean(){
        return false;
    }

    public boolean isCollection(){
        return false;
    }

    public boolean isMap(){
        return true;
    }

    /*
    public Object getValue( HttpSession session ){
        try{
            Map map = (Map) collectionType.newInstance();

            long index = 0;
            Object bean;
            while( (bean = get( session, index )) != null ){
                map.put( getKey( session, index ), bean );
                index++;
            }
            return map;
        }
        catch( Exception e ){
            return null;
        }
    }


    public Object getValue( ServletContext context ){
        try{
            Map map = (Map) collectionType.newInstance();

            long index = 0;
            Object bean;
            while( (bean = get( context, index )) != null ){
                map.put( getKey( context, index ), bean );
                index++;
            }
            return map;
        }
        catch( Exception e ){
            return null;
        }
    }
    */
}
