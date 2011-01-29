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
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.web.WebFrame;
import org.brandao.brutos.web.http.DataInput;
import org.brandao.brutos.web.http.DataOutput;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorProcess implements InterceptorStack{

    private static Logger logger = LoggerProvider.getCurrentLoggerProvider().getLogger(InterceptorProcess.class.getName());
    private Form form;
    private List<org.brandao.brutos.mapping.Interceptor> stack;
    private ThreadLocal<Integer> stackPos;
            
    public InterceptorProcess() {
        this.stackPos = new ThreadLocal();
    }

    public synchronized void reset(){
        this.stack = null;
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
            
    private synchronized void createStack(){
        if( stack != null )
            return;

        createStackInterceptor0();
    }
    
    public void createStackInterceptor0(){
        this.stack = new LinkedList();
        
        List<org.brandao.brutos.mapping.Interceptor> interceptors = 
            form.getDefaultInterceptorList();


        if( interceptors != null ){
            for( org.brandao.brutos.mapping.Interceptor i: interceptors ){
                processInterceptor(
                        new StringBuffer()/*new StringBuffer( String.valueOf( form.hashCode() ) )*/,
                        i.getProperties(),
                        i
                );
            }
        }

        interceptors =
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
            Scope requestScope = Scopes.get(ScopeType.REQUEST.toString());
            org.brandao.brutos.mapping.Interceptor i = stack.get( pos );
            //IOCProvider iocProvider = (IOCProvider)handler.getContext().getAttribute( BrutosConstants.IOC_PROVIDER );
            IOCProvider iocProvider = (IOCProvider)requestScope.get( BrutosConstants.IOC_PROVIDER );

            Interceptor interceptor = interceptor = (Interceptor) iocProvider.getBean( i.getName() );
            
            if( !interceptor.isConfigured() )
                interceptor.setProperties( (Map<String, Object>) i.getProperty( String.valueOf( form.hashCode()  )  ) );

            if( interceptor.accept( handler ) ){
                if( logger.isDebugEnabled() )
                    logger.debug( this.form.getClassType().getName() + " intercepted by: " + i.getName() );

                interceptor.intercepted( this, handler );
            }
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
        Scope paramScope = Scopes.get(ScopeType.PARAM.toString());
        Scope requestScope = Scopes.get(ScopeType.REQUEST.toString());
        //HttpServletRequest request   = handler.getRequest();
        //ServletContext context       = handler.getContext();
        Object source                = handler.getResource();
        //MethodForm method            = form.getMethodByName(
        //                                    request.getParameter( form.getMethodId() ) );
        //MethodForm method            = form.getMethodByName(
        //                                     String.valueOf( paramScope.get( form.getMethodId() ) ) );
        MethodForm method            = handler.getResourceAction().getMethodForm();

        try{
            //DataInput input = new DataInput( handler.getRequest(),
            //                               handler.getResponse(),
            //                               handler.getContext() );
            DataInput input = new DataInput( requestScope );
            
            input.read( form , handler.getResource() );

            preAction( source );

            if( handler.getResourceAction() != null )
                return handler
                    .getResourceAction()
                        //.invoke( source, getParameters( method, request, context ) );
                        .invoke( source, getParameters( method, null, null ) );
            else
                return null;
        }
        catch( ValidatorException e ){
            processException( e, method, requestScope );
            return null;
        }
        catch( InvocationTargetException e ){
            if( e.getTargetException() instanceof RedirectException ){
                //request.setAttribute( BrutosConstants.REDIRECT, ((RedirectException)e.getTargetException()).getPage() );
                requestScope.put( BrutosConstants.REDIRECT, ((RedirectException)e.getTargetException()).getPage() );
            }
            else{
                processException( e.getTargetException(), method, requestScope );
                /*
                ThrowableSafeData tdata = method == null?
                    form.getThrowsSafe(
                        e.getTargetException().getClass() ) :
                    method.getThrowsSafe(
                        e.getTargetException().getClass() );

                if( tdata != null ){
                    //request.setAttribute(
                    requestScope.put(
                            BrutosConstants.EXCEPTION,
                            e.getTargetException() );

                    //request.setAttribute(
                    requestScope.put(
                            BrutosConstants.EXCEPTION_DATA, tdata );
                }
                else
                    throw new InterceptedException( e.getTargetException() );
                 */
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

            //DataOutput dataOutput = new DataOutput( handler.getRequest(), handler.getContext() );
            DataOutput dataOutput = new DataOutput();
            dataOutput.write( form, handler.getResource() );
            dataOutput.writeFields( form, handler.getResource() );
        }

    }

    private void processException( Throwable e, MethodForm method, Scope requestScope ){
            ThrowableSafeData tdata = method == null?
                form.getThrowsSafe(
                    e.getClass() ) :
                method.getThrowsSafe(
                    e.getClass() );

            if( tdata != null ){
                requestScope.put(
                        BrutosConstants.EXCEPTION,
                        e );

                requestScope.put(
                        BrutosConstants.EXCEPTION_DATA, tdata );
            }
            else
                throw new InterceptedException( e );
    }

    private Object[] getParameters( MethodForm method, HttpServletRequest request,
        ServletContext context ) throws InstantiationException, IllegalAccessException,
        ParseException {
        if( method != null ){
            Object[] values = new Object[ method.getParameters().size() ];

            int index = 0;
            for( ParameterMethodMapping p: method.getParameters() ){
                UseBeanData bean = p.getBean();
                //values[ p.getParameterName() - 1 ] = bean.getValue( context, request );
                values[ index++ ] = bean.getValue();
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
            Scope requestScope = Scopes.get(ScopeType.REQUEST.toString());
            Scope paramScope = Scopes.get(ScopeType.PARAM.toString());
            //HttpServletRequest request   = handler.getRequest();
            //HttpServletResponse response = handler.getResponse();
            //BrutosContext brutosContext     = BrutosContext.getCurrentInstance();
            //ServletContext context       = handler.getContext();
            ApplicationContext appContext = (ApplicationContext) requestScope.get( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );
            Object source                 = handler.getResource();
            String redirectView           = (String)requestScope.get( BrutosConstants.REDIRECT );
            Throwable objectThrow         = (Throwable)requestScope.get( BrutosConstants.EXCEPTION );
            ThrowableSafeData thr         = (ThrowableSafeData)requestScope.get( BrutosConstants.EXCEPTION_DATA );
            MethodForm method             = form.getMethodByName(
                                                String.valueOf(paramScope.get( form.getMethodId() )) );
            ViewProvider viewProvider     = appContext.getViewProvider();

            //String redirectPage          = (String)request.getAttribute( BrutosConstants.REDIRECT );
            //Throwable objectThrow        = (Throwable)request.getAttribute( BrutosConstants.EXCEPTION );
            //ThrowableSafeData thr        = (ThrowableSafeData)request.getAttribute( BrutosConstants.EXCEPTION_DATA );
            //MethodForm method            = form.getMethodByName(
            //                                    request.getParameter( form.getMethodId() ) );
            //ViewProvider viewProvider    = brutosContext.getViewProvider();

            if( redirectView != null ){
                //viewProvider.show( redirectPage, true, request, response, context );
                viewProvider.show(redirectView, DispatcherType.REDIRECT);
                return;
            }

            if( thr != null ){
                if( thr.getParameterName() != null )
                    requestScope.put(thr.getParameterName(), objectThrow);
                    //request.setAttribute( thr.getParameterName(), objectThrow );

                if( thr.getUri() != null ){
                    viewProvider.show(thr.getUri(), thr.getDispatcher());
                    //viewProvider.show( thr.getUri(), thr.isRedirect(), request, response, context );
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
                        //request.setAttribute( var, returnValue);
                        requestScope.put(var, returnValue);
                    }

                    if( method.getReturnPage() != null ){
                        //viewProvider.show( method.getReturnPage(),
                        //        method.isRedirect(), request,
                        //        response, context );
                        viewProvider.show(method.getReturnPage(), method.getDispatcherType());
                        return;
                    }
                    else
                    if( method.getReturnType() != null ){
                        //method.getReturnType().setValue( response, context, returnValue );
                        method.getReturnType().setValue(returnValue);
                        return;
                    }
                }
                
                String view = getView( source );
                view = view == null? form.getPage() : view;
                boolean redirect = form.isRedirect();
                //viewProvider.show( page, redirect, request, response, context );
                viewProvider.show(view, form.getDispatcherType());

            }

            
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private String getView( MethodForm method ){
        
        if( method != null && method.getReturnPage() != null )
            return method.getReturnPage();
        else
            return null;
    }

    private String getView( Object source ){
        if( source instanceof WebFrame && ((WebFrame)source).isUpdatable() )
            return ((WebFrame)source).getPage();
        else
            return null;
    }

    private boolean isUpdatable( Object source ){
        return source instanceof WebFrame? ((WebFrame)source).isUpdatable() : true;
    }
}
