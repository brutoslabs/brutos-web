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

import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Afonso Brandao
 */
public class CollectionMapping extends MappingBean{

    private Class<?> collectionType;

    private MappingBean bean;

    public CollectionMapping( Form form ){
        super( form );
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

    public Object getValue(){
        return getValue( null );
    }

    public Object getValue( HttpServletRequest request ){
        return getValue( request, null, null );
    }

    public Object getValue( HttpServletRequest request, Object instance, String prefix ){
        try{
            instance = instance == null? collectionType.newInstance() : instance;
            Collection collection = (Collection)instance;

            long index = 0;
            Object bean;

            while( (bean = get( request, prefix, index )) != null ){
                collection.add(bean);
                index++;
            }
            return collection.size() == 0? null : collection;
        }
        catch( Exception e ){
            return null;
        }
    }

    public boolean isBean(){
        return false;
    }

    public boolean isCollection(){
        return true;
    }

    public boolean isMap(){
        return false;
    }

    /*
    public Object getValue( HttpSession session ){
        try{
            Collection collection = (Collection)collectionType.newInstance();

            long index = 0;
            Object bean;

            while( (bean = get( session, index )) != null ){
                collection.add(bean);
                index++;
            }
            return collection;
        }
        catch( Exception e ){
            return null;
        }
    }


    public Object getValue( ServletContext context ){
        try{
            Collection collection = (Collection)collectionType.newInstance();

            long index = 0;
            Object bean;

            while( (bean = get( context, index )) != null ){
                collection.add(bean);
                index++;
            }
            return collection;
        }
        catch( Exception e ){
            return null;
        }
    }
    */
}
