/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.annotation.helper;

import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Dependency;
import org.brandao.brutos.annotation.Property;

/**
 *
 * @author Brandao
 */
@Bean
public class BeanDTO {

    private Integer id;

    @Dependency(name="name")
    private String name;

    @Dependency(name="identificacao")
    @Temporal("dd/MM/yyyy")
    @Type(BeanDTO.class)
    private Date nasc;

    @Association(target=NewBean.class)
    List carros;

    @Association(target=NewBean.class)
    @Dependency(mappedBy="newBean")
    IBean bean;

    public BeanDTO(){
        this(null,null);
    }

    public BeanDTO(
        Integer id,
        String name){
        this.id = id;
        this.name = name;
    }

    @Property(bean="identificacao")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
