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

package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.*;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.http.BrutosRequest;
import org.brandao.brutos.http.MutableRequest;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.programatic.ControllerManager;

/**
 *
 * @author Afonso Brandao
 */
public class WebControllerResolver implements ControllerResolver{

    private Map<String,URIMap> uris;

    public WebControllerResolver() {
        this.uris = new HashMap<String, URIMap>();
    }

    private URIMap getURIMapping( String uri ){
        try{
            if( uris.containsKey( uri ) )
                return uris.get( uri );
            else{
                URIMap map = new URIMap( uri );
                uris.put( uri , map);
                return map;
            }
        }
        catch( Exception e ){
            throw new BrutosException( e.getMessage(), e );
        }
    }

    /**
     * @deprecated 
     * @param webFrameManager
     * @param request
     * @return
     */
    public Form getController(WebFrameManager webFrameManager, HttpServletRequest request) {
        String path         = request.getRequestURI();
        String contextPath  = request.getContextPath();
        path = path.substring( contextPath.length(), path.length() );
        
        path = path.replace( "\\", "/" );
        return webFrameManager.getForm( path );
    }

    public Form getController(ControllerManager controllerManager, InterceptorHandler handler) {
        String uri = handler.requestId();
        Map<String, Form> forms = controllerManager.getForms();
        BrutosRequest request = (BrutosRequest) ContextLoaderListener
                                    .currentRequest.get();
        for( String u: forms.keySet() ){
            URIMap uriMap = getURIMapping( u );
            if( uriMap.matches(uri) ){
                Map<String,String> params = uriMap.getParameters(uri);
                for(String key: params.keySet() )
                    request.setParameter(key, params.get(key) );
                return forms.get(u);
            }
        }
        return null;
    }
    
}

class URIMap{

    String uriPattern;

    List<Parameter> parameters;

    public URIMap( String uri ) throws MalformedURLException{
        createMap( uri );
        this.uriPattern = getURIPattern( uri );
    }

    private void createMap( String uri ) throws MalformedURLException{
        //fragmentos da uri
        List<String> frags = new ArrayList<String>();
        // identificados detectados
        List<String> ids   = new ArrayList<String>();

        //inicio de um identificador
        int index = uri.indexOf("{");
        //identificador
        String id;

        //se index for igual a -1 entao nao existe identificador. Sua definicao é null
        //se index for diferente de null entao existe um identificador. E extraido o
        //fragmento que inicia em 0 e termina em index
        if( index == -1 )
            frags.add( null );
        else
            frags.add( uri.substring( 0, index ) );

        //enquanto index for diferente de -1, a procura por identificadores continua
        while( index != -1 ){
            //fim do identificador
            int index2 = uri.indexOf("}", index );

            id = index+1 < index2? uri.substring( index+1, index2 ) : null;


            if( id == null )
                throw new MalformedURLException();

            //adiciona o identificador
            ids.add(id);

            //procura o proximo identificador para obter o proximo fragmento
            int nextIndex = uri.indexOf( "{", index2 );

            if( nextIndex == -1 ){
                nextIndex = uri.length();
            }

            //fragmento atual
            String frag = index2+1 < nextIndex? uri.substring(index2+1, nextIndex) : null;

            //adiciona o fragmento
            frags.add( frag );

            index = uri.indexOf("{", index + 1 );
        }

        //se a quantidade de identificadores for impar, entao o ultimo identificador
        // foi encontrado no fim da uri
        if( frags.size() % 2 == 1 )
            frags.add(null);

        parameters = new ArrayList<Parameter>();

        for( int i=0;i<ids.size();i++ ){
            parameters.add(
                    new Parameter(ids.get(i), frags.get(i), frags.get(i+1) ) );
        }
    }

    private String getURIPattern( String uri ){

        if( uri == null )
            throw new NullPointerException();

        String regex = ".";

        int index = uri.indexOf("{");
        int index2 = -1;
        int old = 0;
        while( index != -1 ){
            index2 = uri.indexOf("}", index );

            String id = index+1 < index2? uri.substring( index+1, index2 ) : null;

            if( id == null )
                return "";

            String words = uri.substring( old, index );
            regex += words+"\\w{1,}";
            old = index2+1;
            index = uri.indexOf("{", index + 1 );
        }

        if( index2 == -1 )
            regex = uri;
        else
            regex += uri.substring( index2+1, uri.length() );

        return regex;
    }

    public Map<String,String> getParameters( String uri ){
        int start = 0;
        int end   = 0;
        Map<String,String> params = new HashMap<String,String>();

        for( Parameter p: parameters ){
            start = p.getStart() == null? 0 : uri.indexOf( p.getStart(), start ) + p.getStart().length();
            end   = p.getEnd() == null? uri.length() : uri.indexOf( p.getEnd(), start + 1 );

            params.put(p.getId(), uri.substring(start, end) );
        }

        return params;
    }

    public boolean matches( String uri ){
        return uri.matches(this.uriPattern);
    }
}

class Parameter{

    private String start;
    private String end;
    private String id;

    public Parameter( String id, String start, String end ){
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}