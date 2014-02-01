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
import java.util.Collection;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.ParameterList;

/**
 * Classe base para tipos de coleções.
 * 
 * @author Brandao
 */
public abstract class AbstractCollectionType 
    implements CollectionType{
    
    protected Class collectionType;
    protected Class type;
    protected Type primitiveType;
    protected Type serializableType;
    
    /**
     * Cria um novo tipo de coleção.
     * @param collectionType Tipo de coleção.
     */
    public AbstractCollectionType(Class collectionType){
        this.serializableType = TypeManager.getType( Serializable.class );
        this.collectionType = collectionType;
    }
    
    /**
     * @see CollectionType#setGenericType(java.lang.Object) 
     * 
     */
    public void setGenericType(Object classType) {
        
        Object collectionGenericType = TypeManager.getCollectionType(classType);
        
        if(collectionGenericType != null){
            Class collectionType = TypeManager.getRawType(collectionGenericType);
            if( collectionType != null ){
                this.type = collectionType;
                this.primitiveType = TypeManager.getType( this.type );
                if( this.primitiveType == null )
                    throw new UnknownTypeException( classType.toString() );
            }
        }
        
    }
    
    /**
     * @see CollectionType#getGenericType()
     */
    public Object getGenericType(){
        return this.type;
    }
    
    /**
     * Obtém uma lista de objetos.
     * @param value Coleção de objetos
     * @return Lista.
     */
    protected Collection getCollection(Object value){

        if( this.type == null )
            throw new UnknownTypeException( "invalid collection type" );

        try{
            Collection collection = (Collection)ClassUtil.getInstance(this.collectionType);

            ParameterList list = (ParameterList)value;
            int size = list.size();
            for( int i=0;i<size;i++ ){
                Object o = list.get(i);
                collection.add( this.primitiveType.convert(o) );
            }
            return collection;
        }
        catch( Throwable e ){
            throw new BrutosException( e );
        }
    }
    
    public Object convert(Object value) {
        if( value instanceof ParameterList )
            return getCollection(value);
        else
            return value;
    }

    public void show(MvcResponse response, Object value) throws IOException {
        this.serializableType.show( response, value );
    }
    
}
