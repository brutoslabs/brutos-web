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

package org.brandao.brutos.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.WebFrame;
import org.brandao.brutos.http.DataInput;
import org.brandao.brutos.http.DataOutput;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorProcess implements InterceptorStack{
    
    private Form form;
    private List<org.brandao.brutos.mapping.Interceptor> stack;
    private ThreadLocal<Integer> stackPos;
            
    public InterceptorProcess() {
        this.stackPos = new ThreadLocal();
    }

    public void process( InterceptorHandler handler ){
        if( stack == null )
            createStack();
        
        try{
            stackPos.set( 0 );
            next( handler );
        }
        //catch( InterceptedException e ){
            //suppress
        //}
        finally{
            stackPos.remove();
        }
    }
            
    @Deprecated
    private void createStack(){
        synchronized( this ){
            if( stack != null )
                return;
            
            createStackInterceptor0();
        }
    }
    
    @Deprecated
    public void createStackInterceptor0(){
        this.stack = new LinkedList();
        
        List<org.brandao.brutos.mapping.Interceptor> interceptors = 
            form.getInterceptors();
                
        for( org.brandao.brutos.mapping.Interceptor i: interceptors ){
            processInterceptor( 
                    new StringBuffer()/*new StringBuffer( String.valueOf( form.hashCode() ) )*/,
                    i.getProperties(), 
                    i 
            );
        }

    }

    public void addInterceptor( org.brandao.brutos.mapping.Interceptor i ){
        processInterceptor( 
                new StringBuffer( String.valueOf( form.hashCode() ) ), 
                i.getProperties(), 
                i 
        );
    }
    
    private void processInterceptor( StringBuffer path, Map<String,Object> propertiesScope, org.brandao.brutos.mapping.Interceptor interceptor ){
        //id
        //map
        if( interceptor instanceof org.brandao.brutos.mapping.InterceptorStack ){
            List<org.brandao.brutos.mapping.Interceptor> ins = 
                    ((org.brandao.brutos.mapping.InterceptorStack)interceptor).getInterceptors();

            for( org.brandao.brutos.mapping.Interceptor i: ins ){
                
                if( path.indexOf( i.getName() ) == -1 ){
                    processInterceptor( 
                        path/*.append( "." )*/.append( i.getName() ).append( "." ),
                        propertiesScope,
                        i
                    );
                }
            }
        }
        else{
            String index = String.valueOf( form.hashCode() );
            Map<String,Object> properties = (Map)interceptor.getProperty( index );
            
            if( properties == null ){
                properties = getScopeProperties( path.toString()/*index*/, propertiesScope,
                                            interceptor.getProperties() );
                interceptor.setProperty( index, properties );
            }
            
            stack.add( interceptor );
        }
    }

    private Map<String,Object> getScopeProperties( String path, Map<String,Object> propertiesScope, Map<String,Object> properties ){
        Map<String,Object> props = new HashMap();
        
        Set<String> keys = properties.keySet();
        
        for( String key: keys ){
            Object value = propertiesScope.get( path + /*"." +*/ key );
            
            value = value == null? properties.get( key ) : value;
            props.put( key, value );
        }
        
        return props;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void next(InterceptorHandler handler) throws InterceptedException{
        int pos = stackPos.get();
        stackPos.set( pos + 1 );
        
        if( pos < this.stack.size() ){
            org.brandao.brutos.mapping.Interceptor i = stack.get( pos );
            IOCProvider iocProvider = (IOCProvider)handler.getContext().getAttribute( BrutosConstants.IOC_PROVIDER );

            Interceptor interceptor = interceptor = (Interceptor) iocProvider.getBean( i.getName() );
            
            if( !interceptor.isConfigured() )
                interceptor.setProperties( (Map<String, Object>) i.getProperty( String.valueOf( form.hashCode()  )  ) );

            if( interceptor.accept( handler ) )
                interceptor.intercepted( this, handler );
            else
                next( handler );
        }
        else{
            Object result = null;
            try{
                //DataInput input = new DataInput( handler.getRequest(),
                //                               handler.getResponse(),
                //                               handler.getContext() );
                //input.read( form , handler.getResource() );
                //if( handler.getResourceMethod() != null )
                //    result = invoke( handler );
                result = invoke( handler );
            }
            finally{
                //DataOutput dataOutput = new DataOutput( handler.getRequest(), handler.getContext() );
                //dataOutput.write( form, handler.getResource() );
                //dataOutput.writeFields( form, handler.getResource() );
                show( handler, result );
            }
        }
    }

    public Object invoke( InterceptorHandler handler ) {
        HttpServletRequest request   = handler.getRequest();
        ServletContext context       = handler.getContext();
        Object source                = handler.getResource();
        MethodForm method            = form.getMethodByName(
                                            request.getParameter( form.getMethodId() ) );
        try{
            DataInput input = new DataInput( handler.getRequest(),
                                           handler.getResponse(),
                                           handler.getContext() );
            input.read( form , handler.getResource() );

            preAction( source );

            if( handler.getResourceMethod() != null )
                return handler
                    .getResourceMethod()
                        .invoke( source, getParameters( method, request, context ) );
            else
                return null;
        }
        catch( InvocationTargetException e ){
            if( e.getTargetException() instanceof RedirectException ){
                request.setAttribute( BrutosConstants.REDIRECT, ((RedirectException)e.getTargetException()).getPage() );
            }
            else{
                ThrowableSafeData tdata = method == null?
                    form.getThrowsSafe(
                        e.getTargetException().getClass() ) :
                    method.getThrowsSafe(
                        e.getTargetException().getClass() );

                if( tdata != null ){
                    request.setAttribute(
                            BrutosConstants.EXCEPTION,
                            e.getTargetException() );

                    request.setAttribute(
                            BrutosConstants.EXCEPTION_DATA, tdata );
                }
                else
                    throw new InterceptedException( e.getTargetException() );
            }

            return null;
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Throwable e ){
            throw new BrutosException( e );
        }
        finally{

            postAction( source );

            DataOutput dataOutput = new DataOutput( handler.getRequest(), handler.getContext() );
            dataOutput.write( form, handler.getResource() );
            dataOutput.writeFields( form, handler.getResource() );
        }

    }

    private Object[] getParameters( MethodForm method, HttpServletRequest request,
        ServletContext context ) throws InstantiationException, IllegalAccessException,
        ParseException {
        if( method != null ){
            Object[] values = new Object[ method.getParameters().size() ];

            for( ParameterMethodMapping p: method.getParameters() ){
                UseBeanData bean = p.getBean();
                values[ p.getParameterName() - 1 ] = bean.getValue( context, request );
            }

            return values;
        }
        else
            return null;
    }

    private void preAction( Object source ) {
        try{
            Action action = this.form.getAcion();
            if( action.getPreAction() != null ){
                action.getPreAction().setAccessible( true );
                action.getPreAction().invoke( source );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private void postAction( Object source ) {
        try{
            Action action = this.form.getAcion();
            if( action.getPostAction() != null ){
                action.getPostAction().setAccessible( true );
                action.getPostAction().invoke( source );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

    }

    private void show( InterceptorHandler handler, Object returnValue ){
        try{
            HttpServletRequest request   = handler.getRequest();
            HttpServletResponse response = handler.getResponse();
            BrutosContext brutosContext     = BrutosContext.getCurrentInstance();
            ServletContext context       = handler.getContext();
            Object source                = handler.getResource();
            String redirectPage          = (String)request.getAttribute( BrutosConstants.REDIRECT );
            Throwable objectThrow        = (Throwable)request.getAttribute( BrutosConstants.EXCEPTION );
            ThrowableSafeData thr        = (ThrowableSafeData)request.getAttribute( BrutosConstants.EXCEPTION_DATA );
            MethodForm method            = form.getMethodByName(
                                                request.getParameter( form.getMethodId() ) );
            ViewProvider viewProvider    = brutosContext.getViewProvider();

            if( redirectPage != null ){
                viewProvider.show( redirectPage, true, request, response, context );
                return;
            }

            if( thr != null ){
                if( thr.getParameterName() != null )
                    request.setAttribute( thr.getParameterName(), objectThrow );

                if( thr.getUri() != null ){
                    viewProvider.show( thr.getUri(), thr.isRedirect(), request, response, context );
                    return;
                }
            }
            
            if( isUpdatable( source ) ){

                if( method != null ){

                    if( method.getReturnClass() != void.class ){
                        String var =
                            method.getReturnIn() == null?
                                BrutosConstants.DEFAULT_RETURN_NAME :
                                method.getReturnIn();
                        request.setAttribute( var, returnValue);
                    }

                    if( method.getReturnPage() != null ){
                        viewProvider.show( method.getReturnPage(), 
                                method.isRedirect(), request,
                                response, context );
                        return;
                    }
                    else
                    if( method.getReturnType() != null ){
                        method.getReturnType().setValue( response, context, returnValue );
                        return;
                    }
                }
                
                String page = getPage( source );
                page = page == null? form.getPage() : page;
                boolean redirect = form.isRedirect();
                viewProvider.show( page, redirect, request, response, context );

            }

            
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private String getPage( MethodForm method ){
        
        if( method != null && method.getReturnPage() != null )
            return method.getReturnPage();
        else
            return null;
    }

    private String getPage( Object source ){
        if( source instanceof WebFrame && ((WebFrame)source).isUpdatable() )
            return ((WebFrame)source).getPage();
        else
            return null;
    }

    private boolean isUpdatable( Object source ){
        return source instanceof WebFrame? ((WebFrame)source).isUpdatable() : true;
    }
}