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

package org.brandao.brutos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.brandao.brutos.mapping.MethodForm;

/**
 * Representa uma ação.
 *
 * @author Afonso Brandao
 */
public interface ResourceAction {

    /**
     * Invoca o método associado a ação.
     * @param source Objeto a ter o método invocado.
     * @param args Argumentos necessários para invocar o método.
     * @return Resultado obtido da invocação do método.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object invoke( Object source, Object[] args )
            throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException;

    /**
     * Obtém o mapeamento da ação.
     * @return Método.
     */
    public MethodForm getMethodForm();

    /**
     * Obtém o método associado a ação.
     * @return Método.
     */
    public Method getMethod();

    /**
     * Obtém o tipo de retorno da ação.
     * @return Tipo.
     */
    public Class returnType();

    /**
     * Obtém os argumentos necessários para invocar o método.
     * @return Matriz de tipos.
     */
    public Class[] getParametersType();

    /**
     * Obtém a classe proprietária do método associado a ação.
     * @return Classe.
     */
    public Class getResourceClass();

    /**
     * Verifica se é uma ação abstrata.
     * @return Verdadeiro se existe um método associado a ação, caso
     * contrário falso.
     */
    public boolean isAbstract();
}
