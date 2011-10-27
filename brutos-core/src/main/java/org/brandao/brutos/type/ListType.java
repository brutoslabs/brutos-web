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

package org.brandao.brutos.type;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.web.http.ParameterList;

/**
 * Allows the creation of type List.
 * <p>The object type is determined by the org.brandao.brutos.type.list,
 * if not informed the type is java.util.ArrayList.</p>
 * <p>If the value parameter of the method ListType.getValue() is an
 * instance of the org.brandao.brutos.http.ParameterList then their
 * values should be converted, otherwise there is no need for conversion.</p>
 * 
 * @author Afonso Brandao
 */
public class ListType implements CollectionType{

    private Class listType;
    private Class type;
    private Type primitiveType;
    private Type serializableType;
    
    public ListType(){
    }

    private Class getListType(){

        if( this.listType != null )
            return this.listType;

        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext)Invoker.getApplicationContext();

        String className = context
                .getConfiguration()
                    .getProperty( "org.brandao.brutos.type.list",
                                  "java.util.ArrayList" );

        try{
            this.listType = (Class)
                    Class.forName( className, true,
                                Thread.currentThread().getContextClassLoader());
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

        this.serializableType = Types.getType( Serializable.class );

        return this.listType;
    }

    public void setGenericType(Object classType) {
        Class collectionType = Types.getCollectionType(classType);
        if( collectionType != null ){
            this.type = collectionType;
            this.primitiveType = Types.getType( this.type );
            if( this.primitiveType == null )
                throw new UnknownTypeException( classType.toString() );
        }
        //else
        //    throw new UnknownTypeException( "is not allowed the use the List or List<?>" );
    }

    public Object getGenericType(){
        return this.type;
    }
    
    private List getList(Object value){

        if( this.type == null )
            throw new UnknownTypeException( "is not allowed the use the List or List<?>" );

        try{
            List objList = (List)this.getListType().newInstance();

            ParameterList list = (ParameterList)value;
            int size = list.size();
            //for( Object o: (ParameterList)value )
            for( int i=0;i<size;i++ ){
                Object o = list.get(i);
                objList.add( this.primitiveType.getValue(o) );
                //objList.add( this.primitiveType.getValue(request, context, o) );
            }
            return objList;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    public Class getClassType() {
        return List.class;
    }

    public Object getValue(Object value) {
        if( value instanceof ParameterList )
            return getList(value);

        else
            return value;
    }

    public void setValue(Object value) throws IOException {
        this.serializableType.setValue( value );
    }


}
