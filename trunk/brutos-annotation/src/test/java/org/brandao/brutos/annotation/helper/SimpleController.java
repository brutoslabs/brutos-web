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
    @Action("action1")
    public void myAction(){
    }
}
