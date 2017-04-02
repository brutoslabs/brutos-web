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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebScopeType;

/**
 * 
 * @author Brandao
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

    public void setServletRequest(ServletRequest request){
        super.setRequest(request);
    }
    
    public ServletRequest getServletRequest() {
        return super.getRequest();
    }

    public String getRequestId() {
        
        Object request = getRequest();
        
        if( request instanceof HttpServletRequest ){
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String path         = httpRequest.getRequestURI();
            String contextPath  = httpRequest.getContextPath();
            path = path.substring( contextPath.length(), path.length() );
            return path;
        }
        else
            throw new UnsupportedOperationException();
    }

}
