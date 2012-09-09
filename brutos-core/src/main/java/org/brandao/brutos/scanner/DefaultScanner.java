/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.scanner;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import org.brandao.brutos.scanner.vfs.Dir;
import org.brandao.brutos.scanner.vfs.File;
import org.brandao.brutos.scanner.vfs.Vfs;
/**
 *
 * @author Afonso Brandao
 */
public class DefaultScanner extends AbstractScanner{

    public DefaultScanner(){
    }

    public void scan(){
        load(Thread.currentThread().getContextClassLoader());
    }
    
    public void load( ClassLoader classLoader ){
        try{
            URLClassLoader urls = (URLClassLoader)classLoader;
            Enumeration e = urls.getResources(toResource(basePackage));
            
            while(e.hasMoreElements()){
                URL url = (URL) e.nextElement();
                
                Dir dir = Vfs.getDir(url);
                File[] files = dir.getFiles();
                
                for(int i=0;i<files.length;i++){
                    File file = files[i];
                    String path = file.getRelativePath();
                    
                    if( path.endsWith( ".class" ) ){
                        String tmp = 
                            basePackage + 
                            "." + 
                            path.replace( "/" , "." ).substring( 0, path.length()-6 );
                        
                        try{
                            checkClass( Class.forName( tmp, false, classLoader) );
                        }
                        catch( Throwable ex ){}
                    }
                }
            }
        }
        catch( Exception e ){}
    }

    private void checkClass( Class classe ){
        if(accepts(classe))
            listClass.add(classe);
    }
    
   private String toResource(String value){
        value = value
            .replace(".", "/")
            .replace("\\", "/")
            .replaceAll( "/+" , "/");
        
        if (value.startsWith("/")) 
            value = value.substring(1);
        
        return value;
    }
   
}