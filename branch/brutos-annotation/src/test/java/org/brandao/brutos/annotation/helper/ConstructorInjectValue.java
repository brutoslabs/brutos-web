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


package org.brandao.brutos.annotation.helper;

import org.brandao.brutos.annotation.ioc.ConstructorInject;
import org.brandao.brutos.annotation.ioc.Inject;
import org.brandao.brutos.annotation.ioc.Injectable;

/**
 *
 * @author Afonso Brandao
 */
@Injectable
public class ConstructorInjectValue {

    private int number;

    @ConstructorInject( @Inject( value="300" ) )
    public ConstructorInjectValue( int number ){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
