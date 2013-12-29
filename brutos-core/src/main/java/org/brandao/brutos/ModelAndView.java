/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Brandao
 */
public class ModelAndView {

    private Map modelMap;
    private String view;
    private boolean resolvedView;
    
    public ModelAndView(){
        this.modelMap = new HashMap();
    }

    /**
     * Inclui um objeto ao modelo.
     * @param name Nome do valor que será incluido no modelo.
     * @param value valor que será incluido no modelo. Não pode ser igual a null.
     * @return Objeto modelAndView atual.
     */
    public ModelAndView include(String name, Object value){
        
        if(value == null)
            throw new NullPointerException();
        
        this.modelMap.put(name, value);
        return this;
    }
    
    /**
     * Define a vista a partir de seu nome.
     * @param viewName Nome da vista.
     * @return Objeto modelAndView atual.
     */
    public ModelAndView viewName(String viewName){
        this.view = viewName;
        this.resolvedView = false;
        return this;
    }

    /**
     * Define a vista desse ModelAndView.
     * @param viewName Vista.
     * @return Objeto modelAndView atual.
     */
    public ModelAndView view(String view){
        this.view = view;
        this.resolvedView = true;
        return this;
    }
    
    /**
     * Inclui todos os objetos contido no mapa no modelo.
     * @param map Mapa.
     * @return Objeto modelAndView atual.
     */
    public ModelAndView addAll(Map map){
        this.modelMap.putAll(map);
        return this;
    }
    
    /**
     * Obtém a vista.
     * @return Vista.
     */
    public String getView(){
        return this.view;
    }
    
    /**
     * Obtém o mapa do modelo.
     * @return Mapa do modelo.
     */
    public Map getMap(){
        return this.modelMap;
    }

    /**
     * Determina se a vista foi resolvida.
     * @return Verdadeiro se a vista foi resolvida caso contrário falso. 
     */
    public boolean isResolvedView(){
        return this.resolvedView;
    }
}
