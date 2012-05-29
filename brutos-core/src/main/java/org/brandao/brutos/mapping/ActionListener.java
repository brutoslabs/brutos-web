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

package org.brandao.brutos.mapping;


import java.lang.reflect.Method;

/**
 *
 * @author Afonso Brandao
 */
public class ActionListener {
    
    private Class classType;
    
    private Method preAction;
    
    private Method postAction;
    
    public ActionListener() {
    }

    public Method getPreAction() {
        return preAction;
    }

    public void setPreAction(Method preAction) {
        this.preAction = preAction;
    }

    public Method getPostAction() {
        return postAction;
    }

    public void setPostAction(Method postAction) {
        this.postAction = postAction;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
}
