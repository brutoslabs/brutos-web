/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 *
 * @deprecated 
 * @author Afonso Brandao
 */
public class XMLApplicationContext /*extends ApplicationContext*/{
/*
    private Map<String,Object> xmlData;
    private String version;
    private boolean existXML;

    public XMLApplicationContext(){
        this.existXML = false;
        this.version = null;
        this.xmlData = null;
    }

    private void loadData( ServletContextEvent sce ){
        InputStream       in = null;
        BrutosProcessor bp   = new BrutosProcessor();
        try{
            in = getSourceConfiguration( sce );
            if( in != null ){
                xmlData = bp.processBrutosXML( in );
                existXML = true;
            }
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
        finally{
            try{
                if( in != null )
                    in.close();
            }
            catch( Exception k ){}
        }
    }

    private InputStream getSourceConfiguration( ServletContextEvent sce ) throws FileNotFoundException{
        InputStream in = Thread.currentThread()
                    .getContextClassLoader()
                            .getResourceAsStream( "brutos-config.xml" );

        in = in == null?
                getXMLFile( sce ) :
                in;

        //if( in == null )
        //    throw new BrutosException( "brutos-config.xml not found!" );
        //else
            return in;
    }

    private InputStream getXMLFile( ServletContextEvent sce ) throws FileNotFoundException{
        File xmlFile = new File( sce.getServletContext().getRealPath( "/" ) +
                    "WEB-INF/brutos-config.xml" );
        return xmlFile.exists() && xmlFile.canRead()?
                new FileInputStream( xmlFile ) :
                null;

    }

    public void configure( Properties config, ServletContextEvent sce ){
        loadData( sce );
        preLoad( config );
    }

    private void preLoad( Properties config ){
        try{
            if( !existXML )
                return;
            
            config( xmlData );
            contextParams(
                (Map<String,Object>)xmlData.get( "context-params" ), config );
            loadTypes( (List<Map<String,String>>)xmlData.get( "types" ) );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public void destroy() {
    }

    public void loadIOCManager(IOCManager iocManager) {
        try{
            if( !existXML )
                return;

           IOCXMLMapping iocMapping =
            new IOCXMLMapping( iocManager );
           iocMapping.setBeans(
            (Map<String,Map<String,Object>>)xmlData.get( "beans" ) );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public void loadWebFrameManager(WebFrameManager webFrameManager) {
        try{
            if( !existXML )
                return;

            WebFrameXMLMapping webFrameMapping =
                new WebFrameXMLMapping( webFrameManager );
                webFrameMapping
                    .setWebFrames(
                        (List<Map<String,Object>>)xmlData.get( "web-frames" ) );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public void loadInterceptorManager(InterceptorManager interceptorManager) {
        try{
            if( !existXML )
                return;

            InterceptorXMLMapping interceptorMapping =
                new InterceptorXMLMapping( interceptorManager );
            interceptorMapping
                .processData(
                    (List<Map<String,Object>>)xmlData.get( "interceptors" ) );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private void config( Map<String,Object> data ){
        version  = (String) data.get( "version" );
        if( Arrays
            .binarySearch( XMLBrutosConstants.XML_SUPPORTED_VERSION, version ) == -1 )
            throw new BrutosException( "unsupported version: " + version );
    }

    private void contextParams( Map<String,Object> data, Properties config ){
        ConfigurationXMLMapping configurationMapping = new ConfigurationXMLMapping( config );
        configurationMapping.setData( data );
    }

    private void loadTypes( List<Map<String,String>> data ) throws ClassNotFoundException{
        TypeXMLMapping typeMapping = new TypeXMLMapping();
        typeMapping.processData( data );
    }

    protected void loadController(ControllerManager controllerManager) {
    }
*/
}
