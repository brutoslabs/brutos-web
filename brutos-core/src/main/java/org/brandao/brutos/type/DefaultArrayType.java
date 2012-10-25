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

package org.brandao.brutos.type;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.ParameterList;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultArrayType implements ArrayType{

    private org.brandao.brutos.type.Type componentType;
    private org.brandao.brutos.type.Type serializableType;
    private Class classType;
    private Class arrayComponentType;

    public DefaultArrayType(){
        this.serializableType = TypeManager.getType( Serializable.class );
    }

    public void setContentType(Class type) {
        this.arrayComponentType = type;
        this.componentType = TypeManager.getType( type );
   }

    public Class getClassType() {
        return this.classType;
    }

    private Object getList(Object value){
        try{
            ParameterList param = (ParameterList)value;
            Object objList = Array.newInstance( arrayComponentType , param.size() );

            for( int i=0;i<param.size();i++ )
                Array.set(
                    objList,
                    i,
                    componentType.convert( param.get( i ) )
                    //componentType.getValue(request, context, param.get( i ) )
                );

            return objList;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Object getValue(Object value) {
        return null;
    }
    
    public Object convert(Object value) {
        if( value instanceof ParameterList )
            return getList(value);

        else
            return value;
    }

    public void setValue(Object value) throws IOException {
    }
    
    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }

}
