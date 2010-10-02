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

package org.brandao.brutos.type.json;

/**
 *
 * @author Afonso Brandao
 */
interface JSONConstants {

    public static final StringBuffer HEX          = new StringBuffer( "0123456789abcdef" );
    public static final StringBuffer START_OBJECT = new StringBuffer("{ ");
    public static final StringBuffer END_OBJECT   = new StringBuffer(" }");
    public static final StringBuffer SEPARATOR    = new StringBuffer(", ");
    public static final StringBuffer START_ARRAY  = new StringBuffer("[ ");
    public static final StringBuffer END_ARRAY    = new StringBuffer(" ]");
    public static final StringBuffer EQUALS       = new StringBuffer(" : ");
    public static final StringBuffer QUOTE        = new StringBuffer("\"");
    public static final StringBuffer NULL         = new StringBuffer("null");
    //public static final StringBuffer DATE         = new StringBuffer("Date(%d)");
    public static final StringBuffer SUPER        = new StringBuffer("_super");
    public static final StringBuffer TRUE         = new StringBuffer("true");
    public static final StringBuffer FALSE        = new StringBuffer("false");

    public static final char START_OBJECT_STR = '{';
    public static final char END_OBJECT_STR   = '}';
    public static final char SEPARATOR_STR    = ',';
    public static final char START_ARRAY_STR  = '[';
    public static final char END_ARRAY_STR    = ']';
    public static final char EQUALS_STR       = ':';
    public static final char QUOTE_STR        = '\"';
    
}
