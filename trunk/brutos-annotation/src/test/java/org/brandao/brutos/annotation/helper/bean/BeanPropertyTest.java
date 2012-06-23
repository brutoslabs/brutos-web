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

import java.util.Date;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.helper.EnumTest;

/**
 *
 * @author Brandao
 */
@Bean
public class BeanPropertyTest {
    
    private int propertyA;

    private String propertyB;
    
    private Date propertyC;
    
    private Date propertyD;
    
    private EnumTest propertyE;

    private EnumTest propertyF;
    
    private EnumTest propertyG;

    public int getPropertyA() {
        return propertyA;
    }

    public void setPropertyA(int propertyA) {
        this.propertyA = propertyA;
    }

    @Identify(bean="prop")
    public String getPropertyB() {
        return propertyB;
    }

    public void setPropertyB(String propertyB) {
        this.propertyB = propertyB;
    }

    public Date getPropertyC() {
        return propertyC;
    }

    public void setPropertyC(Date propertyC) {
        this.propertyC = propertyC;
    }

    @Temporal("yyyy-MM-dd")
    public Date getPropertyD() {
        return propertyD;
    }

    public void setPropertyD(Date propertyD) {
        this.propertyD = propertyD;
    }

    public EnumTest getPropertyE() {
        return propertyE;
    }

    public void setPropertyE(EnumTest propertyE) {
        this.propertyE = propertyE;
    }

    @Enumerated(value=EnumerationType.ORDINAL)
    public EnumTest getPropertyF() {
        return propertyF;
    }

    public void setPropertyF(EnumTest propertyF) {
        this.propertyF = propertyF;
    }

    @Enumerated(value=EnumerationType.STRING)
    public EnumTest getPropertyG() {
        return propertyG;
    }

    public void setPropertyG(EnumTest propertyG) {
        this.propertyG = propertyG;
    }
    
}
