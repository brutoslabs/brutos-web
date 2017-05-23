/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.web.type;

import java.io.IOException;
import java.io.OutputStream;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.SerializableType;
import org.brandao.brutos.web.MediaType;
import org.brandao.jbrgates.DefaultJSONContext;
import org.brandao.jbrgates.JSONContext;

/**
 * 
 * @author Brandao
 */
public class JSONType extends SerializableType {

	private static JSONContext jsonContext = new DefaultJSONContext();
	
    public Object convert(Object value) {
         try{
            if( value instanceof String ){
                //JSONDecoder decoder = new JSONDecoder( (String)value );
                //return decoder.decode( classType );
                return jsonContext.decode( (String)value, classType );
            }
            else
                return value;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
   }

    public void show(MvcResponse response, Object value) throws IOException {
        response.setType(MediaType.valueOf("application/json") );
        //response.setCharacterEncoding( "UTF-8" );
        //JSONEncoder encoder = new JSONEncoder( response.processStream() );
        //encoder.encode( value );
        String str = jsonContext.encode(value);
        OutputStream out = response.processStream(); 
        out.write(str.getBytes());
        out.flush();
    }

}