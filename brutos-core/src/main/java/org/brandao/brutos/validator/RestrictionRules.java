

package org.brandao.brutos.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestrictionRules {

    public static final RestrictionRules MIN         = new RestrictionRules( "min" );
    public static final RestrictionRules MINLENGTH   = new RestrictionRules( "minlength" );
    public static final RestrictionRules MAX         = new RestrictionRules( "max" );
    public static final RestrictionRules MAXLENGTH   = new RestrictionRules( "maxlength" );
    public static final RestrictionRules MATCHES     = new RestrictionRules( "matches" );
    public static final RestrictionRules REQUIRED    = new RestrictionRules( "required" );
    public static final RestrictionRules EQUAL       = new RestrictionRules( "equal" );
    public static final RestrictionRules CUSTOM      = new RestrictionRules( "custom" );

    private final static Map defaultRules = new HashMap();

    static{
        defaultRules.put( RestrictionRules.MIN.toString(),        RestrictionRules.MIN );
        defaultRules.put( RestrictionRules.MINLENGTH.toString(),  RestrictionRules.MINLENGTH );
        defaultRules.put( RestrictionRules.MAX.toString(),        RestrictionRules.MAX );
        defaultRules.put( RestrictionRules.MAXLENGTH.toString(),  RestrictionRules.MAXLENGTH );
        defaultRules.put( RestrictionRules.MATCHES.toString(),    RestrictionRules.MATCHES );
        defaultRules.put( RestrictionRules.REQUIRED.toString(),   RestrictionRules.REQUIRED );
        defaultRules.put( RestrictionRules.EQUAL.toString(),      RestrictionRules.EQUAL );
        defaultRules.put( RestrictionRules.CUSTOM.toString(),     RestrictionRules.CUSTOM );
    }

    private String name;

    public RestrictionRules( String name ){
        this.name = name;
    }

    public static List getRestrictionRules(){
        return new ArrayList(defaultRules.values());
    }

    public String toString(){
        return this.name;
    }

    public static RestrictionRules valueOf( String value ){
        if( defaultRules.containsKey(value) )
            return (RestrictionRules)defaultRules.get( value );
        else
            return null;//return new RestrictionRules( value );
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RestrictionRules other = (RestrictionRules) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }


}
