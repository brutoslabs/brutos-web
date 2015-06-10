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

package org.brandao.brutos.annotation.helper;

import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.Basic;


/**
 *
 * @author Brandao
 */
public class MyBean {
    
	@Basic(bean="bean1", scope="request")
    private int prop1;
    
    private int prop2;
    
    @Constructor
    public MyBean(
    		@Basic(bean="idade")
            int value,
            @Basic(bean="idade2")
            int value2){
        this.prop1 = value;
    }
    
    public void setProp1(int value){
        this.prop1 = value;
    }
    
    @Basic(bean="bean1", scope="session")
    public int getProp1(){
        return prop1;
    }
    
}
