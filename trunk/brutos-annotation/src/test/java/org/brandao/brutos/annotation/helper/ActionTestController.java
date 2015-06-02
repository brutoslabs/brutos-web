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

import java.util.Calendar;
import java.util.Date;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.type.IntegerType;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Brandao
 */
public class ActionTestController {
    
    public void myAction(){
    }

    public void myMethod(){
    }
    
    @Action
    public void ac1(){
    }

    @Action
    public void ac2Action(){
    }

    @Action("/myaction")
    public void my3Action(){
    }

    @Action({"/myaction2","/myaction3"})
    public void my4Action(){
    }

    @ThrowSafe(target=RuntimeException.class)
    public void my5Action(){
    }

    @ThrowSafe(target=RuntimeException.class, dispatcher="redirect", view="/view/exception.jsp")
    public void my6Action(){
    }

    @ThrowSafeList({
        @ThrowSafe(target=RuntimeException.class),
        @ThrowSafe(target=Exception.class, dispatcher="redirect", view="/view/exception.jsp")
    })
    public void my7Action(){
    }

    @ThrowSafe(target=Exception.class, rendered=false)
    public void my8Action() throws Exception{
    }

    @ThrowSafe(target=Exception.class, enabled=false)
    public void my9Action() throws Exception{
    }
    
    public void my10Action() throws RuntimeException{
    }

    public Object my11Action(){
        return null;
    }

    @Result("actionResult")
    public Object my12Action(){
        return null;
    }

    @Transient
    public void my13Action(){
    }

    @View("/controller/view.jsp")
    public void my14Action(){
    }

    @View(value="/controller/view.jsp", dispatcher="redirect")
    public void my15Action(){
    }

    @View(rendered=false)
    public void my16Action(){
    }

    @Action
    @View("/controller/view.jsp")
    @Result("actionResult")
    public void my17(){
    }
    
    public void my18Action(int a){
    }
    
    public void my19Action(Integer a, String b){
    }

    public void my20Action(
            @Identify(bean="param1",scope="request")
            Integer a,
            @Identify(scope="request")
            String b){
    }

    public void my21Action(
            @Enumerated(EnumerationType.STRING)
            EnumTest a,
            EnumTest b){
    }

    public void my22Action (
            @Restrictions(
                rules={
                    @Restriction(rule="required",value="true")
                }
            )
            String a,
            @Restrictions(
                rules={
                @Restriction(rule="required",value="true"),
                @Restriction(rule="min",value="10"),
                @Restriction(rule="max",value="100")
            })
            int b){
    }

    public void my22Action(
            @Restrictions(
                rules={
                    @Restriction(rule="required",value="true")
                }
            )
            String a ) throws ValidatorException{
    }
    
    public void my23Action(
            Date a,
            @Temporal("dd/MM/yyyy")
            Date b,
            Calendar c,
            @Temporal("yyyy-MM-dd")
            Calendar d){
    }

    public void my24Action(
            @Type(IntegerType.class)
            Integer a){
    }

    public void my25Action(
            @Identify(bean="integer",mappingType=MappingTypes.COMPLEX)
            Integer a){
    }

    public void my25Action(
            MyBean a){
    }
    
}
