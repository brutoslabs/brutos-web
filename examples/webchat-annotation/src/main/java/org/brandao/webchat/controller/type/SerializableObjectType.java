/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.webchat.controller.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.SerializableType;
import org.brandao.brutos.web.http.ParameterList;
import org.brandao.jbrgates.JSONDecoder;
import org.brandao.jbrgates.JSONEncoder;

/**
 *
 * @author Brandao
 */
public class SerializableObjectType 
    implements SerializableType, CollectionType{

    private Class classType;
    
    @Override
    public void setClassType(Class classType) {
        this.classType = classType;
    }

    @Override
    public void setGenericType(Object genericType) {
    }

    @Override
    @Deprecated
    public Object getValue(Object value) {
        return null;
    }
    
    @Override
    public Object convert(Object value) {
        try{
            String v;

            if( value instanceof ParameterList )
                v = (String) ((ParameterList)value).get(0);
            else
            if( value instanceof String )
                v = (String)value;
            else
                return value;

            JSONDecoder decoder = new JSONDecoder( v );
            return decoder.decode( classType );
        }
        catch( Exception e ){
            return null;
        }
    }

    @Override
    @Deprecated
    public void setValue(Object value) throws IOException {
    }
    
    @Override
    public void show(MvcResponse response, Object value) throws IOException {
        response.setType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        JSONEncoder encoder = new JSONEncoder();
        encoder.encode(value);
        response.process(encoder.toString());
    }

    @Override
    public Object getGenericType() {
        return null;
    }

    @Override
    public Class getClassType() {
        return this.classType;
    }
    
}
