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

package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author Brandao
 */
public class BrutosRequestWrapper extends HttpServletRequestWrapper implements BrutosRequest{

    private BrutosRequest request;

    public BrutosRequestWrapper( BrutosRequest request ){
        super( (HttpServletRequest)request.getServletRequest() );
        this.request = request;
    }

    public Object getObject(String name) {
        return request.getObject(name);
    }

    public List<Object> getObjects(String name) {
        return request.getObjects(name);
    }

    public UploadListener getUploadListener() {
        return request.getUploadListener();
    }

    public void parseRequest() throws IOException {
        request.parseRequest();
    }

    public ServletRequest getServletRequest() {
        return request.getServletRequest();
    }

    public void setParameter(String name, String value) {
        request.setParameter(name, value);
    }

    public void setParameters(String name, String[] values) {
        request.setParameters(name, values);
    }

    public void setObject(String name, Object value) {
        request.setObject(name, value);
    }

    public void setObjects(String name, Object[] value) {
        request.setObjects(name, value);
    }

    public String getRequestId() {
        return request.getRequestId();
    }

}
