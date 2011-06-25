/*
  Brutos Web MVC http://brutos.sourceforge.net/
  Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
*/

package br.brandao.controller;

import br.brandao.beans.MyBean;

/**
 *
 * @author Afonso Brandão
 */
public class IndexController {

    private String text;
    private String title;
    private MyBean bean;

    public IndexController(){
    }

    public void show( MyBean bean ){
        this.bean = bean;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MyBean getBean() {
        return bean;
    }
}
