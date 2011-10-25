/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.brandao.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.FlowController;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.http.BrutosFile;
import org.brandao.brutos.web.http.Download;
import org.brandao.brutos.web.http.download.FileDownload;
/**
 *
 * @author Neto
 */
public class IndexController{

    private String text;
    private String title;
    private String dir;
    private String mensagem;
    
    public IndexController( String dir ){
        this.dir = dir;
    }

    public void addFile( BrutosFile file ) throws IOException{
        if( file != null )
            this.mensagem = "Nome: " + 
                            file.getFileName() +
                            " tamanho: " +
                            file.getFile().length();
    }

    public void delete( String fileName ){
        File file = new File( getDir() + "/" + fileName );
        if( file.exists() ){
            file.delete();
            this.mensagem = "arquivo apagado!";
        }
    }

    public Download download( final String fileName ){
        final File file = new File( getDir() + "/" + fileName );
        if( file.exists() ){
            return new FileDownload( file );
        }
        else{
            FlowController
                .execute(IndexController.class, null);
            return null;
        }
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getFiles() {
        File dstFile = new File( dir );
        String[] r = dstFile.list();
        r = r == null? new String[]{} : r;
        return Arrays.asList( r );
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
