

package org.brandao.brutos.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.validator.ValidatorException;


public class InterceptorProcess implements InterceptorStack{

    private Logger logger =
            LoggerProvider.getCurrentLoggerProvider()
                .getLogger(InterceptorProcess.class.getName());
    
    private Controller form;
    
    private InterceptorEntry start;
    
    private ThreadLocal<InterceptorEntry> stackPos;
            
    public InterceptorProcess() {
        this.stackPos = new ThreadLocal<InterceptorEntry>();
    }

    public synchronized void reset(){
        this.start = null;
    }
    
    public void process( InterceptorHandler handler ){
        
    	InterceptorEntry oldPos = null;
        
        try{
            oldPos = stackPos.get();
            stackPos.set( this.start );
            next( handler );
        }
        finally{
            if( oldPos == null )
                stackPos.remove();
            else
                stackPos.set(oldPos);
        }
    }
            
    public synchronized void flush(){
    	InterceptorProcessConfigurationBuilder
    		ipcb = new InterceptorProcessConfigurationBuilder(this.form);
    	this.start = ipcb.getStack();
    }

    
    
    public Controller getForm() {
        return form;
    }

    public void setForm(Controller form) {
        this.form = form;
    }

    public void next(InterceptorHandler handler) throws InterceptedException{
    	InterceptorEntry pos = stackPos.get();
    	InterceptorEntry next = pos.getNext();
    	
        stackPos.set(next);

        if( next != null )
        	next(handler, next);
        else
            invoke( (ConfigurableInterceptorHandler)handler );
    }

    private void next(InterceptorHandler handler, InterceptorEntry pos)
            throws InterceptedException{

        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext) handler.getContext();

        Interceptor i = pos.getInterceptor();

        org.brandao.brutos.interceptor.InterceptorController interceptor =
            (org.brandao.brutos.interceptor.InterceptorController) i.getInstance(context.getObjectFactory());

        if( !interceptor.isConfigured() )
            interceptor.setProperties(i.getProperties());
        
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
        
        RequestInstrument requestInstrument = handler.getRequestInstrument();
        StackRequestElement stackRequestElement = handler.getStackRequestElement();
        Throwable objectThrow = stackRequestElement.getObjectThrow();
        
        if(objectThrow == null)
            invoke0( handler, stackRequestElement );
        else
            processException(
                stackRequestElement,
                objectThrow,
                stackRequestElement.getAction());        
        
        show(requestInstrument,stackRequestElement);
    }

    private void show(RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement){
        try{
            requestInstrument
                    .getRenderView().show(requestInstrument, stackRequestElement);
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

    public void invoke0( ConfigurableInterceptorHandler handler, 
            StackRequestElement stackRequestElement ) {
        
        Scopes scopes      = handler.getContext().getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST.toString());
        Object resource    = handler.getResource();
        
        
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
                    handler.getResourceAction());
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
                    stackRequestElement.getAction());
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
            Throwable e, ResourceAction resourceAction ){
        
        Action method = resourceAction == null? 
                null : 
                resourceAction.getMethodForm();
        
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

    private void preAction( Object source ) {
        try{
            ActionListener action = this.form.getActionListener();
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
            ActionListener action = this.form.getActionListener();
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
