/*
  Brutos Web MVC http://brutos.sourceforge.net/
  Copyright (C) 2009 Afonso Brand√£o. (afonso.rbn@gmail.com)
*/

package br.brandao.controller;

/**
 *
 * @author Afonso Brand„o
 */
public class IndexController {

    private String text;
    private String title;
    
    public IndexController(){
    }

    public void method1(){
        this.text = "Method 1";
    }

    public void method2(){
        this.text = "Method 2";
    }

    public void method3(){
        this.text = "Method 3";
    }

    public void method4(){
        this.text = "Method 4";
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
}
