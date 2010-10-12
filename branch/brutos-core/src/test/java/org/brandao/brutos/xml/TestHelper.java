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

package org.brandao.brutos.xml;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Afonso Brandao
 */
public class TestHelper {

    public static class MyBean {

        private long property1;
        private String property2;

        public MyBean(){
            this( 0, null );
        }

        public MyBean( long property1, String property2 ){
            this.property1 = property1;
            this.property2 = property2;
        }

        public long getProperty1() {
            return property1;
        }

        public void setProperty1(long property1) {
            this.property1 = property1;
        }

        public String getProperty2() {
            return property2;
        }

        public void setProperty2(String property2) {
            this.property2 = property2;
        }

        public boolean equals( Object o ){
            return o instanceof MyBean? ((MyBean)o).property1 == property1 : false;
        }

        public int hashCode(){
            return (int)property1;
        }
    }

    public static class MyBeanFactory {

        public MyBeanFactory(){
        }

        public Object createInstance() {
            return new MyBean( 123456, "String test" );
        }

    }


    public static class MyBeanTypes {

        private Class property1;
        private boolean property2;
        private byte property3;
        private char property4;
        private double property5;
        private float property6;
        private int property7;
        private long property8;
        private short property9;
        private String property10;
        private Boolean property11;
        private Byte property12;
        private Character property13;
        private Double property14;
        private Float property15;
        private Integer property16;
        private Long property17;
        private Short property18;
        private List<String> property19;
        private Set<Integer> property20;
        private MyEnum property21;
        private Map<String, MyBean> property22;
        private Properties property23;
        private Map<MyBean, MyBean> property24;
        private Map<MyBean, List<MyBean>> property25;
        private Map<MyBean, Map<Integer,MyBean>> property26;
        private Map<MyBean, Set<MyBean>> property27;
        private Map<MyBean, Properties> property28;
        private Date property29;

        public MyBeanTypes(){
        }

        public MyBeanTypes( Class property1 ){
            this.property1 = property1;
        }

        public MyBeanTypes( boolean property2 ){
            this.property2 = property2;
        }

        public MyBeanTypes( byte property3 ){
            this.property3 = property3;
        }

        public MyBeanTypes( char property4 ){
            this.property4 = property4;
        }

        public MyBeanTypes(double property5){
            this.property5 = property5;
        }

        public MyBeanTypes(float property6){
            this.property6 = property6;
        }

        public MyBeanTypes(int property7){
            this.property7 = property7;
        }

        public MyBeanTypes(long property8){
            this.property8 = property8;
        }

        public MyBeanTypes(short property9){
            this.property9 = property9;
        }

        public MyBeanTypes(String property10){
            this.property10 = property10;
        }

        public MyBeanTypes(Boolean property11){
            this.property11 = property11;
        }

        public MyBeanTypes(Byte property12){
            this.property12 = property12;
        }

        public MyBeanTypes(Character property13){
            this.property13 = property13;
        }

        public MyBeanTypes(Double property14){
            this.property14 = property14;
        }

        public MyBeanTypes(Float property15){
            this.property15 = property15;
        }

        public MyBeanTypes(Integer property16){
            this.property16 = property16;
        }

        public MyBeanTypes(Long property17){
            this.property17 = property17;
        }

        public MyBeanTypes(Short property18){
            this.property18 = property18;
        }

        public MyBeanTypes(List<String> property19){
            this.property19 = property19;
        }

        public MyBeanTypes(Set<Integer> property20){
            this.property20 = property20;
        }

        public MyBeanTypes(MyEnum property21){
            this.property21 = property21;
        }

        public MyBeanTypes(Map<String, MyBean> property22, int dif ){
            this.property22 = property22;
        }

        public MyBeanTypes(Properties property23){
            this.property23 = property23;
        }

        public MyBeanTypes(Map<MyBean, MyBean> property24, double dif){
            this.property24 = property24;
        }

        public MyBeanTypes(Map<MyBean, List<MyBean>> property25, String dif){
            this.property25 = property25;
        }

        public MyBeanTypes(Map<MyBean, Map<Integer,MyBean>> property26, long dif){
            this.property26 = property26;
        }

        public MyBeanTypes(Map<MyBean, Set<MyBean>> property27, float dif){
            this.property27 = property27;
        }

        public MyBeanTypes(Map<MyBean, Properties> property28){
            this.property28 = property28;
        }

        public Class getProperty1() {
            return property1;
        }

        public void setProperty1(Class property1) {
            this.property1 = property1;
        }

        public boolean isProperty2() {
            return property2;
        }

        public void setProperty2(boolean property2) {
            this.property2 = property2;
        }

        public byte getProperty3() {
            return property3;
        }

        public void setProperty3(byte property3) {
            this.property3 = property3;
        }

        public char getProperty4() {
            return property4;
        }

        public void setProperty4(char property4) {
            this.property4 = property4;
        }

        public double getProperty5() {
            return property5;
        }

        public void setProperty5(double property5) {
            this.property5 = property5;
        }

        public float getProperty6() {
            return property6;
        }

        public void setProperty6(float property6) {
            this.property6 = property6;
        }

        public int getProperty7() {
            return property7;
        }

        public void setProperty7(int property7) {
            this.property7 = property7;
        }

        public long getProperty8() {
            return property8;
        }

        public void setProperty8(long property8) {
            this.property8 = property8;
        }

        public short getProperty9() {
            return property9;
        }

        public void setProperty9(short property9) {
            this.property9 = property9;
        }

        public String getProperty10() {
            return property10;
        }

        public void setProperty10(String property10) {
            this.property10 = property10;
        }

        public Boolean getProperty11() {
            return property11;
        }

        public void setProperty11(Boolean property11) {
            this.property11 = property11;
        }

        public Byte getProperty12() {
            return property12;
        }

        public void setProperty12(Byte property12) {
            this.property12 = property12;
        }

        public Character getProperty13() {
            return property13;
        }

        public void setProperty13(Character property13) {
            this.property13 = property13;
        }

        public Double getProperty14() {
            return property14;
        }

        public void setProperty14(Double property14) {
            this.property14 = property14;
        }

        public Float getProperty15() {
            return property15;
        }

        public void setProperty15(Float property15) {
            this.property15 = property15;
        }

        public Integer getProperty16() {
            return property16;
        }

        public void setProperty16(Integer property16) {
            this.property16 = property16;
        }

        public Long getProperty17() {
            return property17;
        }

        public void setProperty17(Long property17) {
            this.property17 = property17;
        }

        public Short getProperty18() {
            return property18;
        }

        public void setProperty18(Short property18) {
            this.property18 = property18;
        }

        public List<String> getProperty19() {
            return property19;
        }

        public void setProperty19(List<String> property19) {
            this.property19 = property19;
        }

        public Set<Integer> getProperty20() {
            return property20;
        }

        public void setProperty20(Set<Integer> property20) {
            this.property20 = property20;
        }

        public MyEnum getProperty21() {
            return property21;
        }

        public void setProperty21(MyEnum property21) {
            this.property21 = property21;
        }

        public Map<String, MyBean> getProperty22() {
            return property22;
        }

        public void setProperty22(Map<String, MyBean> property22) {
            this.property22 = property22;
        }

        public Properties getProperty23() {
            return property23;
        }

        public void setProperty23(Properties property23) {
            this.property23 = property23;
        }

        public Map<MyBean, MyBean> getProperty24() {
            return property24;
        }

        public void setProperty24(Map<MyBean, MyBean> property24) {
            this.property24 = property24;
        }

        public Map<MyBean, List<MyBean>> getProperty25() {
            return property25;
        }

        public void setProperty25(Map<MyBean, List<MyBean>> property25) {
            this.property25 = property25;
        }

        public Map<MyBean, Map<Integer, MyBean>> getProperty26() {
            return property26;
        }

        public void setProperty26(Map<MyBean, Map<Integer, MyBean>> property26) {
            this.property26 = property26;
        }

        public Map<MyBean, Set<MyBean>> getProperty27() {
            return property27;
        }

        public void setProperty27(Map<MyBean, Set<MyBean>> property27) {
            this.property27 = property27;
        }

        public Map<MyBean, Properties> getProperty28() {
            return property28;
        }

        public void setProperty28(Map<MyBean, Properties> property28) {
            this.property28 = property28;
        }

        public Date getProperty29() {
            return property29;
        }

        public void setProperty29(Date property29) {
            this.property29 = property29;
        }

    }

    public static enum MyEnum {

        VALUE1,
        VALUE2,
        VALUE3

    }

    public static class MyType implements Type{

        public Object getValue(HttpServletRequest request, ServletContext context, Object value) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setValue(HttpServletResponse response, ServletContext context, Object value) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Class getClassType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}
