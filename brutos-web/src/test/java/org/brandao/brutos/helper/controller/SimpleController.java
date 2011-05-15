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

package org.brandao.brutos.helper.controller;

import java.util.Arrays;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.AbstractWebApplicationContext;

/**
 *
 * @author Brandao
 */
public class SimpleController {

    public void simpleAction(){
    }

    public void actionWithParam(int value){
        assert(value==100);
    }

    public void actionWithParam( int[] value){
        assert(value != null);
        assert(value.length == 3);
        assert(Arrays.equals(value, new int[]{1,6,111}));
    }

    public String actionWithReturn(){
        return "MSG";
    }

    public String actionWithReturnAndParam(String value){
        assert("myvalue".equals(value));
        return "MSG";
    }

    public void actionWithSupportedException(){
        throw new UnsupportedOperationException();
    }

    public void actionWithUnsupportedException(){
        throw new UnsupportedOperationException();
    }

    public void actionWithParamAndView( double value ){
        assert(value==2.33);
    }

    public void actionToOtherAction(){
        AbstractWebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();

        SimpleController otherController =
                (SimpleController) context.getController(SimpleController.class);

        otherController.actionWithParamAndView(2.33);
    }

    public void actionToOtherActionWithReturn(){
        AbstractWebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();

        SimpleController otherController =
                (SimpleController) context.getController(SimpleController.class);

        String result =
                otherController.actionWithReturnAndParam("myvalue");

        assert("MSG".equals(result));
    }

}
