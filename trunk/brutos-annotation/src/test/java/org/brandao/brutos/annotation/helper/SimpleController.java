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

import org.brandao.brutos.annotation.*;

/**
 *
 * @author Brandao
 */
@Controller(id="/simple/{invoke}")
@ControllerAlias({"/index/{new}","/index2/{new}"})
@InterceptedBy({
    @Intercept(
        interceptor=SimpleInterceptor.class),
    @Intercept(
        interceptor=SimpleInterceptor.class,
        params=
            @Param(name="nameParam",value="valueParam"))})
public class SimpleController {

    /**
     * /simple/action1/
     */
    @Action
    public void newUserAction(){
    }

    @Action("/index/{id}")
    public void mySimpleAction(
            @ActionParam(bean="id")
            Integer id ){
    }
}
