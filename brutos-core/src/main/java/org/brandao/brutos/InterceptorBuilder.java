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

import org.brandao.brutos.mapping.Interceptor;

/**
 * Classe usada para construir um interceptador. Com essa classe é possível
 * determinar os parâmetros de configuração do interceptador. Um interceptador
 * deve implementar a interface org.brandao.brutos.interceptor.Interceptor ou
 * AbstractInterceptor.
 *
 * <pre>
 * Ex:
 *
 * public class MyInterceptor extends AbstractInterceptor{
 *
 *     public void intercepted( InterceptorStack stack,
 *        InterceptorHandler handler ) throws InterceptedException{
 *         ...
 *     }
 *
 * }
 * interceptorManager
 *     .addInterceptor( "myInterceptorName", MyInterceptor.class, false );
 * </pre>
 *
 * @author Afonso Brandao
 */
public class InterceptorBuilder {

    private Interceptor interceptor;
    private InterceptorManager manager;

    public InterceptorBuilder( Interceptor interceptor, InterceptorManager manager ) {
        this.interceptor = interceptor;
        this.manager = manager;
    }

    /**
     * Inclui um novo parâmetro.
     * @param name Nome do parâmetro.
     * @param value Valor do Parâmetro
     * @return Construtor do interceptador.
     */
    public InterceptorBuilder addParameter( String name, String value ){
        interceptor.setProperty( name, value );
        return this;
    }
}