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


package org.brandao.brutos.view;

import java.awt.Component;
import org.brandao.brutos.*;
import java.io.IOException;
import java.util.Properties;
import org.brandao.brutos.ioc.IOCProvider;

/**
 *
 * @author Brandao
 */
public class DefaultViewProvider extends ViewProvider{

    public void configure(Properties properties) {
    }

    protected void show(RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType) throws IOException {

        IOCProvider iocProvider = requestInstrument.getIocProvider();

        Object objectView = iocProvider.getBean(view);

        if( objectView instanceof Component )
            ((Component)objectView).setVisible(true);
        
    }

}
