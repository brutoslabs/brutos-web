

package org.brandao.brutos.util;

import org.brandao.brutos.web.util.WebUtil;
import java.net.MalformedURLException;
import junit.framework.TestCase;
import org.brandao.brutos.BrutosException;


public class WebUtilTest extends TestCase{
    
    public void test1(){
        WebUtil.checkURI("/kkkk/", true);
    }
    
    public void test2(){
        try{
            WebUtil.checkURI("kkkk/", true);
        }
        catch(BrutosException e){
            if(!(e.getCause() instanceof MalformedURLException))
                TestCase.fail("expected MalformedURLException");
        }
    }
    
    public void test3(){
        try{
            WebUtil.checkURI("", true);
        }
        catch(BrutosException e){
            if(!(e.getCause() instanceof MalformedURLException))
                TestCase.fail("expected MalformedURLException");
        }
    }

    public void test4(){
        WebUtil.checkURI("/", true);
    }

    public void test5(){
        try{
            WebUtil.checkURI("/{id:/kkk", true);
        }
        catch(BrutosException e){
            if(!(e.getCause() instanceof MalformedURLException))
                TestCase.fail("expected MalformedURLException");
        }
    }
    
    public void test6(){
        WebUtil.checkURI("/{id}/kkkk", true);
    }
    
}
