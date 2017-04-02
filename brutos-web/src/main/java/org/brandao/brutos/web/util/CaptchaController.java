/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import org.brandao.brutos.web.http.Download;
import org.brandao.brutos.util.captcha.Imagem;

/**
 * 
 * @author Brandao
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

            public String getContentType() {
                return "image/jpeg";
            }

            public Map<String, String> getHeader() {
                return null;
            }

            public long getContentLength() {
                return -1;
            }

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
