package org.brandao.webchat.model;

import java.io.IOException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.SerializableType;
import org.brandao.brutos.web.http.ParameterList;
import org.brandao.jbrgates.JSONDecoder;
import org.brandao.jbrgates.JSONEncoder;

public class JSONType implements SerializableType, CollectionType{

    private Class classType;

    @Override
    public Class getClassType() {
        return classType;
    }

    @Override
    public void setClassType(Class classType) {
        this.classType = classType;
    }

    @Override
    public void setGenericType(Object genericType) {
    }

    @Override
    public Object getValue(Object value) {
        try{
            String v = null;

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
    public void setValue(Object value) throws IOException {
        ConfigurableApplicationContext app =
                (ConfigurableApplicationContext)Invoker.getApplicationContext();
        MvcResponse response = app.getMvcResponse();
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

}
