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

package org.brandao.brutos.annotation.helper.action.app1;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Actions;
import org.brandao.brutos.annotation.DispatcherType;
import org.brandao.brutos.annotation.View;

/**
 *
 * @author Brandao
 */
@Actions({
    @Action(value ="/test04",view = @View("test04")),
    @Action(value ="/test05",view = @View(value = "/test05.jsp", resolved = true)),
    @Action(
        value =
                "/test06",
        view = 
                @View(
                        value = "/test06.jsp", 
                        resolved = true, 
                        dispatcher = DispatcherType.REDIRECT))
})
@Action(value ="/test03",view = @View("test03"))
public class App1TestController {
    
    public String test1Action(){
        return "result";
    }

    @Action
    public String test2(){
        return "result2";
    }

    @Action("/test00")
    public String test3(){
        return "result3";
    }

    @Action({"/test01","/test02"})
    public String test4(){
        return "result4";
    }
    
}
