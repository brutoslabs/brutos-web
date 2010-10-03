/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 *
 * @author Afonso Brandao
 */
public class Handler implements ContentHandler, XMLBrutosConstants{

    private boolean readData;
    
    private StringBuffer string;
    
    private Stack pilha;

    private Map<String,Tag> cacheTag;

    public Handler() {
        this.readData = false;
        this.pilha    = new Stack();
        this.cacheTag = new HashMap<String,Tag>();
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    private Tag getTag( String tagName ){
        
        if( cacheTag.containsKey(tagName) )
            return cacheTag.get( tagName );
        else{
            Tag tag = Tags.getTag(tagName);
            cacheTag.put( tagName, tag );
            return tag;
        }
        
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        Tag tag = getTag( localName );

        tag.setStack( pilha );

        if( tag.isRead() ){
            this.readData = true;
            this.string = new StringBuffer();
        }
        else
            this.readData = false;

        tag.start(localName, atts);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Tag tag = getTag( localName );

        if( tag.isRead() ){
            this.readData = false;
            tag.setText( this.string.toString() );
        }
        
        tag.end(localName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if( readData ){
            string.append( ch, start, length );
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

    public Map<String,Object> getData(){
        return (Map<String, Object>) pilha.pop();
    }
}
