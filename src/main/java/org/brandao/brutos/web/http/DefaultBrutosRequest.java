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

package org.brandao.brutos.web.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import org.brandao.brutos.BrutosException;
import org.brandao.jbrgates.JSONDecoder;

/**
 * 
 * @author Brandao
 */
public class DefaultBrutosRequest extends ServletRequestWrapper implements BrutosRequest{

    private Map parameters;
    private DefaultUploadEvent uploadEvent;
    private UploadListener uploadListener;

    private long maxLength;
    private String path;
    
    public DefaultBrutosRequest( ServletRequest request ){
        super( request );
        this.parameters = new HashMap();
        uploadEvent = new DefaultUploadEvent( this );
        initialize();
    }

    public UploadListener getUploadListener(){
    
        throw new UnsupportedOperationException();
    }
    
    private void initialize(){
        throw new UnsupportedOperationException();
        
    }
    
    public void parseRequest(){
        try{
            if( uploadEvent.isMultipart() ){
                try{
                    uploadListener.uploadStarted();
                    uploadEvent.setMaxLength( maxLength );
                    uploadEvent.setPath(path);
                    uploadEvent.start();
                    while( uploadEvent.hasMoreElements() ){
                        Input input = uploadEvent.nextElement();
                        this.setObject( input.getName(), input.getValue() );
                    }
                }
                finally{
                    uploadListener.uploadFinished();
                }
            }
            else
            if( "application/json".equals( this.getContentType() ) ){
                BufferedReader reader = super.getReader();
                String line = null;
                StringBuilder result = new StringBuilder();
                while( (line = reader.readLine()) != null ){
                    result.append( line );
                }
                String json = URLDecoder.decode(result.toString(), "UTF-8");
                JSONDecoder decoder = new JSONDecoder( json );
                Map data = (Map) decoder.decode(Map.class);
                if( data != null ){
                    for( Object o: data.keySet() ){
                        this.setParameter(String.valueOf(o), String.valueOf(data.get(o)));
                    }
                }
            }
        }
        catch( BrutosException e ){
            parameters.clear();
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public long getCurrentDataSize(){
        return uploadEvent.isMultipart()? uploadEvent.getCurrentDataSize() : super.getContentLength();
    }
    
    public Object getObject(String name) {
        if( parameters.containsKey( name ) )
            return getParameter0( name );
        else
            return super.getParameter( name );
    }

    private Object getParameter0( String value ){
        List<Object> values = (List)parameters.get( value );
        return values.get( 0 );
    }

    
    public String getParameter( String name ){
        if( parameters.containsKey( name ) )
            return String.valueOf( getParameter0( name ) );
        else
            return super.getParameter( name );
    }

    public String[] getParameterValues( String name ){
        if( parameters.containsKey( name ) )
            return getParameterValues0( name );
        else
            return super.getParameterValues(name);

    }
    
    private String[] getParameterValues0( String name ){
        List<Object> values = (List<Object>) parameters.get( name );
        String[] result = new String[ values.size() ];
        for( int i=0;i<values.size();i++ )
            result[i] = String.valueOf( values.get( i ) );

        return result;
    }

    public void setObject(String name, Object value) {
        if( value != null ){
            List<Object> values = (List)parameters.get( name );
            if( values == null ){
                values = new ParameterList();
                parameters.put( name, values );
            }

            if( value != null )
                values.add( value );
        }
    }

    public void setParameter(String name, String value) {
        setObject( name, value );
    }

    public List<Object> getObjects(String name) {
        if( parameters.containsKey( name ) )
            return (List<Object>) parameters.get( name );
        else{
            String[] values = super.getParameterValues( name );
            if( values == null )
                return null;
            else
                return new ParameterList( Arrays.asList( values ) );
        }
    }

    public void setParameters(String name, String[] values) {
        for( String value: values )
            this.setParameter(name, value);
    }

    public void setObjects(String name, Object[] values) {
        for( Object value: values )
            this.setObject(name, value);
    }

    public ServletRequest getServletRequest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRequestId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setServletRequest(ServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }




    private class DefaultUploadEvent implements java.util.Enumeration<Input>, UploadEvent{

        private DefaultBrutosRequest request;
        private byte[] buffer;
        private int len;
        //private long length;
        private ServletInputStream in;
        private String boundary;
        private boolean noFields;

        private long maxLength;
        private String path;
        private long currentDataSize;

        public DefaultUploadEvent( DefaultBrutosRequest request ){
            this.request         = request;
            this.buffer          = new byte[ 8192 ];
            this.noFields        = false;
            this.currentDataSize = 0;
        }

        public boolean isMultipart(){
            String tmp = request.getContentType();//.getHeader( "content-type" );
            if( tmp == null )
                return false;

            int i = tmp.indexOf( "multipart/form-data" );
            return i != -1;
        }

        private String getboundary() throws IOException{

            String content_type = request.getContentType();//.getHeader( "content-type" );

            int posboundary = content_type.indexOf( "boundary" );

            if( posboundary == -1 )
                throw new IOException("Boundary not found");

            return "--" + content_type.substring( posboundary + 9 );

        }

        private int readData( byte[] buf, ServletInputStream in ) throws IOException{
            len = in.readLine( buf, 0, buf.length );
            currentDataSize += len;

            if( maxLength > 0 && getCurrentDataSize() > maxLength )
                throw new BrutosException( "data too large" );

            return len;
        }

        public void start() throws IOException {
            in       = request.getInputStream();
            len      = -1;
            boundary = getboundary();

            if( ( len = readData( buffer, in ) ) != 0 ){
                String s = new String( buffer, 0, len-2 );
                noFields = !s.endsWith( boundary );
            }
            else
                noFields = true;

        }

        public boolean hasMoreElements(){
            if( noFields )
                return false;

            try{
                len = -1;
                len = readData( buffer, in );
                if( len == -1 )
                    return false;
                else
                    return true;
            }
            catch( Exception e ){
                throw new BrutosException( e );
            }
        }

        public Input nextElement() {
            try{
                String header = new String( buffer, 0, len-2 );
                String name   = null;
                Object value  = null;

                if( header.indexOf( "filename" ) != -1 ){
                    name  = getName( header, "name" );
                    value = getFieldFile( boundary, header, in );
                }
                else{
                    name  = getName( header, "name" );
                    value = getField( boundary, in );
                }

                return new Input( name, value );
            }
            catch( Exception e ){
                throw new BrutosException( e );
            }
        }

        private String getName( String cab, String name ){
            int initId = cab.indexOf( name );
            int initValor = cab.indexOf( "\"", initId );
            int endValor = cab.indexOf( "\"", initValor + 1 );
            String id = cab.substring( initValor + 1, endValor);
            return id;
        }

        private UploadedFile getFieldFile( String boundary, String header, 
                ServletInputStream in ) throws IOException{
            
            len = -1;
            String fileName  = getName( header, "filename" );

            len = readData( buffer, in );

            if( len == -1 )
                throw new IOException( "not found type of the file!" );

            String typeFile  = (new String( buffer, 0, len-2 ));
            typeFile = typeFile.length() < 14? "" : typeFile.substring( 14 );


            String file = "";
            String dir = fileName;
            char c;
            while( dir.length() > 0 && ( c = dir.charAt( dir.length() - 1 ) )!= '\\' && c != '/' ){
                file = c + file;
                dir = dir.substring( 0, dir.length() - 1 );
            }

            file = "".equals(file)? null : file;

            len = readData( buffer, in );
            String s = new String( buffer, 0, len-2 );

            int filesize = 0;

            FileOutputStream fout = null;
            UploadedFile f = null;
            try{
                while( s.indexOf( boundary ) == -1 ){

                    len = readData( buffer, in );
                    s = new String( buffer, 0, len);

                    if ( file != null && s.indexOf( boundary ) == -1 ){

                        if( fout == null ){
                            File arquivo = getFile( this.path, file );
                            f = new UploadedFileImp( arquivo );
                            f.setFileName( file );
                            fout = new FileOutputStream( arquivo );
                        }

                        fout.write( buffer, 0, len );
                        filesize = filesize + len;
                    }
                }
            }
            finally{
                if( fout != null )
                    fout.close();
            }

            return f;

        }

        private File getFile( String dir, String file ) throws IOException{
            java.io.File arquivo;

            if( dir != null ){
                arquivo = new File( new File(dir), file );
                arquivo.createNewFile();
            }
            else{
                arquivo = File.createTempFile("multpart",".tmp");
                arquivo.deleteOnExit();
            }
            return arquivo;
        }

        private String getField( String boundary, ServletInputStream in ) throws IOException{
            len          = -1;
            String value = "";

            len = readData( buffer, in );
            String s = new String( buffer, 0, len-2 );

            while( !s.equals( boundary ) && !s.equals( boundary + "--" ) ){
                if( s.length() != 0 )
                    value += ( value.length() == 0 )? s : s + "\n";

                len = readData( buffer, in );
                s = new String( buffer, 0, len-2);
            }
            return value;
        }

        public long getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(long maxLength) {
            this.maxLength = maxLength;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public long getCurrentDataSize() {
            return currentDataSize;
        }

        public long getContentLength() {
            return request.getContentLength();
        }

        public long getBytesRead() {
            return this.getCurrentDataSize();
        }

    }

    private class Input{

        private String name;
        private Object value;

        public Input( String name, Object value ){
            this.name  = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

    }
}
