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

package org.brandao.brutos.annotation.helper.bean;

import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.helper.MyBean;

/**
 *
 * @author Brandao
 */
@Bean
public class BeanTestFactory {
    
    @Bean("bean1")
    public MyBean createMyBean(){
        return new MyBean(0,0);
    }

    @Bean("bean2")
    public MyBean createMyBean2(){
        return new MyBean(2,2);
    }

    @Bean("bean3")
    public MyBean createMyBean3(
    		@Basic(bean="value1")
            int v1, 
            @Basic(bean="value2")
            int v2){
        return new MyBean(v1,v2);
    }
    
}
