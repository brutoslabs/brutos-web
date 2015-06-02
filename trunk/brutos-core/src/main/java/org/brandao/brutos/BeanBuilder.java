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
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Brandao
 */
public class BeanBuilder {

    private Controller controller;
    
    private  ControllerBuilder controllerBuilder;
    
    private  Bean mappingBean;
    
    private  ValidatorFactory validatorFactory;
    
    private  ApplicationContext applicationContext;

    private ConstructorBuilder constructorBuilder;
    
    public BeanBuilder(
            Bean mappingBean,
            Controller controller,
            ControllerBuilder controllerBuilder,
            ValidatorFactory validatorFactory,
            ApplicationContext applicationContext) {

        this.controllerBuilder = controllerBuilder;
        this.mappingBean = mappingBean;
        this.controller = controller;
        this.validatorFactory = validatorFactory;
        this.applicationContext = applicationContext;
    }

    /**
     * Define a fábrica do bean.
     * @param factory Fábrica.
     * @return Construtor do bean.
     */
    public BeanBuilder setFactory( String factory ){
        getLogger()
            .info(
                String.format("%s defined factory %s",
                new Object[]{
                    this.getPrefixLogger(),
                    factory} ) );
        mappingBean.setFactory(factory);
        return this;
    }

    /**
     * Define o nome do m�todo da f�brica.
     * @param methodFactory Nome do m�todo.
     * @return Construtor do mapeamento.
     */
    public BeanBuilder setMethodfactory( String methodFactory ){

        getLogger()
            .info(
                String.format("%s defined method factory %s",
                new Object[]{
                    this.getPrefixLogger(),
                    methodFactory} ) );
        
        mappingBean.setMethodfactory(methodFactory);
        return this;
    }

    /**
     * Define o separador. Se n�o informado, o separador ser� ".".
     * @param separator Separador
     * @return Construtor do mapeamento.
     */
    public BeanBuilder setSeparator( String separator ){
        
        getLogger()
            .info(
                String.format("%s separator defined to %s",
                new Object[]{
                    this.getPrefixLogger(),
                    separator} ) );
        
        mappingBean.setSeparator(separator);
        return this;
    }

    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param enumProperty Usado no mapeamento de enum.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty ){
        return addProperty( name, propertyName, enumProperty, null, null, 
                ScopeType.PARAM, null, false, null );
    }

    /**
     * Faz o mapeamento de uma propriedade que não possui valor.
     *
     * @param name Nome do par�metro.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addNullProperty( String propertyName ){
        return addProperty( null, propertyName, null, null, null,
                ScopeType.PARAM, null, true, null );
    }
    
    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param temporalProperty Usado no mapeamento de datas.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addProperty( String name, String propertyName,
            String temporalProperty ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, 
                temporalProperty, null, ScopeType.PARAM, null, false, null );
    }
    
    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param type Faz o processamento da propriedade.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addProperty( String name, String propertyName,
            Type type ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null,ScopeType.PARAM, null, false, type );
    }
    
    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param mapping Mapeamento customizado.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addMappedProperty( String name, String propertyName, String mapping ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.PARAM, null, false, null );
    }

    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param propertyName Nome da propriedade
     * @param mapping Mapeamento customizado.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addMappedProperty( String propertyName, String mapping ){
        return addProperty( null, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.PARAM, null, false, null );
    }

    /**
     * Define o tipo da chave.
     *
     * @param ref Nome do mapeamento.
     * @return Construtor do mapeamento.
     * @throws java.lang.NullPointerException Lan�ado se o nome do mapeamento for igual a null.
     * @throws org.brandao.brutos.NotFoundMappingBeanException Lan�ado se o
     * mapeamento n�o for encontrado.
     * @throws org.brandao.brutos.BrutosException Lançado se a classe alvo do
     * mapeamento não for uma coleção.
     */
    public KeyBuilder setMappedKey( String name, String ref ){
        return setKey( name, EnumerationType.ORDINAL, "dd/MM/yyyy", ref,
            ScopeType.PARAM, null, null, null );
    }

    public KeyBuilder setKey( String ref ){
        return setMappedKey(ref);
    }
    
    public KeyBuilder setMappedKey( String ref ){
        return setMappedKey(null, ref);
    }
    
    public KeyBuilder setKey( String name, EnumerationType enumProperty, Class classType ){
        return setKey( name, enumProperty, "dd/MM/yyyy", null,
                ScopeType.PARAM, null, null, classType );
    }

    public KeyBuilder setKey( String name, String temporalProperty, Class classType ){
        return setKey( name,EnumerationType.ORDINAL, temporalProperty, null,
                ScopeType.PARAM, null, null, classType );
    }

    public KeyBuilder setKey( String name, EnumerationType enumProperty, 
            ScopeType scope, Class classType ){
        return setKey( name, enumProperty, "dd/MM/yyyy", null,
                scope, null, null, classType );
    }

    public KeyBuilder setKey( String name, String temporalProperty, 
            ScopeType scope, Class classType ){
        return setKey( name,EnumerationType.ORDINAL, temporalProperty, null,
                scope, null, null, classType );
    }
    
    public KeyBuilder setKey( String name, ScopeType scope, Class classType ){
        return setKey( name, EnumerationType.ORDINAL, "dd/MM/yyyy", null,
                scope, null, null, classType );
    }
    
    public KeyBuilder setKey( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, Type typeDef, Class type ){
        return setKey( name, enumProperty, temporalProperty, mapping,
                scope, value, typeDef, (Object)type );
    }
    
    public KeyBuilder setKey( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, Type typeDef, Object type ){

        name = StringUtil.adjust(name);
        name = StringUtil.isEmpty(name)?
                "key" :
                name;

        if( !mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );
        
        DependencyBean key =
            MappingBeanUtil.createDependencyBean(name, null,
                enumProperty, temporalProperty, mapping, scope, value, false, 
                typeDef, type, MappingBeanUtil.DEPENDENCY, this.mappingBean, 
                this.validatorFactory, this.controller);

        ((MapBean)mappingBean).setKey(key);
        return new KeyBuilder(key, this);
    }
    
    /**
     * Contr�i o mapeamento da chave usada para acessar os elementos de uma cole��o.
     * 
     * @param type Classe alvo do mapeamento.
     * @return Construtor do mapeamento.
     * @throws org.brandao.brutos.BrutosException Lan�ado se a classe alvo do
     * mapeamento n�o for uma cole��o.
     */
    public BeanBuilder buildKey( Class type ){
        return buildKey(null, type);
    }
    
    public BeanBuilder buildKey( String name, Class type ){

        if( !this.mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );
        
        String beanName = mappingBean.getName() + "#key";
        BeanBuilder bb = controllerBuilder
                    .buildMappingBean(beanName, this.mappingBean.getName(), type);

        setMappedKey(name, beanName);
        return bb;
    }
    
    /**
     * Constr�i o mapeamento dos elementos de uma cole��o.
     *
     * @param type Classe alvo do mapeamento.
     * @return Construtor do mapeamento dos elementos.
     */
    public BeanBuilder buildElement( Class type ){
        return buildElement(null, type);
    }
    
    public BeanBuilder buildElement( String name, Class type ){

        String beanName = mappingBean.getName() + "#bean";
        BeanBuilder bb = controllerBuilder
                    .buildMappingBean(beanName, this.mappingBean.getName(), type);

        setMappedElement( name, beanName );
        
        return bb;
    }

    public ElementBuilder setMappedElement( String ref ){
        return setMappedElement(null, ref);
    }
    
    public ElementBuilder setMappedElement(String name, String ref){
        return setElement( name, EnumerationType.ORDINAL, "dd/MM/yyyy", ref,
            ScopeType.PARAM, null, false, null, null );
    }
    public ElementBuilder setElement( String name, EnumerationType enumProperty, Class classType ){
        return setElement( name, enumProperty, "dd/MM/yyyy", null,
                ScopeType.PARAM, null, false, null, classType );
    }

    public ElementBuilder setElement( String name, String temporalProperty, Class classType ){
        return setElement( name,EnumerationType.ORDINAL, temporalProperty, null,
                ScopeType.PARAM, null, false, null, classType );
    }

    public ElementBuilder setElement( String name, EnumerationType enumProperty, 
            ScopeType scope, Class classType ){
        return setElement( name, enumProperty, "dd/MM/yyyy", null,
                scope, null, false, null, classType );
    }

    public ElementBuilder setElement( String name, String temporalProperty, 
            ScopeType scope, Class classType ){
        return setElement( name,EnumerationType.ORDINAL, temporalProperty, null,
                scope, null, false, null, classType );
    }
    
    public ElementBuilder setElement( String name, ScopeType scope, Class classType ){
        return setElement( name, EnumerationType.ORDINAL, "dd/MM/yyyy", null,
                scope, null, false, null, classType );
    }

    public ElementBuilder setElement( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, boolean nullable, Type typeDef, Class type ){
        return setElement( name, enumProperty, temporalProperty, mapping,
                scope, value, nullable, typeDef, (Object)type );
    }
    
    public ElementBuilder setElement( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, boolean nullable, Type typeDef, Object type ){

        name = StringUtil.adjust(name);
        name = StringUtil.isEmpty(name)?
                "element" :
                name;
        
        if( !mappingBean.isCollection() && !mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );

        DependencyBean collection =
            MappingBeanUtil.createDependencyBean(name, null,
                enumProperty, temporalProperty, mapping, scope, value, nullable, 
                typeDef, type, MappingBeanUtil.DEPENDENCY, this.mappingBean, 
                this.validatorFactory, this.controller);

        ((CollectionBean)mappingBean).setCollection(collection);
        return new ElementBuilder(collection, this);
    }
    
    /**
     * Define a representa��o do �ndice do objeto em uma cole��o.
     * @param indexFormat Representa��o.
     * @return Construtor do mapeamento.
     */
    public BeanBuilder setIndexFormat( String indexFormat ){
        indexFormat = StringUtil.adjust(indexFormat);
        
        if(indexFormat == null)
            throw new IllegalArgumentException();
        
        if(indexFormat.indexOf("$index") == -1)
            throw new IllegalArgumentException("$index not found");
        
        mappingBean.setIndexFormat(indexFormat);
        return this;
    }

    /**
     * Define o tipo da cole��o.
     * 
     * @param ref Nome do mapeamento.
     * @return Construtor do mapeamento.
     * @throws java.lang.NullPointerException Lan�ado se o nome do mapeamento for igual a null.
     * @throws org.brandao.brutos.NotFoundMappingBeanException Lan�ado se o
     * mapeamento n�o for encontrado.
     * @throws org.brandao.brutos.BrutosException Lan�ado se a classe alvo do
     * mapeamento n�o for uma cole��o.
     */
    public RestrictionBuilder setElement( String ref ){
        return setMappedElement(null,ref);
        /*
        ref = StringUtil.adjust(ref);
        
        if( StringUtil.isEmpty(ref) )
            throw new IllegalArgumentException();

        if( !mappingBean.isCollection() && !mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );
        
        if( controller.getBean(ref) == null )
            throw new NotFoundMappingBeanException(
                    String.format(
                        "mapping %s not found: %s",
                        new Object[]{
                            ref,
                            controller.getClassType().getName()
            }) );

        getLogger()
            .info(
                String.format("%s defined element %s",
                new Object[]{
                    this.getPrefixLogger(),
                    ref} ) );
        
        Bean bean = (Bean) controller.getBean( ref );

        ((CollectionBean)mappingBean).setBean( bean );
        return this;
        */
    }

    /**
     * Constr�i o mapeamento de uma propriedade.
     * 
     * @param name Identifica��o.
     * @param propertyName Nome da propriedade
     * @param target Classe alvo do mapeamento.
     * @return Construtor da propriedade.
     */
    public BeanBuilder buildProperty( String propertyName, Class target ){
        return buildProperty( null, propertyName, target );
    }
    
    public BeanBuilder buildProperty( String name, String propertyName, Class target ){
        
        name = StringUtil.adjust(name);
        
        String beanName = this.mappingBean.getName() + "#" + propertyName;
        
        BeanBuilder beanBuilder = 
                this.controllerBuilder
                        .buildMappingBean(
                                beanName, this.mappingBean.getName(), target);

        this.addMappedProperty(name, propertyName, beanName);
        
        return beanBuilder;
    }

    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addProperty( String name, String propertyName ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, ScopeType.PARAM, null, false, null );
    }

    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param scope Escopo.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addProperty( String name, String propertyName, ScopeType scope ){
        return addProperty( name, propertyName, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, scope, null, false, null );
    }

    /**
     * Faz o mapeamento de uma propriedade.
     *
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param value Valor da propriedade. Tem a mesma fun��o do modificador final.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addStaticProperty( String name, String propertyName, Object value ){
        return addProperty( null, propertyName,
            EnumerationType.ORDINAL, "dd/MM/yyyy", null, ScopeType.PARAM, value,
            false, null );
    }

    /**
     * Faz o mapeamento de uma propriedade.
     * 
     * @param name Nome do par�metro.
     * @param propertyName Nome da propriedade
     * @param enumProperty Usado no mapeamento de enum.
     * @param temporalProperty Usado no mapeamento de datas.
     * @param mapping Mapeamento customizado.
     * @param scope Escopo.
     * @param value Valor da propriedade.
     * @param type Faz o processamento da propriedade.
     * @return Construtor do mapeamento.
     */
    public PropertyBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty, String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, Type type ){
        return addProperty(name,propertyName,enumProperty,temporalProperty,mapping, 
            scope, value, nullable, null, type );
    }
    
    public PropertyBuilder addProperty( String name, String propertyName,
            EnumerationType enumProperty, String temporalProperty, String mapping, 
            ScopeType scope, Object value, boolean nullable, Object classType, Type type ){

        name = StringUtil.adjust(name);
        name = StringUtil.isEmpty(name)?
                propertyName :
                name;
        
        PropertyBean propertyBean =
            (PropertyBean) MappingBeanUtil.createDependencyBean(name, propertyName,
                enumProperty, temporalProperty, mapping, scope, value, nullable, 
                type, classType, MappingBeanUtil.PROPERTY, this.mappingBean, 
                this.validatorFactory, this.controller);

        getLogger()
            .info(
                String.format("%s added property %s",
                new Object[]{
                    this.getPrefixLogger(),
                    propertyName} ) );
        
        Configuration validatorConfig = new Configuration();
        propertyBean.setValidator( this.validatorFactory.getValidator(validatorConfig) );
        propertyBean.setBeanProperty(mappingBean.getBeanInstance().getProperty(propertyName));
        this.mappingBean.getFields().put( propertyName, propertyBean );

        return new PropertyBuilder(propertyBean, this);
    }

    public ConstructorBuilder buildConstructor(){
        this.constructorBuilder = 
                new ConstructorBuilder(mappingBean, this, 
                        validatorFactory, controller);        
        return this.constructorBuilder;
    }
    
    public ControllerBuilder getControllerBuilder(){
        return this.controllerBuilder;
    }

    public PropertyBuilder getProperty(String name){
        PropertyBean property = (PropertyBean) mappingBean.getFields().get(name);
        return property == null? null : new PropertyBuilder(property, this);
    }
    
    public ConstructorArgBuilder getConstructorArg(int index){
        ConstructorArgBean arg = mappingBean.getConstructor().getConstructorArg(index);
        return new ConstructorArgBuilder(arg, this.constructorBuilder);
    }

    public String getName(){
        return mappingBean.getName();
    }
    
    public int getConstructorArgSize(){
        return mappingBean.getConstructor().size();
    }

    public Class getClassType(){
        return mappingBean.getClassType();
    }
    
    /**
     * Verifica se � o mapeamento de um Map.
     * @return Verdadeiro se � o mapeamento de um Map, caso contr�rio falso.
     */
    public boolean isMap(){
        return this.mappingBean.isMap();
    }

    /**
     * Verifica se � o mapeamento de uma Collection.
     * @return Verdadeiro se � o mapeamento de uma Collection,
     * caso contr�rio falso.
     */
    public boolean isCollection(){
        return this.mappingBean.isCollection();
    }

    protected String getPrefixLogger(){
        return this.mappingBean.getName() + ":";
    }
    
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(ControllerBuilder.class);
    }
    
}
