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

package org.brandao.brutos.annotation.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class AnnotationUtilTest extends TestCase{
    
    public void test1(){
        Object key = AnnotationUtil.getKeyType(TestMap.class);
        Object element = AnnotationUtil.getCollectionType(TestMap.class);
        Assert.assertEquals(String.class, key);
        Assert.assertEquals(Integer.class, element);
    }

    public void test2(){
        Object element = AnnotationUtil.getCollectionType(TestCollection.class);
        Assert.assertEquals(Integer.class, element);
    }
    
    private static class TestMap extends HashMap<String,Integer>{
    }

    private static class TestCollection extends ArrayList<Integer>{
    }
    
}
