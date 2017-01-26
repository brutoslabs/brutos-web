

package org.brandao.brutos.helper.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class SimpleBean {

    private String arg;
    private int arg2;
    private SimpleBean bean;
    private EnumTest enumTest;
    private Calendar calendar;
    private Date date;
    private Map<EnumTest,String> map;
    
    public SimpleBean(){
    }

    public SimpleBean(String arg){
        this.arg = arg;
    }

    public SimpleBean(Calendar arg){
        this.calendar = arg;
    }

    public SimpleBean(Date arg){
        this.date = arg;
    }

    public SimpleBean(EnumTest enumTest){
        this.enumTest = enumTest;
    }

    public SimpleBean(int arg2){
        this.arg2 = arg2;
    }

    public SimpleBean(SimpleBean arg3){
        this.bean = arg3;
    }

    public SimpleBean(String arg,int arg2,SimpleBean bean){
        this.arg = arg;
        this.arg2 = arg2;
        this.bean = bean;
    }

    public SimpleBean(String arg,int arg2){
        this(arg,arg2,null);
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public SimpleBean getBean() {
        return bean;
    }

    public void setBean(SimpleBean bean) {
        this.bean = bean;
    }

    public EnumTest getEnumTest() {
        return enumTest;
    }

    public void setEnumTest(EnumTest enumTest) {
        this.enumTest = enumTest;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<EnumTest,String> getMap() {
        return map;
    }

    public void setMap(Map<EnumTest,String> map) {
        this.map = map;
    }
}
