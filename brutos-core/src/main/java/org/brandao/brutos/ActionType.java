/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.util.HashMap;
import java.util.Map;

/**
 * Descreve as estratégias de mapeamento da ação.
 * 
 * @author Afonso Brandao
 */

public abstract class ActionType {
   
    private final static Map defaultTypes = new HashMap();
    
    /**
     * A ação é mapeada como um parâmetro.
     */
   public static final ActionType PARAMETER  = new ActionType(){

        public int type() {
            return 0;
        }
       
        public String name(){
            return "PARAMETER";
        }
   };

   /**
    * A ação é mapeada em um nível inferior ao do controlador.
    */
   public static final ActionType HIERARCHY = new ActionType(){

        public int type() {
            return 1;
        }
       
        public String name(){
            return "HIERARCHY";
        }
        
   };

   /**
    * A ação é mapeada no mesmo nível do controlador.
    */
   public static final ActionType DETACHED   = new ActionType(){

        public int type() {
            return 2;
        }
       
        public String name(){
            return "DETACHED";
        }
        
   };
   
   static{
       defaultTypes.put(PARAMETER.name() , PARAMETER);
       defaultTypes.put(HIERARCHY.name(), HIERARCHY);
       defaultTypes.put(DETACHED.name()  , DETACHED);
   }
   
    public static ActionType valueOf( String value ){
        if(value == null)
            return null;
        else
            return (ActionType)defaultTypes.get(value.toUpperCase());
    }
   
   public abstract int type();
   
   public abstract String name();

   public int hashCode(){
       return type();
   }
   
   public boolean equals(Object x){
       return x instanceof ActionType? ((ActionType)x).type() == type() : false;
   }
   
}
