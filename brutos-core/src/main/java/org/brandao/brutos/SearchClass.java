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
public class SearchClass {

    private List<Class> listClass;
    private CheckSearch check;

    public SearchClass(){
        this.listClass = new ArrayList<Class>();
    }

    public void load( ClassLoader classLoader ){
        try{
            URLClassLoader urls = (URLClassLoader)classLoader;
            for( URL url: urls.getURLs() )
                readClassPath( url, classLoader );
        }
        catch( Exception e ){}
    }

    public void loadDirs( ClassLoader classLoader ){
        try{
            URLClassLoader urls = (URLClassLoader)classLoader;
            for( URL url: urls.getURLs() )
                readClassDir( url, classLoader );
        }
        catch( Exception e ){}
    }

    public void manifest(){
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Enumeration<URL> urls = classLoader.getResources( "META-INF/MANIFEST.MF" );

            while( urls.hasMoreElements() ){
                URL url = urls.nextElement();
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
                /*
                fileName = dirName + "/" + fileName;
                File file = new File( fileName );
                if( file.exists() )
                    readJar( file, classLoader );
                */
                if (".".equals(fileName)) {
                    readClassDir(new URL(String.format("file:/%s", dirName)), classLoader);
                } else {
                    fileName = dirName + "/" + fileName;
                    File file = new File(fileName);
                    if (file.exists() && file.isFile())
                        readJar(file, classLoader);
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

        for( File file: files ){
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
            Enumeration<JarEntry> entrys = jar.entries();
            while( entrys.hasMoreElements() ){
                JarEntry entry = entrys.nextElement();
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

    public void setCheck( CheckSearch check ){
        this.check = check;
    }

    private void checkClass( Class<?> classe ){
        if( check == null )
            throw new NullPointerException();

        if( !listClass.contains(classe) && check.checkClass( classe ) ){
            listClass.add( classe );
        }
    }

    public List<Class> getClasses() {
        return listClass;
    }

    public CheckSearch getCheck() {
        return check;
    }
}