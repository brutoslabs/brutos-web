/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.mapping.Mapping;
import org.brandao.brutos.old.programatic.InterceptorManager;

/**
 *
 * @author Afonso Brand�o
 */
@Deprecated
public class XMLBrutosParse {
    /*
    private double version;
    private String encoding;
    private ConfigurationXMLMapping configurationMapping;
    private WebFrameXMLMapping webFrameMapping;
    private IOCXMLMapping iocMapping;
    private InterceptorXMLMapping interceptorMapping;
    private ServletContextEvent contextEvent;
    private List<Mapping> mappings;
    private InterceptorManager interceptorManager;
   
    public XMLBrutosParse( Configuration config, ServletContextEvent contextEvent ) {
        this.configurationMapping = new ConfigurationXMLMapping( config );
        this.contextEvent         = contextEvent;
        this.mappings             = new ArrayList();
    }

    public void processData( Map<String,Object> data ){
        try{
            config( data );
            contextParams( (Map<String,Object>)data.get( "context-params" ) );
            loadTypes( (List<Map<String,String>>)data.get( "types" ) );
            extendConfiguration( (List<String>)data.get( "extend-configuration" ) );
            loadIOCMapping();
            interceptors( (List<Map<String,Object>>)data.get( "interceptors" ) );
            webFrames( (List<Map<String,Object>>)data.get( "web-frames" ) );
            beans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
    }

    private void loadTypes( List<Map<String,String>> data ) throws ClassNotFoundException{
        TypeXMLMapping typeMapping = new TypeXMLMapping();
        typeMapping.processData( data );
    }
    
    private void loadIOCMapping(){
        iocMapping = new IOCXMLMapping( 
                        new IOCManager( 
                            IOCProvider.getProvider( 
                                configurationMapping.getConfiguration() )
                            ) 
                    );
    }
    
    private void config( Map<String,Object> data ){
        version  = Double.parseDouble( (String) data.get( "version" ) );
        encoding = (String) data.get( "encoding" );
    }
    
    private void contextParams( Map<String,Object> data ){
        if( data != null )
            getConfigurationMapping().setData( data );
        
        getConfigurationMapping().getConfiguration().setProperty( "encoding", encoding );
    }
    
    private void extendConfiguration( List<String> data ){
        
        if( data == null )
            return;
        
        List<Mapping> providers = new ArrayList<Mapping>();
        try{
            for( String provider: data ){
                providers.add( 
                        (Mapping)Class.forName( provider, true, Thread.currentThread().getContextClassLoader() ).newInstance() 
                );
            }
            setMappings( providers );
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
    }
    
    private void interceptors( List<Map<String,Object>> interceptors ) throws Exception{
        this.interceptorManager   = new InterceptorManager( iocMapping.getIocManager() );
        this.interceptorMapping   = new InterceptorXMLMapping( interceptorManager );
        interceptorMapping.processData( interceptors );
    }
    
    private void webFrames( List<Map<String,Object>> data ) throws Exception{
        webFrameMapping  = new WebFrameXMLMapping( interceptorManager, iocMapping.getIocManager() );
        if( data != null )
            webFrameMapping.setWebFrames( data );
    }
    
    private void beans( Map<String, Map<String,Object>> beans ) throws Exception{
        getIocMapping().setBeans( beans );
    }
    
    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public ConfigurationXMLMapping getConfigurationMapping() {
        return configurationMapping;
    }

    public void setConfigurationMapping(ConfigurationXMLMapping configurationMapping) {
        this.configurationMapping = configurationMapping;
    }

    public WebFrameXMLMapping getWebFrameMapping() {
        return webFrameMapping;
    }

    public void setWebFrameMapping(WebFrameXMLMapping webFrameMapping) {
        this.webFrameMapping = webFrameMapping;
    }

    public IOCXMLMapping getIocMapping() {
        return iocMapping;
    }

    public void setIocMapping(IOCXMLMapping iocMapping) {
        this.iocMapping = iocMapping;
    }

    public ServletContextEvent getContextEvent() {
        return contextEvent;
    }

    public void setContextEvent(ServletContextEvent contextEvent) {
        this.contextEvent = contextEvent;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
    }
*/
}
