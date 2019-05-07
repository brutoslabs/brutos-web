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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MutableRequestParserEvent;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.WebMvcRequest;

/**
 * 
 * @author Brandao
 *
 */
public class MultipartContentParser {

	private static final String BOUNDARY				= "boundary";

	private static final String FILENAME				= "filename";

	private static final String NAME					= "name";
	
	private static final String EMPTY					= "";
	
	private static final String PREFIX_TMP_FILE_NAME	= "multpart";
	
	private static final String SUFFIX_TMP_FILE_NAME	= ".tmp";
	
    private ServletRequest request;
    
    private byte[] buffer;
    
    private int len;
    
    private ServletInputStream in;
    
    private String boundary;
    
    private boolean noFields;

    private long maxLength;
    
    private String path;
    
    private MutableRequestParserEvent event;
    
    public MultipartContentParser(WebMvcRequest request, 
    		MutableRequestParserEvent event){
        this.request         = request.getServletRequest();
        this.buffer          = new byte[ 8192 ];
        this.noFields        = false;
        this.event           = event;
        this.boundary        = (String)request.getHeader(BOUNDARY);
        
        if(this.boundary == null){
        	this.boundary = ((MediaType)request.getType()).getParams().get(BOUNDARY);
        }
    }

    private int readData( byte[] buf, ServletInputStream in ) throws IOException{
        this.len = in.readLine( buf, 0, buf.length );
        this.event.addBytesRead(this.len);

        if( this.maxLength > 0 && getCurrentDataSize() > this.maxLength )
            throw new BrutosException( "data too large" );

        return len;
    }

    public void start() throws IOException {
    	
    	this.in       = this.request.getInputStream();
    	this.len      = -1;
    	//this.boundary = "--" + this.params.get(BOUNDARY);

        if( ( this.len = readData( this.buffer, this.in ) ) > 0 ){
            String s = new String( this.buffer, 0, this.len-2 );
            noFields = !s.endsWith( this.boundary );
        }
        else
            noFields = true;

    }

    public boolean hasMoreElements(){
        if( this.noFields )
            return false;

        try{
        	this.len = -1;
        	this.len = readData( this.buffer, this.in );
            if( this.len == -1 )
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
            String header = new String( this.buffer, 0, this.len-2 );
            String name   = null;
            Object value  = null;

            if( header.indexOf( FILENAME ) != -1 ){
                name  = this.getName( header, NAME );
                value = this.getFieldFile( this.boundary, header, this.in );
            }
            else{
                name  = this.getName( header, NAME );
                value = this.getField( this.boundary, this.in );
            }

            return new Input( name, value );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private String getName( String cab, String name ){
        int initId    = cab.indexOf( name );
        int initValor = cab.indexOf( "\"", initId );
        int endValor  = cab.indexOf( "\"", initValor + 1 );
        String id     = cab.substring( initValor + 1, endValor);
        return id;
    }

    private UploadedFile getFieldFile(String boundary, String header,
            ServletInputStream in ) throws IOException{

        this.len = -1;
        String fileName  = getName( header, FILENAME );

        this.len = readData( this.buffer, in );

        if( len == -1 )
            throw new IOException( "not found type of the file!" );

        String typeFile = new String( this.buffer, 0, len-2 );
        typeFile        = typeFile.length() < 14? "" : typeFile.substring( 14 );


        String file = "";
        String dir  = fileName;
        char c;
        
        while( dir.length() > 0 && ( c = dir.charAt( dir.length() - 1 ) )!= '\\' && c != '/' ){
            file = c + file;
            dir = dir.substring( 0, dir.length() - 1 );
        }

        file     = EMPTY.equals(file)? null : file;
        this.len = this.readData( this.buffer, in );
        String s = new String( this.buffer, 0, this.len-2 );

        int filesize = 0;

        FileOutputStream fout = null;
        UploadedFile f        = null;
        
        try{
            while( s.indexOf( boundary ) == -1 ){

                this.len = readData( this.buffer, in );
                s        = new String( this.buffer, 0, this.len);

                if ( file != null && s.indexOf( boundary ) == -1 ){

                    if( fout == null ){
                        File arquivo = getFile( this.path, file );
                        f            = new UploadedFileImp( arquivo );
                        f.setFileName( file );
                        fout = new FileOutputStream( arquivo );
                    }

                    fout.write( this.buffer, 0, this.len );
                    filesize = filesize + this.len;
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

        /*if( dir != null ){
            arquivo = new File( new File(dir), file );
            arquivo.createNewFile();
        }
        else{*/
            arquivo = File.createTempFile(PREFIX_TMP_FILE_NAME, SUFFIX_TMP_FILE_NAME);
            arquivo.deleteOnExit();
        //}
        return arquivo;
    }

    private String getField( String boundary, ServletInputStream in ) throws IOException{
        this.len     = -1;
        String value = "";

        this.len = this.readData( this.buffer, in );
        String s = new String( this.buffer, 0, this.len-2 );

        while( !s.equals( boundary ) && !s.equals( boundary + "--" ) ){
            if( s.length() != 0 )
                value += ( value.length() == 0 )? s : s + "\n";

            this.len = readData( this.buffer, in );
            s = new String( this.buffer, 0, this.len-2);
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
        return event.getBytesRead();
    }

    public long getContentLength() {
        return request.getContentLength();
    }

    public long getBytesRead() {
        return this.getCurrentDataSize();
    }

    public class Input{

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