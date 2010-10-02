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

package org.brandao.brutos.mapping.ioc;

import java.beans.PropertyEditor;
import org.brandao.brutos.ioc.EditorConfigurer;

/**
 *
 * @author Afonso Brandao
 */
public class ValueInject extends Injectable{
    
    private Object value;
    private boolean converted;
    
    public ValueInject() {
        this.converted = false;
    }

    public ValueInject( Class<?> target, Object value ) {
        super( target, null, null, false, null );
        this.value     = value;
        this.converted = false;
    }
    
    public Object getValue() {
        return converted? value : converter(value);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private Object converter( Object value ){
        
        this.converted = true;

        if( value instanceof String && getTarget() != String.class ){
            PropertyEditor editor = EditorConfigurer.getPropertyEditor(getTarget().getName());
            if( editor != null ){
                synchronized( editor ){
                    editor.setAsText( (String)value );
                    this.value = editor.getValue();
                    return this.value;
                }
            }
            else
                return value;
        }
        else
            return value;
    }

    public String toString(){
        return String.valueOf( value );
    }
}
