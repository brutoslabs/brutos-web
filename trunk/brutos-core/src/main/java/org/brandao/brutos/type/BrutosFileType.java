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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.http.BrutosFile;
import org.brandao.brutos.web.WebMvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class BrutosFileType implements Type{

    public BrutosFileType() {
    }

    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof BrutosFile )
            return value;
        else
            return null;
    }

    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        if( value instanceof BrutosFile ){
            BrutosFile f = (BrutosFile)value;
            InputStream in   = new FileInputStream( f.getFile() );
            OutputStream out = response.getOutputStream();
            
            if( f.getFile() != null ){
                response.addHeader(
                    "Content-Disposition",
                    "attachment;filename=" + f.getFileName() + ";"
                );
            }
            
            response.setContentLength( (int)f.getFile().length() );
            
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
        return BrutosFile.class;
    }

    public Object getValue(Object value) {
        if( value instanceof BrutosFile )
            return value;
        else
            return null;
    }

    public void setValue(Object value) throws IOException {
        if( value instanceof BrutosFile ){
            ApplicationContext app = ApplicationContext.getCurrentApplicationContext();
            MvcResponse response = app.getMvcResponse();

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
