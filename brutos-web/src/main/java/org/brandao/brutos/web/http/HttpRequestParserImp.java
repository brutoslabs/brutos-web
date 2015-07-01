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

package org.brandao.brutos.web.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.jbrgates.JSONDecoder;

/**
 *
 * @author Brandao
 */
public class HttpRequestParserImp implements HttpRequestParser{

    public boolean isMultipart(BrutosRequest request, 
            UploadListener uploadListener) throws IOException {
        return uploadListener.getUploadEvent().isMultipart();
    }

    public void parserMultipart(BrutosRequest request, Properties config,
            UploadListener uploadListener) throws IOException{

        Long maxLength =
            Long.parseLong(
                config
                    .getProperty( "org.brandao.brutos.request.max_length", "0" ) );

        String path = config
                    .getProperty( "org.brandao.brutos.request.path", null );


        try{
            DefaultUploadEvent uploadEvent = (DefaultUploadEvent)uploadListener
                    .getUploadEvent();
            uploadListener.uploadStarted();
            uploadEvent.setMaxLength( maxLength );
            uploadEvent.setPath(path);
            uploadEvent.start();
            while( uploadEvent.hasMoreElements() ){
                Input input = uploadEvent.nextElement();
                request.setObject( input.getName(), input.getValue() );
            }
        }
        finally{
            uploadListener.uploadFinished();
        }
    }

    public void parserContentType(BrutosRequest request, String contentType) 
            throws IOException {
        ServletRequest httpRequest = request.getServletRequest();
        if( "application/json".equals( httpRequest.getContentType() ) ){
            BufferedReader reader = httpRequest.getReader();
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
                    request.setParameter(
                        String.valueOf(o),
                        String.valueOf(data.get(o)));
                }
            }
        }
    }

    public UploadEvent getUploadEvent(BrutosRequest request) {
        return new DefaultUploadEvent( request );
    }

    private class DefaultUploadEvent implements java.util.Enumeration, UploadEvent{

        private ServletRequest request;
        private byte[] buffer;
        private int len;
        //private long length;
        private ServletInputStream in;
        private String boundary;
        private boolean noFields;

        private long maxLength;
        private String path;
        private long currentDataSize;

        public DefaultUploadEvent( BrutosRequest request ){
            this.request         = request.getServletRequest();
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

            if( ( len = readData( buffer, in ) ) > 0 ){
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
