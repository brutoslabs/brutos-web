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

/**
 *
 * @author Brandao
 */
public class SimpleBean {

    private String arg;
    private int arg2;
    private SimpleBean bean;

    public SimpleBean(){
    }

    public SimpleBean(String arg,int arg2,SimpleBean bean){
        this.arg = arg;
        this.arg2 = arg2;
        this.bean = bean;
    }

    public SimpleBean(String arg,int arg2){
        this(arg,arg2,null);
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public SimpleBean getBean() {
        return bean;
    }

    public void setBean(SimpleBean bean) {
        this.bean = bean;
    }
}
