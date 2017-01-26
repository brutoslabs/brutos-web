

package org.brandao.brutos;

import junit.framework.TestCase;
import org.brandao.brutos.web.util.WebUtil;


public class WebUtilTest extends TestCase{
    
    public void testUriCheck1(){
        WebUtil.checkURI("/{myid:\\d+}", true);
    }
    
    public void testUriCheck2(){
        try{
            WebUtil.checkURI("/{myid:\\d+}|", true);
            TestCase.fail("expected IllegalArgumentException");
        }
        catch(BrutosException e){
            
        }
    }
    
}
