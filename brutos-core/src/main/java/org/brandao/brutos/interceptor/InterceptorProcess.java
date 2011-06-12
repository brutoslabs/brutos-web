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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.StackRequest;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorProcess implements InterceptorStack{

    private Logger logger =
            LoggerProvider.getCurrentLoggerProvider()
                .getLogger(InterceptorProcess.class.getName());
    
    private Controller form;
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
                        new StringBuffer(),
                        i.getProperties(),
                        i
                );
            }
        }

        interceptors =
            form.getInterceptors();

        for( org.brandao.brutos.mapping.Interceptor i: interceptors ){
            processInterceptor( 
                    new StringBuffer(),
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
    
    private void processInterceptor( StringBuffer path, 
            Map<String,Object> propertiesScope,
            org.brandao.brutos.mapping.Interceptor interceptor ){
        
        if( interceptor instanceof org.brandao.brutos.mapping.InterceptorStack ){
            List<org.brandao.brutos.mapping.Interceptor> ins = 
                    ((org.brandao.brutos.mapping.InterceptorStack)interceptor)
                        .getInterceptors();

            for( org.brandao.brutos.mapping.Interceptor i: ins ){
                
                if( path.indexOf( i.getName() ) == -1 ){
                    processInterceptor( 
                        path.append( i.getName() ).append( "." ),
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
                properties = getScopeProperties( path.toString(), propertiesScope,
                                            interceptor.getProperties() );
                interceptor.setProperty( index, properties );
            }
            
            stack.add( interceptor );
        }
    }

    private Map<String,Object> getScopeProperties( String path, 
            Map<String,Object> propertiesScope, Map<String,Object> properties ){
        
        Map<String,Object> props = new HashMap();
        
        Set<String> keys = properties.keySet();
        
        for( String key: keys ){
            Object value = propertiesScope.get( path + key );
            
            value = value == null? properties.get( key ) : value;
            props.put( key, value );
        }
        
        return props;
    }

    public Controller getForm() {
        return form;
    }

    public void setForm(Controller form) {
        this.form = form;
    }

    public void next(InterceptorHandler handler) throws InterceptedException{
        int pos = stackPos.get();
        stackPos.set( pos + 1 );

        if( pos < this.stack.size() )
            next0(handler, pos);
        else
            invoke( handler );
    }

    private void next0(InterceptorHandler handler, int pos)
            throws InterceptedException{
        /*
        Scope requestScope =
            handler.getContext().getScopes().get(ScopeType.REQUEST.toString());

        org.brandao.brutos.mapping.Interceptor i = stack.get( pos );

        IOCProvider iocProvider =
            (IOCProvider)requestScope.get( BrutosConstants.IOC_PROVIDER );
        */

        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext) handler.getContext();

        org.brandao.brutos.mapping.Interceptor i = stack.get( pos );

        Interceptor interceptor =
            interceptor = (Interceptor)i.getInstance(context.getIocProvider());
            /*iocProvider.getBean( i.getName() );*/

        if( !interceptor.isConfigured() )
            interceptor.setProperties( (Map<String, Object>) i
                    .getProperty( String.valueOf( form.hashCode()  )  ) );

        if( interceptor.accept( handler ) ){
            if( logger.isDebugEnabled() )
                logger.debug( this.form.getClassType().getName() +
                        " intercepted by: " + i.getName() );

            interceptor.intercepted( this, handler );
        }
        else
            next( handler );

    }

    private void invoke( InterceptorHandler handler ){
        Scope scope =
            handler.getContext().getScopes()
                .get(ScopeType.REQUEST);

        RequestInstrument requestInstrument =
                (RequestInstrument)
                    scope.get(BrutosConstants.REQUEST_INSTRUMENT);

        StackRequestElement stackRequestElement =
                ((StackRequest)requestInstrument).getCurrent();
        Object result = null;
        try{
            result = invoke0( requestInstrument, stackRequestElement );
            stackRequestElement.setResultAction(result);
        }
        finally{
            show(requestInstrument,stackRequestElement);
        }
    }

    private void show(RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement){
        try{
            requestInstrument
                    .getViewProvider().show(requestInstrument, stackRequestElement);
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }
    private Object executeAction( StackRequestElement stackRequestElement )
        throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ParseException{

        Object[] args =
            stackRequestElement.getParameters() == null?
                getParameters(stackRequestElement.getAction().getMethodForm() ) :
                stackRequestElement.getParameters();

        Object source = stackRequestElement.getResource();

        ResourceAction action = stackRequestElement.getAction();

        return action.invoke(source, args);
    }

    public Object invoke0( RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement ) {
        
        Scopes scopes      = requestInstrument.getContext().getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST.toString());
        Object source      = stackRequestElement.getResource();
        
        try{
            DataInput input = new DataInput( requestScope );
            input.read( form , stackRequestElement.getResource() );
            preAction( source );

            if( stackRequestElement.getAction() != null )
                return executeAction(stackRequestElement);
            else
                return null;
        }
        catch( ValidatorException e ){
            processException(
                    stackRequestElement,
                    e,
                    stackRequestElement.getAction().getMethodForm());
            return null;
        }
        catch( InvocationTargetException e ){
            if( e.getTargetException() instanceof RedirectException ){
                stackRequestElement.setView(
                        ((RedirectException)e.getTargetException()).getPage());
                stackRequestElement.setDispatcherType(DispatcherType.REDIRECT);
                /*
                requestScope.put( 
                    BrutosConstants.REDIRECT,
                    ((RedirectException)e.getTargetException()).getPage() );
                 */
            }
            else{
                processException(
                    stackRequestElement,
                    e.getTargetException(),
                    stackRequestElement.getAction().getMethodForm());
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
            DataOutput dataOutput = new DataOutput(scopes.get(ScopeType.REQUEST));
            dataOutput.write( form, stackRequestElement.getResource() );
            dataOutput.writeFields( form, stackRequestElement.getResource() );
        }

    }

    private void processException( StackRequestElement stackRequestElement,
            Throwable e, MethodForm method ){
            ThrowableSafeData tdata = method == null?
                form.getThrowsSafe(
                    e.getClass() ) :
                method.getThrowsSafe(
                    e.getClass() );

            if( tdata != null ){
                stackRequestElement.setObjectThrow(e);
                stackRequestElement.setThrowableSafeData(tdata);
                /*
                requestScope.put(
                        BrutosConstants.EXCEPTION,
                        e );

                requestScope.put(
                        BrutosConstants.EXCEPTION_DATA, tdata );
                 */
            }
            else
                throw new InterceptedException( e );
    }

    private Object[] getParameters( MethodForm method )
            throws InstantiationException, IllegalAccessException,
        ParseException {
        if( method != null ){
            Object[] values = new Object[ method.getParameters().size() ];

            int index = 0;
            for( ParameterMethodMapping p: method.getParameters() ){
                UseBeanData bean = p.getBean();
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

    /*
    private void show( InterceptorHandler handler, Object returnValue ){
        try{
            ConfigurableApplicationContext appContext =
                    new ConfigurableApplicationContextImp(handler.getContext());
            
            Scopes scopes         =
                handler.getContext().getScopes();
            Scope requestScope    =
                scopes.get(ScopeType.REQUEST.toString());
            String redirectView   =
                (String)requestScope.get( BrutosConstants.REDIRECT );
            Throwable objectThrow =
                (Throwable)requestScope.get( BrutosConstants.EXCEPTION );
            ThrowableSafeData thr =
                (ThrowableSafeData)requestScope.get( BrutosConstants.EXCEPTION_DATA );
            MethodForm method     = 
                handler.getResourceAction().getMethodForm();
            
            ViewProvider viewProvider     = appContext.getViewProvider();

            if( redirectView != null ){
                viewProvider.show(redirectView, DispatcherType.REDIRECT);
                return;
            }

            if( thr != null ){
                if( thr.getParameterName() != null )
                    requestScope.put(thr.getParameterName(), objectThrow);

                if( thr.getUri() != null ){
                    viewProvider.show(thr.getUri(), thr.getDispatcher());
                    return;
                }
            }
            
            if( method != null ){

                if( method.getReturnClass() != void.class ){
                    String var =
                        method.getReturnIn() == null?
                            BrutosConstants.DEFAULT_RETURN_NAME :
                            method.getReturnIn();
                    requestScope.put(var, returnValue);
                }

                if( method.getReturnPage() != null ){
                    viewProvider.show(method.getReturnPage(),
                            method.getDispatcherType());
                    return;
                }
                else
                if( method.getReturnType() != null ){
                    method.getReturnType().setValue(returnValue);
                    return;
                }
            }

            String view = form.getPage();
            viewProvider.show(view, form.getDispatcherType());

        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    */
    
    /*
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
    */
}
