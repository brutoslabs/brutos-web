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

package org.brandao.brutos.xml.parser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 *
 * @author Afonso Brandao
 */
public class BrutosXMLError implements ErrorHandler{
    
    private StringBuffer error;
    
    public BrutosXMLError(){
        this.error = null;
    }
    
    public void warning(SAXParseException exception) throws SAXException {
        addError( exception, "Warning" );
    }

    public void error(SAXParseException exception) throws SAXException {
        addError( exception, "Error" );
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        addError( exception, "Fatal error" );
    }

    private void addError( SAXParseException e, String type ){
        if( this.error == null )
            this.error = new StringBuffer();
        
        this.error.append(type + ": " ).append( "\n" );
        this.error.append("   Line number: "  + e.getLineNumber()  ).append( "\n" );
        this.error.append("   Column number: "+ e.getColumnNumber()).append( "\n" );
        this.error.append("   Message: "      + e.getMessage()     ).append( "\n" );
        this.error.append( "\n" );
    }
    
    public SAXParseException getException(){
        return error == null? null : new SAXParseException( error.toString(), null );
    }
}
