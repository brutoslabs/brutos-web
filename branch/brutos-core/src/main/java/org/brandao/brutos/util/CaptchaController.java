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

package org.brandao.brutos.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import org.brandao.brutos.http.Download;
import org.brandao.brutos.util.captcha.Imagem;

/**
 *
 * @author Afonso Brandao
 */
public class CaptchaController {

    private char[] chars;
    private int maxChars;

    private String value;
    private static Random random = new Random();
    
    public CaptchaController(){
        this.chars =
        "ABCDEFGHIJKLMNOPQRSTUVXZYW0123456789"
                .toCharArray();
        this.maxChars = 4;
    }

    public void setChars( char[] value ){
        this.chars = value;
    }

    public void setMaxChars( int value ){
        this.maxChars = value;
    }

    public Download image() throws IOException{
        value = "";
        for( int i=0;i<maxChars;i++ ){
            value += this.chars[ random.nextInt( this.chars.length ) ];
        }

        Download download = new Download() {

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public Map<String, String> getHeader() {
                return null;
            }

            @Override
            public long getContentLength() {
                return -1;
            }

            @Override
            public void write(OutputStream out) throws IOException {
                Imagem i = new Imagem();
                BufferedImage bi = i.getImage( value );
                ImageIO.write( bi, "JPEG", out );
            }
        };

        return download;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
