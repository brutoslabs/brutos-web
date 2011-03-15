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


package org.brandao.brutos.type.json.jsonencoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONEncoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderDateTest extends TestCase implements Test{

    public JSONEncoderDateTest(){
        super();
    }

    public void testDate() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        Date date = new Date();
        jse.writeObject( date );
        jse.close();
        assertEquals( String.valueOf( date.getTime() ), out.toString() );
    }

    public void testTime() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        Time time = new Time( (new Date()).getTime() );
        jse.writeObject( time );
        jse.close();
        assertEquals( String.valueOf( time.getTime() ), out.toString() );
    }

    public void testTimestamp() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        Timestamp time = new Timestamp( (new Date()).getTime() );
        jse.writeObject( time );
        jse.close();
        assertEquals( String.valueOf( time.getTime() ), out.toString() );
    }

    public void testCalendar() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        Calendar cal = new GregorianCalendar();
        jse.writeObject( cal );
        jse.close();
        assertEquals( String.valueOf( cal.getTime().getTime() ), out.toString() );
    }

}
