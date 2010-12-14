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

package org.brandao.brutos.programatic;

/**
 *
 * @author Afonso Brandao
 */
public class MappingBeanTestHelper {

    public static class MyBean{

        private int intProperty;

        public MyBean( Integer intProperty  ){
            this.intProperty = intProperty;
        }

        public MyBean(){
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

    }

    public static class TestController{

        private MyBean myBeanProperty;

        public void testAction( MyBean myBeanProperty ){
            this.setMyBeanProperty(myBeanProperty);
        }

        public MyBean getMyBeanProperty() {
            return myBeanProperty;
        }

        public void setMyBeanProperty(MyBean myBeanProperty) {
            this.myBeanProperty = myBeanProperty;
        }

    }
}
