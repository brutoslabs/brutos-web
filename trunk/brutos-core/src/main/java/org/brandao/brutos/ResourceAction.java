/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.brandao.brutos.mapping.Action;

/**
 * Representa uma açao.
 *
 * @author Afonso Brandao
 */
public interface ResourceAction {

    /**
     * Invoca o m�todo associado a a��o.
     * @param source Objeto a ter o m�todo invocado.
     * @param args Argumentos necess�rios para invocar o m�todo.
     * @return Resultado obtido da invoca��o do m�todo.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object invoke( Object source, Object[] args )
            throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException;

    /**
     * Obt�m o mapeamento da a��o.
     * @return M�todo.
     */
    public Action getMethodForm();

    /**
     * Obt�m o m�todo associado a a��o.
     * @return M�todo.
     */
    public Method getMethod();

    /**
     * Obt�m o tipo de retorno da a��o.
     * @return Tipo.
     */
    public Class returnType();

    /**
     * Obt�m os argumentos necess�rios para invocar o m�todo.
     * @return Matriz de tipos.
     */
    public Class[] getParametersType();

    /**
     * Obt�m a classe propriet�ria do m�todo associado a a��o.
     * @return Classe.
     */
    public Class getResourceClass();

    /**
     * Verifica se � uma a��o abstrata.
     * @return Verdadeiro se existe um m�todo associado a a��o, caso
     * contr�rio falso.
     */
    public boolean isAbstract();
}
