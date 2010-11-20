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

package org.brandao.brutos.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.type.json.JSONDecoder;



/**
 *
 * @author Afonso Brandao
 */
public class DefaultBrutosRequest extends HttpServletRequestWrapper implements BrutosRequest{

    private Map parameters;
    LoadRequestData loadData;
    private long maxLength;
    private String path;
    
    public DefaultBrutosRequest( HttpServletRequest request ){
        super( request );
        this.parameters = new HashMap();
        initialize();
        loadData();
    }

    
    private void initialize(){
        ApplicationContext context = WebApplicationContext.getCurrentApplicationContext();
        if( context != null ){
            maxLength =
                Long.parseLong(
                    context.getConfiguration()
                        .getProperty( "org.brandao.brutos.request.max_length", "0" ) );

            path = context
                    .getConfiguration()
                        .getProperty( "org.brandao.brutos.request.path", null );
        }
    }
    
    private void loadData(){
        try{
            loadData = new LoadRequestData( this );
            if( loadData.isMultipart() ){
                loadData.setMaxLength( maxLength );
                loadData.setPath(path);
                loadData.start();
                while( loadData.hasMoreElements() ){
                    Input input = loadData.nextElement();
                    this.setObject( input.getName() , input.getValue() );
                }

            }
            else
            if( "application/json".equals( this.getContentType() ) ){
                JSONDecoder decoder = new JSONDecoder( this.getInputStream() );
                Map data = decoder.getInstance(null);
                for( Object o: data.keySet() ){
                    this.setParameter(String.valueOf(o), String.valueOf(data.get(o)));
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
        return loadData.isMultipart()? loadData.getCurrentDataSize() : super.getContentLength();
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
                values = new ParameterList<Object>();
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

}
class LoadRequestData implements java.util.Enumeration<Input>{

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
    
    public LoadRequestData( DefaultBrutosRequest request ){
        this.request  = request;
        this.buffer   = new byte[ 8192 ];
        this.noFields = false;
    }

    public boolean isMultipart(){
        String tmp = request.getHeader( "content-type" );
        if( tmp == null )
            return false;

        int i = tmp.indexOf( "multipart/form-data" );
        return i != -1;
    }

    private String getboundary() throws IOException{

        String content_type = request.getHeader( "content-type" );

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

    @Override
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

    @Override
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

    private BrutosFile getFieldFile( String boundary, String header, ServletInputStream in ) throws IOException{
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
        BrutosFile f = null;
        try{
            while( s.indexOf( boundary ) == -1 ){

                len = readData( buffer, in );
                s = new String( buffer, 0, len);

                if ( file != null && s.indexOf( boundary ) == -1 ){

                    if( fout == null ){
                        File arquivo = getFile( this.path, file );
                        f = new BrutosFile( arquivo );
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

    /**
     * @return the currentDataSize
     */
    public long getCurrentDataSize() {
        return currentDataSize;
    }

}
class Input{

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