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

package org.brandao.brutos.xml;

import org.brandao.brutos.ClassType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.programatic.InterceptorBuilder;
import org.brandao.brutos.programatic.InterceptorManager;
import org.brandao.brutos.old.programatic.BeanBuilder;
import org.brandao.brutos.old.programatic.CollectionBuilder;
import org.brandao.brutos.old.programatic.MapBuilder;
import org.brandao.brutos.old.programatic.MethodBuilder;
import org.brandao.brutos.old.programatic.WebFrameBuilder;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Afonso Brandao
 */
public class WebFrameXMLMapping {
    
    private WebFrameManager webFrameManager;
    
    public WebFrameXMLMapping( WebFrameManager webFrameManager ) {
        this.webFrameManager = webFrameManager;
    }

    public WebFrameXMLMapping( InterceptorManager interceptorManager, IOCManager iocManager ) {
        this.setWebFrameManager( new WebFrameManager( interceptorManager, iocManager ) );
    }
    
    public void setWebFrames( List<Map<String,Object>> data ) throws Exception {
        if( data == null )
            return;
        
        for( Map<String,Object> wf: data ){
            addWebFrame( wf );
        }
    }
    
    private void addWebFrame( Map<String,Object> data ) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        WebFrameBuilder wfb = getWebFrameManager()
            .addWebFrame( 
                (String)data.get( "uri" ),
                (String)data.get( "page" ),
                Boolean.valueOf( (String)data.get( "redirect" ) ),
                (String)data.get( "name" ),
                (Class)Class.forName( (String) data.get( "class" ), true, Thread.currentThread().getContextClassLoader() ),
                ScopeType.valueOf( data.get( "scope" ).toString() ),
                (String)data.get( "method-parameter-name" )
            );
        

        alias( wfb, (List<String>)data.get( "alias" ) );
        throwSafe( wfb, (List<Map<String,Object>>)data.get( "throw-safe" ) );

        List<Map<String,Object>> interceptors = (List)data.get( "interceptors" );
        interceptors( wfb, interceptors );
        
        Map<String,Map<String,Object>> mapsKey = (Map)data.get( "mapping-keys" );
        List<Map<String,Object>> maps = (List)data.get( "mapping" );
        
        if( maps != null ){
            /*for( Map<String,Object> mapping: maps ){
                addMappingBean( mapsKey, mapping, wfb );
            }*/
            addMappings( maps, wfb );
        }
        
        
        List<Map<String,Object>> props = (List)data.get( "property-webframe" );
        if( props != null ){
            for( Map<String,Object> prop: props )
                addProperty( prop, wfb );
        }
        List<Map<String,Object>> methods = (List)data.get( "method" );
        
        if( methods != null ){
            for( Map<String,Object> method: methods )
                addMethod( method, wfb );
        }
        
        setDefaultMethod( data.get( "default-method-name" ), wfb );
    }

    private void alias( WebFrameBuilder wfb, List<String> alias ){
        for( int i=0; alias != null && i<alias.size();i++ ){
            wfb.addAlias( alias.get(i) );
        }
    }

    private void throwSafe( Object wfb, List<Map<String,Object>> thws ) throws ClassNotFoundException{
        for( int i=0; thws != null && i<thws.size();i++ ){
            Map<String,Object> data = thws.get(i);


            if( wfb instanceof WebFrameBuilder ){
                ((WebFrameBuilder)wfb)
                    .addThrowable(
                        (Class)Class.forName( (String) data.get( "target" ), true, Thread.currentThread().getContextClassLoader() ),
                        (String)data.get( "uri" ),
                        (String)data.get("name"),
                        Boolean.valueOf( (String)data.get( "redirect" ) )
                    );
            }
            else
            if( wfb instanceof MethodBuilder ){
                ((MethodBuilder)wfb)
                    .addThrowable(
                        (Class)Class.forName( (String) data.get( "target" ), true, Thread.currentThread().getContextClassLoader() ),
                        (String)data.get( "uri" ),
                        (String)data.get("name"),
                        Boolean.valueOf( (String)data.get( "redirect" ) )
                    );
            }
        }

    }

    private void interceptors( WebFrameBuilder wfb, List<Map<String,Object>> interceptors ){
        if( interceptors != null ){
            for( Map<String,Object> i: interceptors ){
                InterceptorBuilder ib = wfb.addInterceptor( (String)i.get( "ref" ) );
                
                Map<String,Object> params = new HashMap();
                List<Map<String,Object>> list = (List)i.get( "params" );
                for( Map<String,Object> param: list )
                    ib.addParameter( (String)param.get( "name" ), (String) param.get( "value" ) );
            }
        }
    }
    
    private void setDefaultMethod( Object methodName, WebFrameBuilder wfb ){
        if( methodName != null )
            wfb.setDefaultMethodName( (String)methodName );
    }

    @Deprecated
    private void addMappingBean( Map<String,Map<String,Object>> maps, Map<String,Object> mapping, WebFrameBuilder wfb ) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        BeanBuilder mbb = wfb
                .addMappingBean( 
                    (String)mapping.get( "name" ),
                    (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                );
        
        List<Map<String,Object>> properties = (List) mapping.get( "properties" );
        if( properties != null ){
            for( Map<String,Object> property: properties )
                addPropertyMappingBean( maps, property, mbb );
        }
    }

    @Deprecated
    private void addMappingBean( Map<String,Object> mapping, WebFrameBuilder wfb ) 
        throws ClassNotFoundException, InstantiationException,
            IllegalAccessException{
        BeanBuilder mbb = wfb
                .addMappingBean(
                    (String)mapping.get( "name" ),
                    (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                );

        List<Map<String,Object>> properties = (List) mapping.get( "properties" );
        if( properties != null ){
            for( Map<String,Object> property: properties ){
                mbb
                    .addProperty(
                        (String)property.get( "name" ),
                        (String)property.get( "property-name" ),
                        EnumerationType.valueOf( property.get( "enum-property" ).toString() ),
                        (String)property.get( "temporal-property" ),
                        (String)property.get( "mapping-name" ),
                        ScopeType.valueOf( ((String)property.get( "scope" )) ),
                        property.get( "factory" ) == null?
                            null :
                            (Type)(Class.forName( (String) property.get( "factory" ), true, Thread.currentThread().getContextClassLoader() )).newInstance()
                    );
            }
        }
    }

    private void addMappings( List<Map<String,Object>> mappings, WebFrameBuilder wfb )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        for( Map<String,Object> mapping: mappings ){
            addMappings( mapping, wfb );
            //addMappings( mapping, wfb, null, null );
        }
    }

    private void addMappings( Map<String,Object> mapping, WebFrameBuilder wfb )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{

        if( "mapping".equals(mapping.get("@tag")) ){
            BeanBuilder mbb = wfb
                    .addMappingBean(
                        (String)mapping.get( "name" ),
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            addMappingBean( mapping, mbb );
        }
        else
        if( "mapping-collection".equals(mapping.get("@tag")) ){
            CollectionBuilder cc = wfb.addMappingCollection(
                        (String)mapping.get( "name" ),
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );

            addMappings( (Map<String,Object>)mapping.get("bean"), cc );
        }
        else
        if( "mapping-map".equals(mapping.get("@tag")) ){
            MapBuilder cc = wfb.addMappingMap(
                        (String)mapping.get( "name" ),
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );

            Map<String,Object> key = (Map<String,Object>)mapping.get("key");
            cc.setKey( 
                    (String)key.get("name"),
                    ClassType.get( (String) key.get( "type" ) ),
                    ScopeType.valueOf( ((String)key.get( "scope" )) )
                );
            addMappings( (Map<String,Object>)mapping.get("bean"), cc );
        }
    }

    private void addMappings( Map<String,Object> mapping, CollectionBuilder cb )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{

        if( "mapping".equals(mapping.get("@tag")) ){
            BeanBuilder mbb = cb
                    .bean(
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            addMappingBean( mapping, mbb );
        }
        /*else
        if( "mapping-collection".equals(mapping.get("@tag")) ){
            CollectionBuilder cc = cb.collection(
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            addMappings( (Map<String,Object>)mapping.get("bean"), cc );
        }
        else
        if( "mapping-map".equals(mapping.get("@tag")) ){
            MapBuilder cc = cb.map(
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            
            Map<String,Object> key = (Map<String,Object>)mapping.get("key");
            cc.setKey(
                    (String)key.get("name"),
                    ClassType.get( (String) key.get( "type" ) )
                );
            addMappings( (Map<String,Object>)mapping.get("bean"), cc );
        }
        else
        if( "mapping-ref".equals(mapping.get("@tag")) ){
            cb.beanRef( (String) mapping.get( "name" ) );
        }*/
    }

    private void addMappings( Map<String,Object> mapping, MapBuilder mb )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{

        if( "mapping".equals(mapping.get("@tag")) ){
            BeanBuilder mbb = mb
                    .bean(
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            addMappingBean( mapping, mbb );
        }
        /*else
        if( "mapping-collection".equals(mapping.get("@tag")) ){
            CollectionBuilder cc = mb.collection(
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            addMappings( (Map<String,Object>)mapping.get("bean"), cc );
        }
        else
        if( "mapping-map".equals(mapping.get("@tag")) ){
            MapBuilder cc = mb.map(
                        (Class)Class.forName( (String) mapping.get( "target" ), true, Thread.currentThread().getContextClassLoader() )
                    );
            Map<String,Object> key = (Map<String,Object>)mapping.get("key");
            cc.setKey(
                    (String)key.get("name"),
                    ClassType.get( (String) key.get( "type" ) )
                );
            addMappings( (Map<String,Object>)mapping.get("bean"), cc );
        }
        else
        if( "mapping-ref".equals(mapping.get("@tag")) ){
            mb.beanRef( (String) mapping.get( "name" ) );
        }*/
    }

    private void addMappingBean( Map<String,Object> mapping, BeanBuilder bean )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        List<Map<String,Object>> properties = (List) mapping.get( "properties" );
        if( properties != null ){
            for( Map<String,Object> property: properties ){
                bean
                    .addProperty(
                        (String)property.get( "name" ),
                        (String)property.get( "property-name" ),
                        EnumerationType.valueOf( property.get( "enum-property" ).toString() ),
                        (String)property.get( "temporal-property" ),
                        (String)property.get( "mapping-name" ),
                        ScopeType.valueOf( ((String)property.get( "scope" )) ),
                        property.get( "factory" ) == null?
                            null :
                            (Type)(Class.forName( (String) property.get( "factory" ), true, Thread.currentThread().getContextClassLoader() )).newInstance()
                    );
            }
        }
    }

    @Deprecated
    private void addPropertyMappingBean( Map<String,Map<String,Object>> maps, Map<String,Object> property, BeanBuilder mbb ) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        mbb
            .addProperty(  
                (String)property.get( "name" ),
                (String)property.get( "property-name" ),
                EnumerationType.valueOf( property.get( "enum-property" ).toString() ),
                (String)property.get( "temporal-property" ),
                (String)property.get( "mapping-name" ),
                ScopeType.valueOf( ((String)property.get( "scope" )) ),
                property.get( "factory" ) == null?
                    null : 
                    (Type)(Class.forName( (String) property.get( "factory" ), true, Thread.currentThread().getContextClassLoader() )).newInstance()
            );
    }
    
    private void addProperty( Map<String,Object> property, WebFrameBuilder wfb ) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        wfb
            .addProperty(
                (String)property.get( "property-name" ),
                (String)property.get( "name" ),
                ScopeType.valueOf( ((String)property.get( "scope" )) ),
                EnumerationType.valueOf( property.get( "enum-property" ).toString() ),
                (String)property.get( "temporal-property" ),
                (String)property.get( "mapping-name" ),
                property.get( "factory" ) == null?
                    null : 
                    (Type)(Class.forName( (String) property.get( "factory" ), true, Thread.currentThread().getContextClassLoader() )).newInstance()
            );
    }
    
    private List<Class> getArgTypes( List<Map<String,Object>> args ) throws ClassNotFoundException{
        List<Class> list = new ArrayList<Class>();
        for( Map<String,Object> arg: args )
            list.add( ClassType.get( (String) arg.get( "class-type" ) ) );
        
        return list;
    }
    
    private void addMethod( Map<String,Object> method, WebFrameBuilder wfb ) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        List<Class> parameterTypes = getArgTypes( (List<Map<String,Object>>)method.get( "parameter" ) );
        MethodBuilder mb = wfb
            .addMethod( 
                (String)method.get( "name" ), 
                (String)method.get( "return-in" ), 
                (String)method.get( "page" ), 
                Boolean.valueOf( (String)method.get( "redirect" ) ),
                (String)method.get( "method-name" ), 
                parameterTypes.toArray( new Class[]{} )
            );
        
        List<Map<String,Object>> parameters = (List)method.get( "parameter" );
        for( Map<String,Object> param: parameters ){
            addParameter( param, mb );
        }

        throwSafe( mb, (List<Map<String,Object>>)method.get( "throw-safe" ) );

    }
    
    private void addParameter( Map<String,Object> parameter, MethodBuilder mb ) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        mb
            .addParameter( 
                (String)parameter.get( "name" ),  
                ScopeType.valueOf( ((String)parameter.get( "scope" )) ),
                EnumerationType.valueOf( ((String)parameter.get( "enum-property" )) ),
                (String)parameter.get( "temporal-property" ),
                (String)parameter.get( "mapping-name" ),
                parameter.get( "factory" ) == null?
                    null : 
                    (Type)(Class.forName( (String) parameter.get( "factory" ), true, Thread.currentThread().getContextClassLoader() )).newInstance()
            );
    }

    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }
    
}