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

package org.brandao.brutos.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.interceptor.Interceptors;
import org.brandao.brutos.ioc.PicoContainerIOCProvider;
import org.brandao.brutos.ioc.picocontainer.PicoContainerScopes;
import org.brandao.brutos.ioc.picocontainer.ProtoTypeScope;
import org.brandao.brutos.ioc.picocontainer.SingletonScope;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.programatic.IOCManager;
import org.brandao.brutos.programatic.InterceptorManager;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorConfigurationTest extends TestCase{

    @Intercepts
    public static class MyInterceptor extends AbstractInterceptor{

        public void intercepted(InterceptorStack stack, InterceptorHandler handler) throws InterceptedException {
        }

    }

    @Intercepts( name="myInterceptor" )
    public static class NamedInterceptor extends AbstractInterceptor{

        public void intercepted(InterceptorStack stack, InterceptorHandler handler) throws InterceptedException {
        }

    }

    @Intercepts( params=@Param( name="paramName", value="paramValue" ) )
    public static class ParamInterceptor extends AbstractInterceptor{

        public void intercepted(InterceptorStack stack, InterceptorHandler handler) throws InterceptedException {
        }

    }

    @Intercepts( isDefault=true )
    public static class DefaultInterceptor extends AbstractInterceptor{

        public void intercepted(InterceptorStack stack, InterceptorHandler handler) throws InterceptedException {
        }

    }

    @Intercepts
    public static class MyInterceptorStack implements Interceptors{

        public Class<? extends org.brandao.brutos.interceptor.Interceptor>[] getInterceptors() {
            return new Class[]{ MyInterceptor.class, NamedInterceptor.class };
        }

    }

    public void testConfigurationDefault(){
        InterceptorManager iManager = new InterceptorManager( new IOCManager( new PicoContainerIOCProvider() ) );
        PicoContainerScopes.register( "singleton", new SingletonScope() );
        PicoContainerScopes.register( "prototype", new ProtoTypeScope() );

        InterceptorConfiguration ic = new InterceptorConfiguration();

        List<Class> interceptors = new ArrayList<Class>();
        interceptors.add( MyInterceptor.class );
        ic.setResource(iManager);
        ic.setSource(interceptors);
        ic.configure();

        Interceptor interceptor = iManager.getInterceptor(MyInterceptor.class.getSimpleName());

        assertNotNull(interceptor);
        assertEquals( MyInterceptor.class, interceptor.getType() );
    }

    public void testNamed(){
        InterceptorManager iManager = new InterceptorManager( new IOCManager( new PicoContainerIOCProvider() ) );
        PicoContainerScopes.register( "singleton", new SingletonScope() );
        PicoContainerScopes.register( "prototype", new ProtoTypeScope() );

        InterceptorConfiguration ic = new InterceptorConfiguration();

        List<Class> interceptors = new ArrayList<Class>();
        interceptors.add( NamedInterceptor.class );
        ic.setResource(iManager);
        ic.setSource(interceptors);
        ic.configure();

        Interceptor interceptor = iManager.getInterceptor("myInterceptor");

        assertNotNull(interceptor);
        assertEquals( NamedInterceptor.class, interceptor.getType() );
    }

    public void testParams(){
        InterceptorManager iManager = new InterceptorManager( new IOCManager( new PicoContainerIOCProvider() ) );
        PicoContainerScopes.register( "singleton", new SingletonScope() );
        PicoContainerScopes.register( "prototype", new ProtoTypeScope() );

        InterceptorConfiguration ic = new InterceptorConfiguration();

        List<Class> interceptors = new ArrayList<Class>();
        interceptors.add( ParamInterceptor.class );
        ic.setResource(iManager);
        ic.setSource(interceptors);
        ic.configure();

        Interceptor interceptor = iManager.getInterceptor(ParamInterceptor.class.getSimpleName());

        assertNotNull(interceptor);
        assertEquals( ParamInterceptor.class, interceptor.getType() );
        
        Map props = interceptor.getProperties();
        assertNotNull(props );
        assertEquals( "paramValue", props.get("paramName") );
    }

    public void testDefault(){
        InterceptorManager iManager = new InterceptorManager( new IOCManager( new PicoContainerIOCProvider() ) );
        PicoContainerScopes.register( "singleton", new SingletonScope() );
        PicoContainerScopes.register( "prototype", new ProtoTypeScope() );

        InterceptorConfiguration ic = new InterceptorConfiguration();

        List<Class> interceptors = new ArrayList<Class>();
        interceptors.add( DefaultInterceptor.class );
        ic.setResource(iManager);
        ic.setSource(interceptors);
        ic.configure();

        List<Interceptor> listInterceptors = iManager.getDefaultInterceptors();

        assertNotNull( listInterceptors );
        assertTrue( listInterceptors.size() > 0 );
        Interceptor interceptor = listInterceptors.get(0);

        assertNotNull(interceptor);
        assertEquals( DefaultInterceptor.class, interceptor.getType() );
    }

    public void testInterceptorStack(){
        InterceptorManager iManager = new InterceptorManager( new IOCManager( new PicoContainerIOCProvider() ) );
        PicoContainerScopes.register( "singleton", new SingletonScope() );
        PicoContainerScopes.register( "prototype", new ProtoTypeScope() );

        InterceptorConfiguration ic = new InterceptorConfiguration();

        List<Class> interceptors = new ArrayList<Class>();
        interceptors.add( MyInterceptorStack.class );
        interceptors.add( MyInterceptor.class );
        interceptors.add( NamedInterceptor.class );
        ic.setResource(iManager);
        ic.setSource(interceptors);
        ic.configure();

        Interceptor interceptor = iManager.getInterceptor(MyInterceptorStack.class.getSimpleName());

        assertNotNull(interceptor);
        assertTrue( interceptor instanceof InterceptorStack );
        assertEquals( MyInterceptorStack.class, interceptor.getType() );

        List<Interceptor> listInterceptor =
                ((org.brandao.brutos.mapping.InterceptorStack)interceptor)
                    .getInterceptors();

        assertNotNull( listInterceptor );
        assertTrue( listInterceptor.size() == 2 );

        assertEquals( MyInterceptor.class, listInterceptor.get(0).getType() );
        assertEquals( NamedInterceptor.class, listInterceptor.get(1).getType() );
    }

}
