/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos;

import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingBeanUtil;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Brandao
 */
public class ConstructorBuilder extends RestrictionBuilder{

    private Bean mappingBean;
    
    private BeanBuilder beanBuilder;
    
    private ValidatorFactory validatorFactory;

    private Controller controller;

    public ConstructorBuilder(Bean mappingBean, 
    		BeanBuilder beanBuilder, 
            ValidatorFactory validatorFactory, Controller controller) {
        super(mappingBean.getConstructor().getValidator().getConfiguration());
        this.mappingBean = mappingBean;
        this.beanBuilder = beanBuilder;
        this.validatorFactory = validatorFactory;
        this.controller = controller;
    }
    
    /**
     * Constr�i o mapeamento de um argumento do construtor.
     *
     * @param name Identifica��o.
     * @param target Classe alvo do mapeamento.
     * @return Construtor do argumento.
     */
    public BeanBuilder buildConstructorArg( String name, Class<?> target ){
        
        name = StringUtil.adjust(name);
        
        String beanName = this.mappingBean.getName()
                + "#" + this.mappingBean.getConstructor().size();

        BeanBuilder beanBuilder =
                this.beanBuilder.getControllerBuilder().buildMappingBean(
                        beanName, this.mappingBean.getName(), target);

        this.addMappedContructorArg(name, beanName);

        return beanBuilder;
    }

    /**
     * Constrói o mapeamento de um argumento do construtor.
     *
     * @param name Identificação.
     * @param target Classe alvo do mapeamento.
     * @param classType Tipo do argumento do contrutor.
     * @return Construtor do argumento.
     */
    public BeanBuilder buildConstructorArg( String name, Class<?> classType, Class<?> target ){
        
        name = StringUtil.adjust(name);
        
        String beanName = this.mappingBean.getName()
                + "#" + this.mappingBean.getConstructor().size();

        BeanBuilder beanBuilder =
                this.beanBuilder.getControllerBuilder().buildMappingBean(
                        beanName, this.mappingBean.getName(), target);

        this.addMappedContructorArg(name, beanName);

        return beanBuilder;
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param enumProperty Usado no mapeamento argumentos do tipo enum.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addContructorArg( String name,
            EnumerationType enumProperty ){
        return addContructorArg( name, enumProperty, null, null, 
                ScopeType.PARAM, null, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param temporalProperty Usado no mapeamento de datas.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addContructorArg( String name,
            String temporalProperty ){
        return addContructorArg( name, EnumerationType.ORDINAL, 
                temporalProperty, null, ScopeType.PARAM, null, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor que não possui valor.
     *
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addNullContructorArg(){
        return addContructorArg( null, EnumerationType.ORDINAL,
                null, null, ScopeType.PARAM, null, true, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param type Faz o processamento do argumento.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addContructorArg( String name,
            Type type ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null,ScopeType.PARAM, null, false, type, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param mapping Mapeamento customizado.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addMappedContructorArg( String name, String mapping ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.PARAM, null, false, null, null );
    }

    public ConstructorArgBuilder addMappedContructorArg( String name, String mapping, Class<?> type ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.PARAM, null, false, false, null, (Object)type );
    }
    
    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addContructorArg( String name ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, ScopeType.PARAM, null, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param scope Escopo.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addContructorArg( String name, ScopeType scope ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, scope, null, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param value Valor da propriedade. Tem a mesma fun��o do modificador final.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addStaticContructorArg( String name, Object value ){
        return addContructorArg( name,
            EnumerationType.ORDINAL, "dd/MM/yyyy", null, ScopeType.PARAM,
            value, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @param enumProperty Usado no mapeamento argumentos do tipo enum.
     * @param temporalProperty Usado no mapeamento de datas.
     * @param mapping Mapeamento customizado.
     * @param scope Escopo.
     * @param value Valor do argumento do contrutor.
     * @param factory Faz o processamento do argumento.
     * @param type Tipo do argumento do construtor.
     * @return Construtor do argumento.
     */
    public ConstructorArgBuilder addContructorArg( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, boolean nullable, Type typeDef, Class<?> type ){
        return addContructorArg( name, enumProperty, temporalProperty, mapping,
            scope, value, nullable, false, typeDef, (Object)type );
    }

    public ConstructorArgBuilder addGenericContructorArg(String name, Class<?> type){
    	return 
			this.addContructorArg(name, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
                BrutosConstants.DEFAULT_SCOPETYPE, null, false, true, null, type );
    }

    public ConstructorArgBuilder addGenericContructorArg(String name){
    	return 
			this.addContructorArg(name, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null,
                BrutosConstants.DEFAULT_SCOPETYPE, null, false, true, null, null);
    }
    
    public ConstructorArgBuilder addContructorArg( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, boolean nullable, boolean generic, Type typeDef, Object type ){

        name = StringUtil.adjust(name);

        if(StringUtil.isEmpty(name) && value == null && !nullable)
        	throw new IllegalArgumentException("bean name is required");
        
        if(scope == null)
        	throw new MappingException("invalid scope");
        
        /*
        name = StringUtil.isEmpty(name)?
                "arg" + this.mappingBean.getConstructor().size() :
                name;
        */
        
        ConstructorArgBean arg =
            (ConstructorArgBean) MappingBeanUtil.createDependencyBean(name, null,
                enumProperty, temporalProperty, mapping, scope, value, nullable,
                generic, typeDef, type, MappingBeanUtil.CONSTRUCTOR_ARG, this.mappingBean, 
                this.validatorFactory, this.controller);

        getLogger()
            .info(
                String.format("%s added constructor arg %s",
                new Object[]{
                    this.getPrefixLogger(),
                    String.valueOf(this.mappingBean.getConstructor().size())} ) );
        
        Configuration validatorConfig = new Configuration();
        arg.setValidator( this.validatorFactory.getValidator(validatorConfig) );
        this.mappingBean.getConstructor().addConstructorArg(arg);
        return new ConstructorArgBuilder(arg, this, this.validatorFactory);
    }

    protected String getPrefixLogger(){
        return this.mappingBean.getName() + ":";
    }
    
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(ConstructorBuilder.class);
    }

    public int getConstructorArgSize(){
        return mappingBean.getConstructor().size();
    }

    public ConstructorArgBuilder getConstructorArg(int index){
        ConstructorArgBean arg = mappingBean.getConstructor().getConstructorArg(index);
        return new ConstructorArgBuilder(arg, this, this.validatorFactory);
    }
    
    public Class<?> getClassType(){
        return this.mappingBean.getClassType();
    }
    
    public BeanBuilder getBeanBuilder(){
        return this.beanBuilder;
    }
    
}
