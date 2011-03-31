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

package org.brandao.brutos.ioc.spring;

import java.util.List;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.ioc.ConstructorInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.ListInject;
import org.brandao.brutos.mapping.ioc.MapInject;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.PropertiesInject;
import org.brandao.brutos.mapping.ioc.Property;
import org.brandao.brutos.mapping.ioc.PropertyInject;
import org.brandao.brutos.mapping.ioc.SetInject;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.brandao.brutos.old.programatic.Bean;

/**
 *
 * @author Afonso Brandao
 */
public class XMLParser {
    
    private Properties config;
    
    public XMLParser() {
    }

    public StringBuffer parse( List<Bean> injects ){
        return getBeans( injects );
    }
    
    public String getPropertiesPattern( PropertiesInject inject ){
        String pattern = "";
        
        pattern += "            <props>\n";
        pattern += "$value\n";
        pattern += "            </props>\n";

        pattern = pattern.replace( "$id", " id=\"" + inject.getName() + "\"" );
        pattern = pattern.replace( "$type", " class=\"" + inject.getTarget().getName() + "\"" );
        pattern = pattern.replace( "$scope", "scope=\"singleton\"" );
        pattern = pattern.replace( "$singleton", "" );
        
        String value = "";
        for( Property prop: inject.getProps() ){
            String name  = String.valueOf( prop.getKey() );
            String val   = String.valueOf( prop.getValue() instanceof ValueInject? String.valueOf( ((ValueInject)prop.getValue()).getValue() ) : "" );
            value += value.length() > 0? "\n" : "";
            value += "            <prop key=\"" + name + "\">" + val + "</prop>";
        }
        
        pattern = pattern.replace( "$value", value );
        /*
        pattern = 
            inject.isSingleton()? 
                pattern.replace( "$singleton", " singleton=\"true\"" ).replace( "$scope", "" ) : 
                pattern.replace( "$singleton", "" ).replace( "$scope", " scope=\"" + getScopeName( inject.getScope() ) + "\"" );
        */
        return pattern;
    }    
    
    public String getMapPattern( MapInject inject ){
        String pattern = "";
        
        pattern += "        <map>\n";
        pattern += "$value\n";
        pattern += "        </map>\n";
        
        String value = "";
        for( Property prop: inject.getProps() ){
            String name  = String.valueOf( prop.getKey() );
            String val   = prop.getValue() instanceof ValueInject? 
                            "<value>"+String.valueOf( ((ValueInject)prop.getValue()).getValue() )+"</value>" : 
                            "<ref bean=\""+prop.getValue().getName()+"\"/>";
            value += value.length() > 0? "\n" : "";
            value += "            <entry>\n";
            value += "                <key>\n";
            value += "                    <value>$key</value>\n";
            value += "                </key>\n";
            value += "                $val\n";
            value += "            </entry>";
            value = value.replace( "$key", name ).replace( "$val", val );
        }
        pattern = pattern.replace( "$value", value );
        return pattern;
    }
    
    public String getSetPattern( SetInject inject ){
        String pattern = "";
        
        pattern += "        <set>\n";
        pattern += "$value\n";
        pattern += "        </set>\n";
        
        String value = "";
        for( Property prop: inject.getProps() ){
            String val   = prop.getValue() instanceof ValueInject? 
                            "<value>"+String.valueOf( ((ValueInject)prop.getValue()).getValue() )+"</value>" : 
                            "<ref bean=\""+prop.getValue().getName()+"\"/>";
            value += value.length() > 0? "\n" : "";
            value += "            " + val;
        }
        pattern = pattern.replace( "$value", value );
        return pattern;
    }
    
    public String getListPattern( ListInject inject ){
        String pattern = "";
        
        pattern += "        <list>\n";
        pattern += "$value\n";
        pattern += "        </list>\n";
        
        String value = "";
        for( Property prop: inject.getProps() ){
            String val   = prop.getValue() instanceof ValueInject? 
                            "<value>"+String.valueOf( ((ValueInject)prop.getValue()).getValue() )+"</value>" : 
                            "<ref bean=\""+prop.getValue().getName()+"\"/>";
            value += value.length() > 0? "\n" : "";
            value += "            " + val;
        }
        pattern = pattern.replace( "$value", value );
        return pattern;
    }
    
    public String getConstructArgPattern( Injectable inject ){
        String pattern = "";
        
        pattern += "        <constructor-arg$type>\n";
        pattern += "$param\n";
        pattern += "        </constructor-arg>\n";
        /*
        pattern += "        <constructor-arg$ref$type$value>\n";
        pattern += "$param";
        pattern += "        </constructor-arg>\n";
        */
        
        return insertData( pattern, inject );
    }
    
    private String insertData( String pattern, Injectable inject ){
        if( inject instanceof ValueInject ){
            pattern = pattern.replace( "$type", " type=\"" + inject.getTarget().getName() + "\"" );
            pattern = pattern.replace( "$param", returnString( (ValueInject)inject ) );
            //pattern = pattern.replace( "$ref", "" );
            //pattern = pattern.replace( "$param", "" );
        }
        else
        if( inject instanceof MapInject ){
            pattern = pattern.replace( "$type", "" );
            pattern = pattern.replace( "$value", "" );
            pattern = pattern.replace( "$ref", "" );
            pattern = pattern.replace( "$param", getMapPattern( (MapInject)inject ) );
        }
        else
        if( inject instanceof SetInject ){
            pattern = pattern.replace( "$type", "" );
            pattern = pattern.replace( "$value", "" );
            pattern = pattern.replace( "$ref", "" );
            pattern = pattern.replace( "$param", getSetPattern( (SetInject)inject ) );
        }
        else
        if( inject instanceof ListInject ){
            pattern = pattern.replace( "$type", "" );
            pattern = pattern.replace( "$value", "" );
            pattern = pattern.replace( "$ref", "" );
            pattern = pattern.replace( "$param", getListPattern( (ListInject)inject ) );
        }
        else
        if( inject instanceof ComplexObjectInject ){
            pattern = pattern.replace( "$type", "" );
            pattern = pattern.replace( "$value", "" );
            pattern = pattern.replace( "$ref", "" );
            pattern = pattern.replace( "$param", getPropertiesPattern( (PropertiesInject)inject ) );
        }
        else{
            pattern = pattern.replace( "$type", "" );
            pattern = pattern.replace( "$value", "" );
            //pattern = pattern.replace( "$ref", " ref=\"" + inject.getName() + "\"" );
            pattern = pattern.replace( "$param", "<ref bean=\"" + inject.getName() + "\"/>" );
            //pattern = pattern.replace( "$param", "" );
        }
        
        return pattern;
    }
    
    public String getPropertyPattern( PropertyInject arg ){
        String pattern = "        <property$name>\n";
              pattern += "$param";
              pattern += "        </property>\n";
        
        /*
        pattern = pattern.replace( "$name", " name=\"" + name + "\"" );
        
        pattern = ref == null? 
                        pattern
                            .replace( "$param", value ) : 
                        pattern
                            .replace( "$param", "<ref bean=\"" + ref + "\"/>" ); 
        */
        return insertData( pattern, arg.getProperty() ).replace( "$name", " name=\"" + arg.getName() + "\"" );
    }

    public String getScopeName( ScopeType scope ){
        if( scope == ScopeType.APPLICATION )
            return "globalSession";
        else
            return scope.toString();
    }

    
    
    public String getMapPattern( ComplexObjectInject inject ){
        String pattern = "";
        
        pattern += "    <util:map id=\"$id\" map-class=\"$type\">\n";
        pattern += "$value\n";
        pattern += "    </util:map>\n";

        pattern = pattern.replace( "$id",inject.getName() );
        pattern = pattern.replace( "$type", inject.getTarget().getName() );
        pattern = pattern.replace( "$scope", "scope=\"singleton\"" );
        pattern = pattern.replace( "$singleton", "" );
        
        String value = "";
        for( Property prop: inject.getProps() ){
            String name  = String.valueOf( prop.getKey() );
            String val   = prop.getValue() instanceof ValueInject? 
                            "value=\""+String.valueOf( ((ValueInject)prop.getValue()).getValue() )+"\"" : 
                            "ref=\""+prop.getValue().getName()+"\"";
            value += value.length() > 0? "\n" : "";
            value += "        <entry key=\"" + name + "\" " + val + "/>";
        }
        
        pattern = pattern.replace( "$value", value );
        return pattern;
    }    
    
    public String getBeanPattern( Injectable inject ){
        String pattern = "";
        
        pattern += "    <bean$id$type$scope$singleton$factory>\n";
        pattern += "$constructor\n";
        pattern += "$setter\n";
        pattern += "    </bean>\n";

        pattern = pattern.replace( "$id", " id=\"" + inject.getName() + "\"" );
        pattern = pattern.replace( "$type", " class=\"" + inject.getTarget().getName() + "\"" );
        //pattern = pattern.replace( "$scope", " scope=\"" + getScopeName( inject.getScope() ) + "\"" );
        pattern = 
            inject.isSingleton()? 
                pattern.replace( "$singleton", " scope=\"singleton\"" ).replace( "$scope", "" ) : 
                pattern.replace( "$singleton", "" ).replace( "$scope", " scope=\"" + getScopeName( inject.getScope() ) + "\"" );

        pattern = pattern.replace( "$constructor", getConstructor( inject ) );
        pattern = pattern.replace( "$setter", getSetter( inject ) );

        String patternFactory = "$factoryMethod$factoryBean";

        pattern = pattern.replace( "$factory",
                    patternFactory
                        .replace( "$factoryMethod",
                           inject.getConstructor().isMethodFactory()?
                                " factory-method=\"" + inject.getConstructor().getMethodFactory() + "\""
                                : "" )
                        .replace( "$factoryBean",
                           inject.getFactory() != null ?
                                " factory-bean=\"" + inject.getFactory() + "\""
                                : "" )
                  );

        return pattern;
    }
    
    public StringBuffer getConstructor( Injectable inject ){
        StringBuffer result = new StringBuffer( "" );
        
        ConstructorInject constructor = inject.getConstructor();
        
        if( constructor != null ){
            List<Injectable> args = constructor.getArgs();
            
            for( Injectable arg: args ){
                result = result.length() == 0? result : result.append( "\n" );
                    result.append( 
                            getConstructArgPattern( arg )
                    );
            }
        }
        
        return result;
    }

    public StringBuffer getSetter( Injectable inject ){
        StringBuffer result = new StringBuffer( "" );
        
        List<PropertyInject> args = inject.getProperties();

        if( args != null ){
            for( PropertyInject arg: args ){
                result = result.length() == 0? result : result.append( "\n" );
                result.append( getPropertyPattern( arg ) );
            }
        }
        
        return result;
    }
    
    public StringBuffer getBeans( List<Bean> injects ){
        StringBuffer result = new StringBuffer( "" );
        result
            .append( "<?xml version=\"1.0\" encoding=\"" + config.getProperty( "org.brandao.brutos.ioc.spring.encoding", "UTF-8" ) + "\"?>" ).append( "\n" )
            .append( "<beans xmlns=\"http://www.springframework.org/schema/beans\" " ).append( "\n" )
            .append( "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" ).append( "\n" )
            .append( "xmlns:lang=\"http://www.springframework.org/schema/lang\"" ).append( "\n" )
            .append( "xsi:schemaLocation=\"" ).append( "\n" )
            .append( "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd" ).append( "\n" )
            .append( "http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang-2.0.xsd\"> " ).append( "\n" );

        for( Bean inject: injects ){
            if( !(inject.getInjectable() instanceof ComplexObjectInject) )
                result.append( getBean( inject.getInjectable() ) );
        }
        result.append( "</beans>" ).append( "\n" );
        return result;
    }
    
    private String returnString( ValueInject inject ){
        Object value = inject.toString();
        
        if( value == null )
            return "<null/>";
        else
            return "<value>" + String.valueOf( value ) + "</value>";
    }
    
    public StringBuffer getBean( Injectable inject ){
        StringBuffer result = new StringBuffer("");
        result.append( getBeanPattern( inject ) );
        return result;
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }
}
