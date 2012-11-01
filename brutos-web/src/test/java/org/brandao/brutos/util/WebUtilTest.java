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

package org.brandao.brutos.util;

import java.net.MalformedURLException;
import junit.framework.TestCase;
import org.brandao.brutos.BrutosException;

/**
 *
 * @author Brandao
 */
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
