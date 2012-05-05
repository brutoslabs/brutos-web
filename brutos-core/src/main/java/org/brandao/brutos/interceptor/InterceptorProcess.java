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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;
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
    private List stack;
    private ThreadLocal stackPos;
            
    public InterceptorProcess() {
        this.stackPos = new ThreadLocal();
    }

    public synchronized void reset(){
        this.stack = null;
    }
    
    public void process( InterceptorHandler handler ){
        if( stack == null )
            createStack();

        Integer oldPos = null;
        
        try{
            oldPos = (Integer) stackPos.get();
            stackPos.set( new Integer(0) );
            next( handler );
        }
        finally{
            if( oldPos == null )
                stackPos.remove();
            else
                stackPos.set(oldPos);
        }
    }
            
    private synchronized void createStack(){
        if( stack != null )
            return;

        createStackInterceptor0();
    }
    
    public void createStackInterceptor0(){
        this.stack = new LinkedList();
        
        List interceptors = 
            form.getDefaultInterceptorList();


        if( interceptors != null ){
            for( int idx=0;idx<interceptors.size();idx++ ){
                Interceptor i =
                        (Interceptor) interceptors.get(idx);
                processInterceptor(
                        new StringBuffer(),
                        i.getProperties(),
                        i
                );
            }
        }

        interceptors =
            form.getInterceptors();

            for( int idx=0;idx<interceptors.size();idx++ ){
                Interceptor i =
                        (Interceptor) interceptors.get(idx);
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
            Map propertiesScope,
            Interceptor interceptor ){
        
        if( interceptor instanceof org.brandao.brutos.mapping.InterceptorStack ){
            List ins = 
                    ((org.brandao.brutos.mapping.InterceptorStack)interceptor)
                        .getInterceptors();

            //for( org.brandao.brutos.mapping.Interceptor i: ins ){
            for( int idx=0;idx<ins.size();idx++ ){
                Interceptor i =
                        (Interceptor) ins.get(idx);
                
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
            Map properties = (Map)interceptor.getProperty( index );
            
            if( properties == null ){
                properties = getScopeProperties( path.toString(), propertiesScope,
                                            interceptor.getProperties() );
                interceptor.setProperty( index, properties );
            }
            
            stack.add( interceptor );
        }
    }

    private Map getScopeProperties( String path, 
            Map propertiesScope, Map properties ){
        
        Map props = new HashMap();
        
        Set keys = properties.keySet();
        Iterator iKeys = keys.iterator();

        //for( String key: keys ){
        while( iKeys.hasNext() ){
            String key = (String) iKeys.next();
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
        Integer pos = (Integer) stackPos.get();
        stackPos.set( new Integer(pos.intValue() + 1) );

        if( pos.intValue() < this.stack.size() )
            next0(handler, pos);
        else
            invoke( (ConfigurableInterceptorHandler)handler );
    }

    private void next0(InterceptorHandler handler, Integer pos)
            throws InterceptedException{

        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext) handler.getContext();

        Interceptor i = (Interceptor) stack.get( pos.intValue() );

        org.brandao.brutos.interceptor.Interceptor interceptor =
            (org.brandao.brutos.interceptor.Interceptor) i.getInstance(context.getIocProvider());

        if( !interceptor.isConfigured() )
            interceptor.setProperties( (Map) i
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

    private void invoke( ConfigurableInterceptorHandler handler ){
        /*Scope scope =
            handler.getContext().getScopes()
                .get(ScopeType.REQUEST);

        RequestInstrument requestInstrument =
                (RequestInstrument)
                    scope.get(BrutosConstants.REQUEST_INSTRUMENT);

        StackRequestElement stackRequestElement =
                ((StackRequest)requestInstrument).getCurrent();
        */
        
        
        invoke0( handler );
        
        RequestInstrument requestInstrument = handler.getRequestInstrument();
        StackRequestElement stackRequestElement = handler.getStackRequestElement();
        
        show(requestInstrument,stackRequestElement);
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
    
    private void executeAction( ConfigurableInterceptorHandler handler )
        throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ParseException{

        if(handler.getResourceAction() == null)
            return;
        Object[] args = handler.getParameters();
        /*    stackRequestElement.getParameters() == null?
                getParameters(stackRequestElement.getAction().getMethodForm() ) :
                stackRequestElement.getParameters();
        */
                
        ResourceAction action = handler.getResourceAction();
        Object source = handler.getResource();
        
        try{
            Object result = action.invoke(source, args);
            handler.setResult(result);
            handler.getStackRequestElement().setResultAction(result);
        }
        catch( IllegalArgumentException ex ){

            StringBuilder argsText = new StringBuilder();
            for( int i=0;i<args.length;i++ ){
                Object arg = args[i];
                argsText =
                    argsText.length() == 0?
                        argsText.append(arg) :
                        argsText.append(", ").append(arg);
            }

            StringBuilder exText = new StringBuilder();
            exText
                .append("can't invoke the action: ")
                .append(source.getClass().getName())
                .append(".")
                .append(action.getMethod().getName())
                .append("(")
                .append(argsText)
                .append(")");

            throw new IllegalArgumentException(exText.toString(), ex);
        }
    }

    public void invoke0( ConfigurableInterceptorHandler handler/*, RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement*/ ) {
        
        Scopes scopes      = handler.getContext().getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST.toString());
        Object resource    = handler.getResource();
        StackRequestElement stackRequestElement = handler.getStackRequestElement();
        
        
        try{
            DataInput input = new DataInput( requestScope );
            input.read( form , resource );
            preAction( resource );

            executeAction(handler);
        }
        catch( ValidatorException e ){
            processException(
                    handler.getStackRequestElement(),
                    e,
                    handler.getResourceAction().getMethodForm());
        }
        catch( InvocationTargetException e ){
            if( e.getTargetException() instanceof RedirectException ){
                RedirectException re = (RedirectException)e.getTargetException();
                stackRequestElement.setView(
                        re.getView());
                stackRequestElement.setDispatcherType(re.getDispatcher());
            }
            else{
                processException(
                    stackRequestElement,
                    e.getTargetException(),
                    stackRequestElement.getAction().getMethodForm());
            }
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Throwable e ){
            throw new BrutosException( e );
        }
        finally{
            postAction( resource );
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
            }
            else
            if( e instanceof BrutosException )
                throw (BrutosException)e;
            else
                throw new InterceptedException( e );
    }

    /**
     * @deprecated 
     * @param method
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ParseException 
     */
    private Object[] getParameters( MethodForm method )
            throws InstantiationException, IllegalAccessException,
        ParseException {
        if( method != null ){
            Object[] values = new Object[ method.getParameters().size() ];

            int index = 0;
            //for( ParameterMethodMapping p: method.getParameters() ){
            List params = method.getParameters();
            for( int i=0;i<params.size();i++ ){
                ParameterMethodMapping p = (ParameterMethodMapping) params.get(i);
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
                action.getPreAction().invoke( source, new Object[]{} );
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
                action.getPostAction().invoke( source, new Object[]{} );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

    }

}
