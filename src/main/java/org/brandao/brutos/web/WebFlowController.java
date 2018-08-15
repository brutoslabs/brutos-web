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

package org.brandao.brutos.web;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.FlowController;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.RedirectException;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.scope.Scope;

/**
 * Permite alterar o fluxo de execução em uma aplicação web.
 * <p>Esta classe é uma especialização da classe {@link FlowController}. Além
 * dos recursos oferecidos por este, são oferecidos recursos de redirecionamento.</p>
 * O redirecionamento pode ser feito de duas formas. A primeira é executando o método 
 * {@link #redirectTo(String)}.
 * 
 * Ex:
 * <pre>
 * WebFlowController.redirectTo("/users");
 * </pre>
 * 
 * <p>A segunda é executando o método {@link #redirect()}. Este permite
 * disponibilizar valores que podem ser acessados na página que será redirecionada.</p>
 * 
 * Ex:
 * <pre>
 * WebFlowController
 * .redirect()
 *   .put("msg", "User added successfully!")
 * .to("/users/" + user.getId());
 * </pre>
 * 
 * Ao executar o método {@link #redirectTo(String)} e {@link RedirectBuilder#to(String)}, 
 * será lançada a exceção {@link RedirectException}. Não é um erro, isso indica ao framework 
 * que o fluxo tem que ser redirecionado.
 * 
 * @author Brandao
 *
 */
public class WebFlowController extends FlowController{

	/**
	 * Redireciona a execução para um determinado URI permitindo
	 * disponibilizar valores que podem ser acessados no URI que 
	 * será redirecionado.
	 * @param value URI que será redirecionado. 
	 */
	public static RedirectBuilder redirect(){
		return new RedirectBuilder();
	}

	/**
	 * Executa uma ação de um determinado controlador.
	 * @param clazz Classe do controlador.
	 * @param requestMethodType Tipo da requisição.
	 * @param actionName Ação.
	 * @return Resultado da execução da ação.
	 */
	public static Object execute(Class<?> clazz, String requestMethodType, String actionName) {
		return ((WebInvoker)Invoker.getInstance()).invoke(
					clazz, 
					RequestMethodType.valueOf(requestMethodType), 
					actionName
				);
	}
	
	/**
	 * Redireciona a execução para uma determinada URI.
	 * @param value URI que será redirecionado. 
	 */
	public static void redirectTo(String value){
		throw new RedirectException(value, WebDispatcherType.REDIRECT);
	}
	
	public static class RedirectBuilder {
		
		private Map<String,Object> vars;
		
		public RedirectBuilder(){
			this.vars = new HashMap<String, Object>();
		}
		
		public void to(String address){
			ApplicationContext context 	= Invoker.getCurrentApplicationContext();
			Scopes scopes 				= context.getScopes();
			Scope scope 				= scopes.get(WebScopeType.FLASH);
			scope.put(BrutosWebConstants.REDIRECT_PARAMS, this.vars);
			throw new RedirectException(address, WebDispatcherType.REDIRECT);
		}
		
		public RedirectBuilder put(String name, Object value){
			this.vars.put(name, value);
			return this;
		}
		
	}
	
}
