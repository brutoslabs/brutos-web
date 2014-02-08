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

import java.util.Properties;

/**
 * Define qual visão será utilizada pelo controlador, ação ou exceção. 
 * Uma visão é automaticamente definada quando ela não é infomrada no momento 
 * da construção do controlador, ação ou exceção.
 * 
 * <p>O método {@link #getView(org.brandao.brutos.ControllerBuilder, 
 * org.brandao.brutos.ActionBuilder, java.lang.Class, java.util.Properties) 
 * getView()} a partir dos parâmetros informados, determina qual visão será 
 * usada. Ele recebe como parâmetro, o construtor do controlador, da ação, 
 * a classe que representa a exceção e a configuração da aplicação. Em todas 
 * as etapas, a configuração da aplicação é necessária. Na resolução da visão 
 * do controlador, além da configuração, é usado o seu construtor. Para a 
 * resolução da visão da ação além da configuração da aplicação, são necessários
 * o construtor do controlador e o construtor da ação. Já na resolução da visão 
 * da exceção, são necessários todos os parâmetros.</p>
 * 
 * <p>A configuração da auto-resolução tem que ser feita no inicio da aplicação 
 * com o uso propriedade org.brandao.brutos.view.resolver. A classe informada 
 * nessa propriedade tem que implementar a interface 
 * {@link org.brandao.brutos.ViewResolver ViewResolver}. 
 * Caso não seja informada, será utilizada a implementação padrão 
 * {@link org.brandao.brutos.DefaultViewResolver DefaultViewResolver}.</p>
 * 
 * @author Brandao
 */
public interface ViewResolver {
    
    /**
     * Obtém uma visão.
     * @param controllerBuilder Construtor do controlador.
     * @param actionBuilder Construtor da ação.
     * @param exceptionType Classe que representa a exceção.
     * @param view Vista.
     * @return Visão.
     */
    String getView(ControllerBuilder controllerBuilder, ActionBuilder actionBuilder, 
            Class exceptionType, String view);

    void setApplicationContext(ApplicationContext context);
    
    String getPrefix();
    
    String getSuffix();
    
    String getIndexName();
    
    String getSeparator();
    
    String getControllerView(Class controllerType, String view);
    
    String getActionView(Class controllerType, String actionExecutor, String view);
    
    String getExceptionView(Class controllerType, String actionExecutor, Class exceptionType, String view);
    
    String getExceptionView(Class controllerType, Class exceptionType, String view);
    
}
