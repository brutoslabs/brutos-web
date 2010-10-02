/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.type.json.jsonencoder;

/**
 *
 * @author Bogomil
 */
public class Utils {

    public static String trimWhiteSpace(String s){
        String[] s1 = s.split("[\\n ]");
        StringBuffer res = new StringBuffer();
        for(int i = 0; i < s1.length; i++){
            res.append(s1[i]);
        }
        return res.toString();

    }

}
