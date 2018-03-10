/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.MutableMvcResponse;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.validator.ValidatorException;

/**
 * 
 * @author Brandao
 */
public class InterceptorProcess implements InterceptorStack {

	private Logger logger = LoggerProvider.getCurrentLoggerProvider()
			.getLogger(InterceptorProcess.class.getName());

	private Controller form;

	private InterceptorEntry start;

	private ThreadLocal<InterceptorEntry> stackPos;

	public InterceptorProcess() {
		this.stackPos = new ThreadLocal<InterceptorEntry>();
	}

	public synchronized void reset() {
		this.start = null;
	}

	public void process(InterceptorHandler handler) {

		InterceptorEntry oldPos = null;

		try {
			oldPos = stackPos.get();
			stackPos.set(this.start);
			next(handler);
		} finally {
			if (oldPos == null)
				stackPos.remove();
			else
				stackPos.set(oldPos);
		}
	}

	public synchronized void flush() {
		InterceptorProcessConfigurationBuilder ipcb = new InterceptorProcessConfigurationBuilder(
				this.form);
		this.start = ipcb.getStack();
	}

	public Controller getForm() {
		return form;
	}

	public void setForm(Controller form) {
		this.form = form;
	}

	public void next(InterceptorHandler handler) throws InterceptedException {
		InterceptorEntry pos = stackPos.get();
		InterceptorEntry next = pos.getNext();

		stackPos.set(next);

		if (next != null)
			next(handler, next);
		else
			invoke((ConfigurableInterceptorHandler) handler);
	}

	private void next(InterceptorHandler handler, InterceptorEntry pos)
			throws InterceptedException {

		ConfigurableApplicationContext context = (ConfigurableApplicationContext) handler
				.getContext();

		Interceptor i = pos.getInterceptor();

		org.brandao.brutos.interceptor.InterceptorController interceptor = 
			(org.brandao.brutos.interceptor.InterceptorController)
				i.getInstance(context.getObjectFactory());

		if (!interceptor.isConfigured())
			interceptor.setProperties(i.getProperties());

		if (interceptor.accept(handler)) {
			if (logger.isDebugEnabled())
				logger.debug(this.form.getClassType().getName()
						+ " intercepted by: " + i.getName());

			interceptor.intercepted(this, handler);
		} else
			next(handler);

	}

	private void invoke(ConfigurableInterceptorHandler handler) {

		StackRequestElement stackRequestElement = 
				handler.getRequest().getStackRequestElement();
				
		Throwable objectThrow = stackRequestElement.getObjectThrow();
		Object resource       = handler.getResource();
		
		try{
			this.preAction(resource, stackRequestElement);
			
			if (objectThrow == null)
				invoke0(handler, stackRequestElement);
			else
				processException(stackRequestElement, objectThrow,
						stackRequestElement.getAction());
			}
		finally{
			this.postAction(resource, stackRequestElement);
		}
		
	}
	
	private void executeAction(ConfigurableInterceptorHandler handler)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, ParseException {

		if (handler.getResourceAction() == null)
			return;
		Object[] args = handler.getParameters();

		ResourceAction action = handler.getResourceAction();
		Object source = handler.getResource();

		try {
			Object result = action.invoke(source, args);
			MutableMvcResponse response = (MutableMvcResponse)handler.getResponse();
			response.setResult(result);
			handler.getRequest().getStackRequestElement().setResultAction(result);
		}
		catch (IllegalArgumentException ex) {

			StringBuilder argsText = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				argsText = argsText.length() == 0 ? argsText.append(arg)
						: argsText.append(", ").append(arg);
			}

			StringBuilder exText = new StringBuilder();
			exText.append("can't invoke the action: ")
					.append(source.getClass().getName()).append(".")
					.append(action.getMethod().getName()).append("(")
					.append(argsText).append(")");

			throw new IllegalArgumentException(exText.toString(), ex);
		}
	}

	public void invoke0(ConfigurableInterceptorHandler handler,
			StackRequestElement stackRequestElement) {

		try{
			executeAction(handler);
		}
		catch(ValidatorException e) {
			this.processException(
					handler.getRequest().getStackRequestElement(), 
					e,
					handler.getResourceAction());
		}
		catch(InvocationTargetException e){
			if (e.getTargetException() instanceof RedirectException) {
				RedirectException re = 
					(RedirectException)e.getTargetException();
				stackRequestElement.setView(re.getView());
				stackRequestElement.setDispatcherType(re.getDispatcher());
			}
			else{
				processException(
					stackRequestElement, 
					e.getTargetException(),
					stackRequestElement.getAction());
			}
		}
		catch (BrutosException e) {
			throw e;
		}
		catch (Throwable e) {
			throw new BrutosException(e);
		}

	}

	private void processException(StackRequestElement stackRequestElement,
			Throwable e, ResourceAction resourceAction) {

		Action method = 
				resourceAction == null ? 
					null : 
					resourceAction.getMethodForm();

		ThrowableSafeData tdata = null;
		
		if(method != null){
			tdata = method.getThrowsSafe(e.getClass());
		}
		else{
			tdata = form.getThrowsSafe(e.getClass());
		}

		if(tdata != null) {
			stackRequestElement.getRequest().setThrowable(e);
			stackRequestElement.setObjectThrow(e);
			stackRequestElement.setThrowableSafeData(tdata);
		}
		else
		if (e instanceof BrutosException){
			throw (BrutosException) e;
		}
		else{
			throw new InterceptedException(e);
		}
	}

	private void preAction(Object source, StackRequestElement element) {
		try {
			ActionListener action = this.form.getActionListener();
			if (action.getPreAction() != null) {
				action.getPreAction().setAccessible(true);
				action.getPreAction().invoke(source, new Object[] {element});
			}
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	private void postAction(Object source, StackRequestElement element) {
		try {
			ActionListener action = this.form.getActionListener();
			if (action.getPostAction() != null) {
				action.getPostAction().setAccessible(true);
				action.getPostAction().invoke(source, new Object[] {element});
			}
		} catch (Exception e) {
			throw new BrutosException(e);
		}

	}

}
