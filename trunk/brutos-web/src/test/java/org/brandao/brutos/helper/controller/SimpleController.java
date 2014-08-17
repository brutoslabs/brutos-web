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

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.brandao.brutos.FlowController;
import org.brandao.brutos.ResultAction;

/**
 *
 * @author Brandao
 */
public class SimpleController {

    private String property1;

    private Integer property2;

    public void simpleAction(){
    }

    public void testProperty1(){
        TestCase.assertEquals("teste",this.property1);
    }

    public void testProperty2(){
        TestCase.assertEquals(new Integer(101),this.property2);
    }

    public String defaultAction(){
        return "OK";
    }

    public void actionWithParam(int value){
        TestCase.assertEquals(100,value);
    }

    public void actionWithParam( int[] value){
        TestCase.assertNotNull(value);
        TestCase.assertEquals(3,value.length);
        TestCase.assertTrue(Arrays.equals(value, new int[]{1,6,111}));
    }

    public String actionWithReturn(){
        return "MSG";
    }

    public String actionWithReturnAndParam(String value){
        TestCase.assertEquals("myvalue",value);
        return "MSG";
    }

    public void actionWithParams(String value, double value2){
        TestCase.assertEquals("myvalue",value);
        TestCase.assertEquals(20.3,value2);
    }

    public void actionWithSupportedException(){
        throw new UnsupportedOperationException();
    }

    public void actionToOtherAction(){
        SimpleController otherController =
                (SimpleController) FlowController
                    .getController(SimpleController.class);

        otherController.actionWithParam(100);
    }

    public void actionToOtherAction2(){
        FlowController
        .execute( SimpleController.class, "test2Action" );
    }

    public void actionToOtherActionWithReturn2(){
        String result =
                (String) FlowController
                .execute(SimpleController.class,"test2Action");

        TestCase.assertEquals( "MSG", result );
    }

    public void actionToOtherActionWithReturn(){
        SimpleController otherController =
                (SimpleController) FlowController
                    .getController(SimpleController.class);

        String result =
                otherController.actionWithReturnAndParam("myvalue");

        TestCase.assertEquals("MSG",result);
    }

    public void actionWithCollectionParam( List list ){
        throw new UnsupportedOperationException();
    }

    public ResultAction actionWithResultActionType(ResultAction result){
        result
            .addInfo("Content-Type", "Content-Type: text/html; charset=utf-8")
            .setContentType(String.class)
            .setContent("<html><body>test</body></html>");
        return result;
    }

    public ResultAction actionWithResultActionUseDefaultView(ResultAction result){
        result
            .addInfo("Content-Type", "Content-Type: text/html; charset=utf-8")
            .add("value1", BigDecimal.ONE)
            .add("value2", BigDecimal.TEN);
        return result;
    }

    public ResultAction actionWithResultActionUseUnresolvedView(ResultAction result){
        result
            .addInfo("Content-Type", "Content-Type: text/html; charset=utf-8")
            .setView("/jsp/myView.jsp", false)
            .add("value1", BigDecimal.ONE);
        return result;
    }

    public ResultAction actionWithResultActionUseView(ResultAction result){
        result
            .addInfo("Content-Type", "Content-Type: text/html; charset=utf-8")
            .setView("myView")
            .add("value1", BigDecimal.ONE);
        return result;
    }
    
    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public Integer getProperty2() {
        return property2;
    }

    public void setProperty2(Integer property2) {
        this.property2 = property2;
    }

}
