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

package org.brandao.brutos.util.captcha;

import javax.swing.ImageIcon;

/**
 *
 * @author Afonso Brandão
 */
public class Imagem {
    
    public Imagem() {

    }
    
    public java.awt.image.BufferedImage getImage( String texto ){
        
        String[] fonts = new String[]{ "Arial", "Tahoma", "Arial Black" };
        java.awt.Color cores[] = new java.awt.Color[]{
                                                        new java.awt.Color(177, 242, 255 ),
                                                        new java.awt.Color(115, 203, 193 ),
                                                        new java.awt.Color( 35, 250, 175 )};
        /*
                                                        new java.awt.Color(107, 172, 187 ),
                                                        new java.awt.Color(95, 183, 173 ),
                                                        new java.awt.Color( 15, 236, 155 )};
         */

        java.awt.image.BufferedImage image = null;
        java.awt.Graphics2D g = null;
        try{

            image = new java.awt.image.BufferedImage( 100, 50, java.awt.image.BufferedImage.TYPE_INT_RGB);
            g = (java.awt.Graphics2D)image.getGraphics();
            ImageIcon background = new ImageIcon( getClass().getResource( "/org/brandao/brutos/util/captcha/background.jpg" ) );
            g.drawImage( background.getImage(), 0, 0, null);
            //g.setColor( new java.awt.Color( 107, 172, 187 ) );
            //g.fillRect( 0, 0, 100, 30 );
            //g.setColor( new java.awt.Color( 0, 0, 0 ) );
            //g.drawRect( 0, 0, 99, 29 );
            //g.setColor( new java.awt.Color( 0, 0, 0 ) );
            //g.drawLine( 10, 10, 100, 10 );
            //g.drawLine( 100, 20, 10, 10 );
            //g.drawLine( 10, 20, 100, 15 );
            //g.drawLine( 10, 10, 100, 10 );
            int posx = 3;
            for(int i=0; i<texto.length();i++){
                g.setColor( cores[ i % cores.length ] );
                g.setFont( new java.awt.Font(  fonts[ i % fonts.length ], 0, 22) );
                g.drawString( texto.charAt( i ) + "", posx, 40 + ( (i*3) % 5 ) );
                posx = posx + g.getFontMetrics().charWidth( texto.charAt( i ) );
            }
        }
        catch( Exception e ){
            return null;
        }
        return image;
    }
}
