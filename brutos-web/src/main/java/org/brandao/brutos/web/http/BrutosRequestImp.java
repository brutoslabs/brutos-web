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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class BrutosRequestImp extends ServletRequestWrapper
        implements BrutosRequest{

    private Map parameters;
    private UploadListener uploadListener;
    private HttpRequestParser httpRequestParser;
    private WebApplicationContext context;

    public BrutosRequestImp( ServletRequest request ){
        super( request );
        this.parameters = new HashMap();
        initialize();
    }

    public UploadListener getUploadListener(){

        if( uploadListener == null ){

            Scope contextScope = context.getScopes()
                    .get( WebScopeType.APPLICATION );

            UploadListenerFactory uploadListenerFactory =
                    (UploadListenerFactory) contextScope
                        .get( BrutosConstants.UPLOAD_LISTENER_FACTORY );

            UploadEvent uploadEvent = this.httpRequestParser
                    .getUploadEvent(this);
            
            this.uploadListener =
                    uploadListenerFactory.getNewUploadListener(uploadEvent);

        }

        return this.uploadListener;
    }
    
    private void initialize(){
        this.context = ContextLoader
                .getCurrentWebApplicationContext();
                
        if( context != null ){
            Scope contextScope = context.getScopes()
                    .get( WebScopeType.APPLICATION );

            httpRequestParser =
                    (HttpRequestParser) contextScope
                        .get( BrutosConstants.HTTP_REQUEST_PARSER );
        }


    }
    
    public void parseRequest() throws IOException{

        UploadListener uploadListener = getUploadListener();

        boolean isMultPart = httpRequestParser.isMultipart(this,uploadListener);

        if( isMultPart )
            httpRequestParser.parserMultipart(this,
                    context.getConfiguration(), uploadListener);
        else
            httpRequestParser.parserContentType(this, this.getContentType());

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
        return super.getRequest();
    }

    public String getRequestId() {

        if( getRequest() instanceof HttpServletRequest ){
            HttpServletRequest httpRequest = (HttpServletRequest)getRequest();
            String path         = httpRequest.getRequestURI();
            String contextPath  = httpRequest.getContextPath();
            path = path.substring( contextPath.length(), path.length() );
            return path;
        }
        else
            throw new UnsupportedOperationException();
    }

}
