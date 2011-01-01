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

package org.brandao.brutos;

import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultActionResolver implements ActionResolver{
    
    private ResourceAction getResourceMethod( MethodForm methodForm ){
        return new DefaultResourceAction( methodForm );
    }

    public ResourceAction getResourceAction(Form controller, Scope scope) {
        MethodForm method = controller
                .getMethodByName( String.valueOf( scope.get( controller.getMethodId() ) ) );
        return method == null? null : getResourceMethod( method );
    }

}
