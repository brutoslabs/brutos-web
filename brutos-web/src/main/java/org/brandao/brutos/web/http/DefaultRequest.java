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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.brandao.brutos.*;

/**
 *
 * @author Afonso Brandao
 */
@Deprecated
public class DefaultRequest extends HttpServletRequestWrapper implements BrutosRequest{
    
    private long readsizedata = 0;
    private char[] buf = new char[1024];
    private boolean run = false;
    private Map parameters;
    
    public DefaultRequest( HttpServletRequest request ) {
        super( request );
        this.parameters = new HashMap();
        loadData();
    }
    
    public Object getObject( String name ){
        if( parameters.containsKey( name ) )
            return getParameter0( name );
        else
        if( name != null )
            return super.getParameter( name );
        else
            return null;
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

    private Object getParameter0( String value ){
        List<Object> values = (List)parameters.get( value );
        return values.get( 0 );
    }
    
    /*
    public void setRequest(HttpServletRequest request){
        this.request = request;
        loadData();
    }
    */
    
    private void loadData(){
        try{
            if( isMultipart() )
                readData();
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    
    private String getboundary() throws IOException{
        
        String content_type = getHeader( "content-type" );
        String boundary;
        
        int posboundary = content_type.indexOf( "boundary" );
        
        if( posboundary == -1 ) 
            throw new IOException("Boundary not found");
        

        return "--" + content_type.substring( posboundary + 9 );
        
    }
    
    private int readData( byte[] buf, ServletInputStream in ) throws IOException{
        int l = in.readLine( buf, 0, buf.length );
        /*if( l != -1 )
            System.out.print( new String( buf, 0, l ) );
         */
        this.setReadsizedata(this.getReadsizedata() + l);
        return l;
    }
    
    private void readData() throws IOException {
        ServletInputStream in = getInputStream();
        byte[] buf            = new byte[2048];
        int l                 = -1;
        String boundary       = getboundary();        
        //setFields(new LinkedHashMap());
        setRun(true);
        
        if( ( l = readData( buf, in ) ) != 0 ){
            String s = new String( buf, 0, l-2 );
            if( s.endsWith( boundary ) ){
                while( getNewField( boundary, in ) );
            }
        }
        
    }
    
    private boolean getNewField( String boundary, ServletInputStream in ) throws IOException{
        byte[] buf            = new byte[2048];
        int l                 = -1;
        l = readData( buf, in );
        
        if( l == -1 )
            return false;
        
        String header    = new String( buf, 0, l-2 );
        Object value     = null;
        String fieldName = null;
        
        if( header.indexOf( "filename" ) != -1 ){
            fieldName = getField0( header, "name" );
            value = getFieldFile( boundary, header, in );
        }
        else{
            fieldName = getField0( header, "name" );
            value = getField( boundary, in );
        }
 
        if( value != null )
            this.setObject( fieldName, value );
        
        return true;
    }
    
    private BrutosFile getFieldFile( String boundary, String header, ServletInputStream in ) throws IOException{
        byte[] buf            = new byte[2048];
        int l                 = -1;
        String fileName  = getField0( header, "filename" );
        
        l = readData( buf, in );
        
        if( l == -1 )
            throw new IOException( "not found type of the file!" );

        String typeFile  = (new String( buf, 0, l-2 ));
        typeFile = typeFile.length() < 14? "" : typeFile.substring( 14 );


        //divide a string em nome do arquivo e diretorio
        String file = "";
        String path = fileName;
        char c;
        while( path.length() > 0 && ( c = path.charAt( path.length() - 1 ) )!= '\\' && c != '/' ){
            file = c + file;
            path = path.substring( 0, path.length() - 1 );
        }
        
        java.io.File arquivo = File.createTempFile("multpart",".tmp");
        arquivo.deleteOnExit();
        
        BrutosFile f = new BrutosFile( arquivo );
        f.setFileName( file );
        //f.setContentType( super.getContentType() );
        
        java.io.FileOutputStream fout = new java.io.FileOutputStream( arquivo );

        l = readData( buf, in );
        String s = new String( buf, 0, l-2 );
        
        int filesize = 0;
        
        while( s.indexOf( boundary ) == -1 ){
            l = readData( buf, in );
            s = new String( buf, 0, l);

            if ( s.indexOf( boundary ) == -1 ){
                fout.write( buf, 0, l );
                filesize = filesize + l;
            }
        }
        fout.close();
        
        if( fileName.equals( "" ) )
            return null;
        else
            return f;
        
    }
    
    private String getField( String boundary, ServletInputStream in ) throws IOException{
        byte[] buf            = new byte[2048];
        int l                 = -1;
        String value          = "";
        
        l = readData( buf, in );
        String s = new String( buf, 0, l-2 );
        
        int filesize = 0;
        
        while( !s.equals( boundary ) && !s.equals( boundary + "--" ) ){
            if( s.length() != 0 )
                value += ( value.length() == 0 )? s : s + "\n";
            
            l = readData( buf, in );
            s = new String( buf, 0, l-2);
        }
        return value;
    }
    
    private String getField0( String cab, String name ){
        int initId = cab.indexOf( name );
        int initValor = cab.indexOf( "\"", initId );
        int endValor = cab.indexOf( "\"", initValor + 1 );
        String id = cab.substring( initValor + 1, endValor);
        return id;
    }
       
    private boolean isMultipart(){
        String tmp = getHeader( "content-type" );
        if( tmp == null )
            return false;
        
        int i = tmp.indexOf( "multipart/form-data" );
        return i != -1;
    }
    
    private long getSize(){
        return getContentLength();
    }

    private long getReaddatasize() {
        return getReadsizedata();
    }

    private void stop(){
        this.setRun(false);
    }

    public long getReadsizedata() {
        return readsizedata;
    }

    public void setReadsizedata(long readsizedata) {
        this.readsizedata = readsizedata;
    }

    private char[] getBuf() {
        return buf;
    }

    private void setBuf(char[] buf) {
        this.buf = buf;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
    
    public void setObject(String name, Object value) {
        List<Object> values = (List)parameters.get( name );
        if( values == null ){
            values = new ArrayList<Object>();
            parameters.put( name, values );
        }
        values.add( value );
    }

    public void setParameter(String name, String value) {
        setObject( name, value );
    }

    @Override
    public List<Object> getObjects(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setParameters(String name, String[] values) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setObjects(String name, Object[] value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UploadListener getUploadListener() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void parseRequest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}