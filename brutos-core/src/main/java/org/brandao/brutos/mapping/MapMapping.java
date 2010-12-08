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
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Afonso Brandao
 */
public class MapMapping extends MappingBean{

    private Class<?> collectionType;

    private MappingBean bean;

    private String key;

    private Type keyType;

    private ScopeType keyScopeType;

    public MapMapping( Form form ){
        super(form);
    }
    
    public void setKey( String name, Type type, ScopeType scope ){
        this.key = name;
        this.keyType = type;
        this.keyScopeType = scope;
    }

    public Class<?> getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Class<?> collectionType) {
        this.collectionType = collectionType;
    }

    public MappingBean getBean() {
        return bean;
    }

    public void setBean(MappingBean bean) {
        this.bean = bean;
    }

    private Object get( HttpServletRequest request, String prefix, long index ){
        if( bean == null )
            return super.getValue(request, null, prefix, index );
        else
            return bean.getValue(request, null, prefix, index );
    }
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
    
    private Object getKey( HttpServletRequest request, long index, String prefix ){
        /*return keyType.getValue(
            request,
            request.getSession().getServletContext(),
            getKeyScope().get( getKeyName( index, prefix ) ) );*/
        return keyType.getValue( getKeyScope().get( getKeyName( index, prefix ) ) );
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

    public Object getValue( HttpServletRequest request ){
        return getValue( request, null, null );
    }

    public Object getValue( HttpServletRequest request, Object instance, String prefix ){
        try{
            instance = instance == null? collectionType.newInstance() : instance;
            Map map = (Map)instance;

            long index = 0;
            Object bean;
            
            while( (bean = get( request, prefix, index )) != null ){
                map.put( getKey( request, index, prefix ), bean );
                index++;
            }
            return map.size() == 0? null : map;
        }
        catch( Exception e ){
            return null;
        }
    }

    public Scope getKeyScope() {
        Scope objectScope = Scopes.get( keyScopeType.toString() );

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
