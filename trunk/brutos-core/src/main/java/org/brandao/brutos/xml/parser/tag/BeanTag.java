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

package org.brandao.brutos.xml.parser.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.xml.parser.Stack;
import org.brandao.brutos.xml.parser.Tag;
import org.xml.sax.Attributes;

/**
 *
 * @author Afonso Brandao
 */
public class BeanTag implements Tag{

    private Stack stack;

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public void setText(String txt) {
    }

    public boolean isRead() {
        return false;
    }

    public void start(String localName, Attributes atts) {
        Map<String,Object> beans = (Map)stack.pop();

        Map<String,Object> bean = new HashMap<String,Object>();
        bean.put( "@tag", localName );
        bean.put( "name", atts.getValue( "name" ) );
        bean.put( "class", atts.getValue( "class" ) );
        bean.put( "scope", atts.getValue( "scope" ) );
        bean.put( "singleton", atts.getValue( "singleton" ) );
        bean.put( "factory-bean", atts.getValue( "factory-bean" ) );
        bean.put( "factory-method", atts.getValue( "factory-method" ) );
        bean.put( "type" , "bean" );

        if( "beans".equals( beans.get( "@tag" ) ) ){
            if( beans.put( atts.getValue( "name" ), bean ) != null )
                throw new MappingException( "conflict bean name: " + atts.getValue( "name" ) );
        }

        stack.push(beans);
        stack.push(bean);
        /*
        Map<String,Object> bean = new HashMap<String,Object>();
        bean.put( "name", atts.getValue( "name" ) );
        bean.put( "class", atts.getValue( "class" ) );
        bean.put( "scope", atts.getValue( "scope" ) );
        bean.put( "singleton", atts.getValue( "singleton" ) );
        bean.put( "factory-bean", atts.getValue( "factory-bean" ) );
        bean.put( "factory-method", atts.getValue( "factory-method" ) );

        if( atts.getValue( "name" ) == null ){
            bean.put( "type", "inner-bean" );
            //inner-bean
            bean.put( "complex-type", "inner-bean" );
            //necessario para manter a compatibilidade
            bean.put( "data", bean );
        }
        else{
            bean.put( "type", "bean" );
            Map<String,Map<String,Object>> beans = (Map)stack.pop();
            if( beans.put( atts.getValue( "name" ), bean ) != null )
                throw new MappingException( "conflict bean name: " + atts.getValue( "name" ) );

            stack.push( beans );
        }

        stack.push( bean );
        */
    }

    public void end(String localName) {
        Map<String,Object> bean = (Map<String,Object>)stack.pop();
        Map<String,Object> parent = (Map<String,Object>)stack.pop();

        stack.push( parent );

        if( !"beans".equals( parent.get( "@tag" ) ) )
            stack.push( bean );
        
        /*
        Map<String,Object> data = (Map<String,Object>)stack.pop();

        //se o objeto removido da pilha nao for um bean, significa
        //que o objeto ja foi removido,por se tratar de um inner-bean.
        if( !"bean".equals( data.get( "type" ) ) )
            stack.push( data );
        */
    }

}
