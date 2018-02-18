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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;

/**
 * Descreve as estratégias de mapeamento de ação. Esta classe é usada em conjunto com
 * a classe {@link ActionTypeResolver}.
 * 
 * @author Brandao
 */
public class ActionType {

	private final static Map<String, ActionType> defaultTypes = 
			new HashMap<String, ActionType>();

	public static final ActionType PARAMETER = new ActionType(){
		
		public String id(){
			return "PARAMETER";
		}

		public String name(){
			return "Parameter";
		}
		
		public String getControllerID(String className){
			return className;
		}

		public String getActionID(String actionName){
			return actionName;
		}
		
		/*
		public boolean isComposite(){
			return true;
		}

		public boolean isDelegate(){
			return false;
		}
		*/
		
		public boolean isValidControllerId(String value){
			return value != null;
		}

		public boolean isValidActionId(String value){
			return value != null;
		}
		
		public List<ActionID> getIDs(ControllerID controllerID, Controller controller, 
				ActionID actionID, Action action){
			//ações não possuem ids
			if(action != null){
				return null;
			}
			
			return Arrays.asList(new ActionID(controllerID.getName()));
		}
		
	};
	
	public static ActionType valueOf(String value) {
		if (value == null)
			return null;
		else
			return (ActionType) defaultTypes.get(value.toUpperCase());
	}

	/**
	 * Obtém a identificação do tipo de mapeamento de ação. 
	 * Ela é única em toda aplicação.
	 * @return Identificação.
	 */
	public String id(){
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtém o nome do tipo de mapeamento de ação. 
	 * @return Nome.
	 */
	public String name(){
		throw new UnsupportedOperationException();
	}
	
	/*
	 * Verifica se a identificação é composta pelo controlador e a ação.
	 * @return Verdadeiro se a identificação é composta. Caso contrário, falso.
	 */
	//public boolean isComposite(){
	//	throw new UnsupportedOperationException();
	//}

	/*
	 * Verifica se a resolução da ação é delegada ao controlador.
	 * @return Verdadeiro se a resolução da ação é delagada ao controladora. Caso contrário, falso.
	 */
	//public boolean isDelegate(){
	//	throw new UnsupportedOperationException();
	//}
	
	/**
	 * Obtém a identificação do controlador em um formato válido.
	 * @param classType Nome da classe do controlador.
	 */
	public String getControllerID(String className){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Verifica se a identificação do controlador é válida.
	 * @param value Identificação.
	 * @return Verdadeiro se for válida. Caso contrário, falso.
	 */
	public boolean isValidControllerId(String value){
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtém a identificação da ação em um formato válido.
	 * @param actionName Nome do método da ação.
	 */
	public String getActionID(String actionName){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Verifica se a identificação da ação é válida.
	 * @param value Identificação.
	 * @return Verdadeiro se for válida. Caso contrário, falso.
	 */
	public boolean isValidActionId(String value){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Obtém as identificações públicas das ações.
	 * @param controllerID Identificação do controlador. 
	 * @param controller Controlador.
	 * @param actionID Identificação da ação.
	 * @param action Ação.
	 * @return Identificações públicas.
	 */
	public List<ActionID> getIDs(ControllerID controllerID, Controller controller, 
			ActionID actionID, Action action){
		throw new UnsupportedOperationException();
	}
	
	public String toString(){
		return this.id();
	}
}
