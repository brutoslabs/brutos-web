/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.brandao.controller.bean;

import javax.servlet.ServletContext;

/**
 *
 * @author Neto
 */
public class FilesPathFactory {

    private ServletContext context;

    public FilesPathFactory( ServletContext context ){
        this.context = context;
    }
    public Object createInstance() {
        String path = context.getRealPath( "/" ) + "WEB-INF/files";
        return path;
    }

    public Class getClassType() {
        return String.class;
    }

}
