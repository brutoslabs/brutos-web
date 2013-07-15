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

import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 * Provê os recursos da aplicação.
 * 
 * @author Brandao
 */
public interface RequestInstrument extends ViewCheck{

    /**
     * Obtém o contexto da aplicação.
     * @return Contexto da aplicação.
     */
    ApplicationContext getContext();

    /**
     * Obtém o provedor do container IoC.
     * @return Provedor do container IoC.
     */
    IOCProvider getIocProvider();

    /**
     * Obtém o provedor de vista.
     * @return Provedor de vista.
     */
    ViewProvider getViewProvider();

}
