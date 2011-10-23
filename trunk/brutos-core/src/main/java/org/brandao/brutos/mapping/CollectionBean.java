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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Afonso Brandao
 */
public class CollectionBean extends Bean{

    /**
     * @deprecated 
     */
    private Class<?> collectionType;

    private Bean bean;

    public CollectionBean( Controller form ){
        super( form );
    }

    /**
     * @deprecated
     * @return .
     */
    public Class<?> getCollectionType() {
        return collectionType;
    }

    /**
     * @deprecated 
     * @param collectionType
     */
    public void setCollectionType(Class<?> collectionType) {
        this.collectionType = collectionType;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    protected Object get( String prefix, long index, ValidatorException exceptionHandler ){
        /*
         * A partir da vers�o 2.0 o bean sempre ser� diferente de null.
         */
        if( bean == null )
            //return super.getValue( null, prefix, index, false );
            throw new MappingException(
                String.format(
                    "element of the collection is not defined: %s",
                    this.getName()));
        else
            return bean.getValue( null, prefix, index, exceptionHandler, false );
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

    public Object getValue( boolean force ){
        return getValue( null, null, -1, null, force );
   }

    public Object getValue( Object instance ){
        return getValue( instance, null, -1, null, false );
    }

    public Object getValue(){
        return getValue( null );
    }

    public Object getValue( Object instance, String prefix, long otherIndex, 
            ValidatorException exceptionHandler, boolean force){
        try{
            
            ValidatorException vex =
                exceptionHandler == null?
                    new ValidatorException() :
                    exceptionHandler;

            instance = getInstance( instance,prefix,otherIndex,vex,force);
            Collection collection = (Collection)instance;

            long index = 0;
            Object beanInstance;

            while( (beanInstance = get( prefix, index, vex )) != null ){
                collection.add(beanInstance);
                index++;
            }

            if(!collection.isEmpty() || force){
                if( vex != exceptionHandler && !vex.getCauses().isEmpty())
                    throw vex;
                else
                    return collection;
            }
            else
                return null;

            //return force || !collection.isEmpty()? collection : null;
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

    protected Object getInstance( Object instance, String prefix, long index, 
            ValidatorException exceptionHandler, boolean force )
            throws InstantiationException, IllegalAccessException{

        if( instance == null ){
            if( collectionType == null )
                instance = super.getValue(instance,prefix,index,exceptionHandler,force);
            else
                instance = collectionType.newInstance();
        }

        /*
        instance = instance == null?
            (collectionType != null?
                   collectionType.newInstance() :
                   super.getValue()) :
            instance;
        */
        
        return instance;
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
