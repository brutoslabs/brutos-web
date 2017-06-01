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

package org.brandao.brutos;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;

/**
 * 
 * @author Brandao
 */
public abstract class AbstractRenderView 
	implements RenderViewType {

	protected abstract void show(RequestInstrument requestInstrument,
			String view, DispatcherType dispatcherType);

	private void showView(RequestInstrument requestInstrument, String view,
			DispatcherType dispatcherType){
		requestInstrument.setHasViewProcessed(true);
		show(requestInstrument, view, dispatcherType);
	}

	private void showView(RequestInstrument requestInstrument,
			StackRequestElement stackRequestElement, Type type){
		requestInstrument.setHasViewProcessed(true);
		type.show(stackRequestElement.getResponse(), stackRequestElement.getResultAction());
	}

	public void show(MvcRequest request, MvcResponse response){
		RequestInstrument requestInstrument     = request.getRequestInstrument();
		StackRequestElement stackRequestElement = request.getStackRequestElement();

		if (requestInstrument.isHasViewProcessed())
			return;

		Scopes scopes = requestInstrument.getContext().getScopes();
		Scope requestScope = scopes.get(ScopeType.REQUEST.toString());

		Action method = stackRequestElement.getAction() == null ? null
				: stackRequestElement.getAction().getMethodForm();

		ThrowableSafeData throwableSafeData = stackRequestElement
				.getThrowableSafeData();

		Object objectThrow = stackRequestElement.getObjectThrow();

		if (throwableSafeData != null) {
			if (throwableSafeData.getParameterName() != null)
				requestScope.put(throwableSafeData.getParameterName(),
						objectThrow);

			if (throwableSafeData.getView() != null) {
				this.showView(requestInstrument, throwableSafeData.getView(),
						throwableSafeData.getDispatcher());
				return;
			}
		}

		if (stackRequestElement.getView() != null) {
			this.showView(requestInstrument, stackRequestElement.getView(),
					stackRequestElement.getDispatcherType());
			return;
		}

		if (method != null) {
			org.brandao.brutos.mapping.ResultAction resultAction =
					method.getResultAction();
			if (resultAction.getType() != null) {
			//if (method.getReturnClass() != void.class) {
				String var = 
						resultAction.getName() == null? 
							BrutosConstants.DEFAULT_RETURN_NAME : 
								resultAction.getName();
				requestScope.put(var, stackRequestElement.getResultAction());

				if (method.isReturnRendered() || resultAction.getType().isAlwaysRender()) {
					this.showView(
							requestInstrument, 
							stackRequestElement,
							resultAction.getType());
					return;
				}
			}

			if (method.getView() != null) {
				this.showView(requestInstrument, method.getView(),
						method.getDispatcherType());
				return;
			}
		}

		if (stackRequestElement.getController().getView() != null) {
			this.showView(
					requestInstrument, 
					stackRequestElement
					.getController().getView(), 
					stackRequestElement.getController().getDispatcherType()
			);
		}
		else
		if(method != null && method.getResultAction().getType() != null){
			this.showView(
					requestInstrument, 
					stackRequestElement,
					method.getResultAction().getType());
		}

	}

}
