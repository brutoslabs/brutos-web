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
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.http.ParameterList;

/**
 * Allows the creation of type Set.
 * <p>The object type is determined by the org.brandao.brutos.type.set,
 * if not informed the type is java.util.HashSet.</p>
 * <p>If the value parameter of the method SetType.getValue() is an
 * instance of the org.brandao.brutos.http.ParameterList then their
 * values should be converted, otherwise there is no need for conversion.</p>
 * 
 * @author Afonso Brandao
 */
public class SetType implements CollectionType{

    private Class<? extends Set> listType;
    private Class<?> type;
    private Type primitiveType;
    private Type serializableType;

    public SetType(){
        BrutosContext context = BrutosContext.getCurrentInstance();
        String className = context
                .getConfiguration()
                    .getProperty( "org.brandao.brutos.type.set",
                                  "java.util.HashSet" );

        try{
            this.listType = (Class<? extends Set>)
                    Class.forName( className, true,
                                Thread.currentThread().getContextClassLoader());
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

        this.serializableType = Types.getType( Serializable.class );
    }
    
    @Override
    public void setGenericType(java.lang.reflect.Type type) {
        java.lang.reflect.Type classType = type;
        if( classType instanceof ParameterizedType ){
            this.type = (Class)((ParameterizedType)classType)
                                    .getActualTypeArguments()[0];
            this.primitiveType = Types.getType( this.type );
            if( this.primitiveType == null )
                throw new UnknownTypeException( ((Class)type).getName() );
        }
        else
            throw new UnknownTypeException( "is not allowed the use the Set or Set<?>" );
    }

    @Override
    public Object getValue(HttpServletRequest request, ServletContext context, Object value) {
        //Se value for instancia de ParameterList significa que
        //os dados ainda nao foram processados.
        if( value instanceof ParameterList )
            return getList(request, context, value);
            
        else
            return value;
    }

    private Set getList(HttpServletRequest request, ServletContext context, Object value){
        try{
            Set objList = this.listType.newInstance();
            
            for( Object o: (ParameterList)value )
                objList.add( this.primitiveType.getValue(request, context, o) );

            return objList;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    @Override
    public void setValue(HttpServletResponse response, ServletContext context, Object value) throws IOException {
        this.serializableType.setValue( response, context, value );
    }

    @Override
    public Class getClassType() {
        return Set.class;
    }


}
