/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.BrutosFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Afonso Brandao
 */
public class BrutosFileType implements Type{

    public BrutosFileType() {
    }
    
    public Class getClassType() {
        return BrutosFile.class;
    }

    /**
     * @deprecated 
     * @param value
     * @return 
     */
    public Object getValue(Object value) {
        if( value instanceof BrutosFile )
            return value;
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    public void setValue(Object value) throws IOException {
    }

    public Object convert(Object value) {
        return getValue(value);
    }

    public void show(MvcResponse response, Object value) throws IOException{
        if( value instanceof BrutosFile ){
            BrutosFile f = (BrutosFile)value;

            if( f.getFile() != null ){
                response.setInfo(
                    "Content-Disposition",
                    "attachment;filename=" + f.getFileName() + ";"
                );
            }

            response.setLength( (int)f.getFile().length() );

            InputStream in   = new FileInputStream( f.getFile() );
            OutputStream out = response.processStream();


            try{
                byte[] buffer = new byte[ 3072 ];
                int length;

                while( (length = in.read( buffer )) != -1 )
                    out.write( buffer, 0, length );
            }
            finally{
                if( in != null )
                    in.close();
            }
        }
    }
}
