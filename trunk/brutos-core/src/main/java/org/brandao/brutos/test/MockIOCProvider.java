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

package org.brandao.brutos.test;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ioc.*;

/**
 *
 * @author Afonso Brandao
 */
public class MockIOCProvider extends IOCProvider{

    public Object getBean(String name) {
        return null;
    }

    @Override
    public Object getBean(Class clazz) {
        try{
            return clazz.newInstance();
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    public void configure(Properties properties) {
    }

    public void destroy() {
    }

}
