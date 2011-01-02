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

import org.brandao.brutos.Configuration;
import org.brandao.brutos.validator.RestrictionRules;

/**
 * Constroi uma propriedade de um controlador.
 * 
 * @author Afonso Brandao
 */
public class PropertyBuilder extends RestrictionBuilder{

    public PropertyBuilder( Configuration config){
        super( config );
    }

    public PropertyBuilder addRestriction( RestrictionRules ruleId, Object value ){
        return (PropertyBuilder)super.addRestriction( ruleId, value );
    }

    public PropertyBuilder setMessage( String message ){
        return (PropertyBuilder)super.setMessage(message);
    }


}
