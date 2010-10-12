/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.type.json.jsondecoder;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderDateTest extends TestCase implements Test{

    public JSONDecoderDateTest(){
        super();
    }

    public void testDate() throws IOException{
        Date date = new Date();
        JSONDecoder jse = new JSONDecoder( String.valueOf( date.getTime() ) );
        Date result = jse.decode( Date.class );
        assertEquals( date.getTime(), result.getTime() );
    }

    public void testTime() throws IOException{
        Time time = new Time( (new Date()).getTime() );
        JSONDecoder jse = new JSONDecoder( String.valueOf( time.getTime() ) );
        Time result = jse.decode( Time.class );
        assertEquals( time.getTime(), result.getTime() );
    }

    public void testTimestamp() throws IOException{
        Timestamp time = new Timestamp( (new Date()).getTime() );
        JSONDecoder jse = new JSONDecoder( String.valueOf( time.getTime() ) );
        Timestamp result = jse.decode( Timestamp.class );
        assertEquals( time.getTime(), result.getTime() );
    }

    public void testCalendar() throws IOException{
        Calendar time = new GregorianCalendar();
        JSONDecoder jse = new JSONDecoder( String.valueOf( time.getTime().getTime() ) );
        Calendar result = jse.decode( Calendar.class );
        assertEquals( time.getTime(), result.getTime() );
    }

}
