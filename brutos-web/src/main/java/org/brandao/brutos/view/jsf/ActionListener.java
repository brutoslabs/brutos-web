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

package org.brandao.brutos.view.jsf;

import com.sun.faces.application.ActionListenerImpl;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.mapping.Form;

/**
 *
 * @author Afonso Brandao
 */
public class ActionListener extends ActionListenerImpl{
    
    
    public ActionListener() {
    }

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        WebApplicationContext brutosContext = (WebApplicationContext) ApplicationContext.getCurrentApplicationContext();
        Form controller = brutosContext.getController();
        
        if( controller != null ){
                brutosContext
                    .getRequest()
                        .setAttribute(
                            BrutosConstants.JSF_ACTION_LISTENER, event );
        }
        else
            super.processAction( event );
    }
    
}
