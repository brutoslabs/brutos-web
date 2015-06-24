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

import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.MappingException;

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
    	
    	if(name == null || !name.matches("([a-zA-Z0-9_]+)(\\.[a-zA-Z0-9_]+)+"))
    		throw new MappingException("invalid parameter name: " + name);
    	
        interceptor.setProperty( name, value );
        return this;
    }
}