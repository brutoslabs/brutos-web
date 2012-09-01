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


package org.brandao.brutos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.brandao.brutos.mapping.MappingException;
/**
 * Usado para encontrar classes que atendam um determinado critério.
 *
 * @author Afonso Brandao
 */
public class Scanner {

    private List listClass;
    private ScannerFilter filter;

    public Scanner(){
        this.listClass = new ArrayList();
    }

    public void load( ClassLoader classLoader ){
        try{
            URLClassLoader urls = (URLClassLoader)classLoader;
            URL[] aUrls = urls.getURLs();
            //for( URL url: urls.getURLs() )
            for( int i=0;i<aUrls.length;i++ ){
                URL url = aUrls[i];
                readClassPath( url, classLoader );
            }
        }
        catch( Exception e ){}
    }

    public void loadDirs( ClassLoader classLoader ){
        try{
            URLClassLoader urls = (URLClassLoader)classLoader;
            //for( URL url: urls.getURLs() )
            URL[] aUrls = urls.getURLs();
            for( int i=0;i<aUrls.length;i++ ){
                URL url = aUrls[i];
                readClassDir( url, classLoader );
            }
        }
        catch( Exception e ){}
    }

    public void manifest(){
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Enumeration urls = classLoader.getResources( "META-INF/MANIFEST.MF" );

            while( urls.hasMoreElements() ){
                URL url = (URL) urls.nextElement();
                InputStream in = url.openConnection().getInputStream();
                manifest(in, classLoader);
            }
        } catch (Exception ex) {
            throw new MappingException( ex );
        }
    }

    public void manifest(InputStream in, ClassLoader classLoader){
        try{
            java.io.BufferedReader reader = new java.io.BufferedReader( new java.io.InputStreamReader( in ) );
            String txt = "";
            String line = "";

            while( (line = reader.readLine() ) != null ){
                if( line.startsWith( "Class-Path: " ) ){
                    txt = line.substring( "Class-Path: ".length(), line.length() );
                    while( (line = reader.readLine() ) != null && line.startsWith( " " ) ){
                        txt += line.substring( 1, line.length() );
                    }
                }
            }

            StringTokenizer stok = new StringTokenizer( txt, " ", false );
            while( stok.hasMoreTokens() ){
                String dirName  = System.getProperty( "user.dir" );
                String fileName = stok.nextToken();

                if( fileName.startsWith("file:/") ){
                    URL u = new URL (fileName);
                    fileName = URLDecoder.decode(u.getFile(), "UTF-8");
                    File f = new File(fileName);
                    if( f.isFile() )
                        readJar(f, classLoader);
                    else
                    if( f.isDirectory() )
                        readClassDir(u, classLoader);
                }
                else
                if (".".equals(fileName)) 
                    readClassDir(new URL(String.format("file:/%s", new Object[]{dirName})), classLoader);
                else{
                    fileName = dirName + "/" + fileName;
                    File file = new File(fileName);
                    if (file.exists() && file.isFile())
                        readJar(file, classLoader);
                    else
                    if (file.isDirectory())
                        readClassDir(new URL("file:/"+fileName), classLoader);
                }
            }
        }
        catch( Throwable e ){
            throw new MappingException( e );
        }

    }

    private void readClassDir( URL url, ClassLoader classLoader ) throws UnsupportedEncodingException, IOException, ClassNotFoundException{
        //logger.debug( "URL: " + url.toString() );
        String path = URLDecoder.decode( url.getFile(),  "UTF-8" );
        File file = new File( path );
        if( file.isDirectory() ){
            path = file.getPath();
            readDir( file, classLoader, path.length() );
        }
    }

    private void readClassPath( URL url, ClassLoader classLoader ) throws UnsupportedEncodingException, IOException, ClassNotFoundException{
        //logger.debug( "URL: " + url.toString() );
        String path = URLDecoder.decode( url.getFile(),  "UTF-8" );
        File file = new File( path );
        if( file.isFile() )
            readJar( file, classLoader );
        else
        if( file.isDirectory() ){
            path = file.getPath();
            readDir( file, classLoader, path.length() );
        }
    }

    private void readDir( File dir, ClassLoader classLoader, int removePos ){
        File[] files = dir.listFiles();

        //for( File file: files ){
        for( int i=0;i<files.length;i++ ){
            File file  = files[i];
            if( file.isDirectory() )
                readDir( file, classLoader, removePos );
            else
            if( file.isFile() ){
                String name = file.getPath();
                if( name.endsWith( ".class" ) ){
                    name = name.substring( removePos + 1, name.length()-6 ).replace( "/" , "." ).replace( "\\", "." );
                    try{
                        checkClass( Class.forName( name, false, classLoader) );
                    }
                    catch( Throwable e ){}
                }
            }
        }
    }

    private void readJar( File file, ClassLoader classLoader ) throws IOException, ClassNotFoundException{
        JarFile jar = null;
        jar = new JarFile( file );
        try{
            Enumeration entrys = jar.entries();
            while( entrys.hasMoreElements() ){
                JarEntry entry = (JarEntry) entrys.nextElement();
                String name = entry.getName();

                if( name.endsWith( ".class" ) ){
                    String tmp = name.replace( "/" , "." ).substring( 0, name.length()-6 );
                    try{
                        checkClass( Class.forName( tmp, false, classLoader) );
                    }
                    catch( Throwable e ){}
                }
                //System.out.println( entry.getName() );
            }
        }
        catch( Exception e ){
            if( jar != null )
                jar.close();
        }
        jar.close();
    }

    public void setFilter( ScannerFilter filter ){
        this.filter = filter;
    }

    private void checkClass( Class classe ){
        if( filter == null )
            throw new NullPointerException();

        if( !listClass.contains(classe) && filter.accepts( classe ) ){
            listClass.add( classe );
        }
    }

    public List getClasses() {
        return listClass;
    }

    public ScannerFilter getFilter() {
        return filter;
    }
}