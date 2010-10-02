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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.http.BrutosFile;

/**
 *
 * @author Afonso Brandao
 */
public class FileType implements Type{

    public FileType() {
    }

    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof BrutosFile )
            return ((BrutosFile)value).getFile();
        else
            return null;
    }

    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        if( value instanceof File ){
            File f = (File)value;
            InputStream in   = new FileInputStream( f );
            OutputStream out = response.getOutputStream();
            
            response.addHeader(
                "Content-Disposition",
                "attachment;filename=" + f.getName() + ";"
            );
            response.setContentLength( (int)f.length() );
            
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
    
    public Class getClassType() {
        return File.class;
    }
    
}
