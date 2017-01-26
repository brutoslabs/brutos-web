


package org.brandao.brutos.web.type;

import java.io.IOException;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.SerializableType;
import org.brandao.jbrgates.JSONDecoder;
import org.brandao.jbrgates.JSONEncoder;


public class JSONType extends SerializableType {

    public Object convert(Object value) {
         try{
            if( value instanceof String ){
                JSONDecoder decoder = new JSONDecoder( (String)value );
                return decoder.decode( classType );
            }
            else
                return value;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
   }

    public void show(MvcResponse response, Object value) throws IOException {
        response.setType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        JSONEncoder encoder = new JSONEncoder( response.processStream() );
        encoder.encode( value );
    }

}