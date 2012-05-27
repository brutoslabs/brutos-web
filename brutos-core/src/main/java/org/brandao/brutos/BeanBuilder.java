/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Constr�i o mapeamento de um bean.<br>
 * Com essa classe � poss�vel definir as depend�ncias do bean, tanto por construtor
 * quanto por m�todo. � poss�vel tamb�m obter a inst�ncia do bean por meio de
 * uma f�brica ou m�todo est�tico. <br>
 * Sua inst�ncia � obtida a partir da classe ControllerBuilder.<br>
 * BeanBuilder n�o tem a mesma fun��o que um container IOC, ela apenas faz o
 * mapeamento de uma requisi��o em um determinado bean.
 *
 * <p>No exemplo abaixo a depend�ncia do bean � mapeada por construtor.</p>
 *
 * Ex:
 * <pre>
 *
 * &lt;html&gt;
 *   &lt;body&gt;
 *    &lt;a href="/index.jbrs?idBean=100"&gt;show&lt;/a&gt;
 *   &lt;/body&gt;
 * &lt;/html&gt;
 *
 * public class MyBean{
 *
 *   private int id;
 *
 *   public MyBean( int val ){
 *     this.id = id;
 *   }
 *   ...
 * }
 *
 * beanBuilder.addContructorArg( "idBean" );
 *
 * </pre>
 *
 * No pr�ximo exemplo o mesmo par�metro � mapeado em uma propriedade.
 *
 * Ex:
 * <pre>
 * public class MyBean{
 *
 *   private int id;
 *
 *   public MyBean(){
 *   }
 *
 *   public void setId( int id ){
 *     this.id = id;
 *   }
 *   ...
 * }
 *
 * beanBuilder.addProperty( "id","idBean" );
 *
 * </pre>
 *
 * Nesse exemplo � feito o mapeamento de um atributo do tipo enum.
 * <pre>
 *
 * &lt;html&gt;
 *   &lt;body&gt;
 *    &lt;a href="/index.jbrs?idBean=1"&gt;show&lt;/a&gt;
 *   &lt;/body&gt;
 * &lt;/html&gt;
 *
 * public enum MyEnum{
 *   VALUE1("Valor 1"),
 *   VALUE2("Valor 2"),
 *   VALUE3("Valor 3");
 *
 *   ...
 *
 * }
 *
 * public class MyBean{
 *
 *   private MyEnum id;
 *
 *   public MyBean(){
 *   }
 *
 *   public void setId( MyEnum id ){
 *     this.id = id;
 *   }
 *   ...
 * }
 *
 * beanBuilder.addProperty( "idBean","id", EnumerationType.ORDINAL );
 *
 * </pre>
 *
 * O mapeamento do enum pode tamb�m ser feito da seguinte maneira:
 * <pre>
 *
 * &lt;html&gt;
 *   &lt;body&gt;
 *    &lt;a href="/index.jbrs?enumName=VALUE2"&gt;show&lt;/a&gt;
 *   &lt;/body&gt;
 * &lt;/html&gt;
 *
 * controllerBuilder
 *      .buildMappingBean( "myEnumMapping", MyEnum.class )
 *      .setMethodfactory( "valueOf" )
 *      .addConstructorArg( "enumName" );
 *
 * beanBuilder.addMappedProperty( "id", "myEnumMapping" );
 *
 * </pre>
 *
 *
 * @author Afonso Brandao
 */
public class BeanBuilder {

    Controller controller;
    ControllerBuilder controllerBuilder;
    Bean mappingBean;
    ValidatorProvider validatorProvider;
    AbstractApplicationContext applicationContext;

    public BeanBuilder(
            Bean mappingBean,
            Controller controller,
            ControllerBuilder controllerBuilder,
            ValidatorProvider validatorProvider,
            AbstractApplicationContext applicationContext) {

        this.controllerBuilder = controllerBuilder;
        this.mappingBean = mappingBean;
        this.controller = controller;
        this.validatorProvider = validatorProvider;
        this.applicationContext = applicationContext;
    }

    /**
     * Define o nome da f�brica do bean.
     * @param factory Nome da f�brica.
     * @return Construtor do mapeamento.
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
        
        mappingBean.getConstructor().setMethodFactory(methodFactory);
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
    public BeanBuilder setKey( String ref ){

        if( ref == null )
            throw new NullPointerException();

        if( !this.mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );

        ref = ref == null || ref.replace( " ", "" ).length() == 0? null : ref;

        if( !controller.getMappingBeans().containsKey( ref ) )
            throw new NotFoundMappingBeanException(
                    String.format(
                        "mapping %s not found: %s",
                        new Object[]{
                            ref,
                            controller.getClassType().getName()} ) );


        getLogger()
            .info(
                String.format("%s defined key %s",
                new Object[]{
                    this.getPrefixLogger(),
                    ref} ) );
        
        Bean key = (Bean)controller.getMappingBean(ref);
        ((MapBean)mappingBean).setMappingKey(key);
        return this;
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

        if( !this.mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );
        
        String beanName = mappingBean.getName() + "#key";
        BeanBuilder bb = controllerBuilder
                    .buildMappingBean(beanName, type);

        setKey(beanName);
        return bb;
    }
    
    /**
     * Constr�i o mapeamento dos elementos de uma cole��o.
     *
     * @param type Classe alvo do mapeamento.
     * @return Construtor do mapeamento dos elementos.
     */
    public BeanBuilder buildElement( Class type ){

        String beanName = mappingBean.getName() + "#bean";
        BeanBuilder bb = controllerBuilder
                    .buildMappingBean(beanName, type);

        setElement( beanName );

        return bb;
    }

    /**
     * Define a representa��o do �ndice do objeto em uma cole��o.
     * @param indexFormat Representa��o.
     * @return Construtor do mapeamento.
     */
    public BeanBuilder setIndexFormat( String indexFormat ){
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
    public BeanBuilder setElement( String ref ){

        if( ref == null )
            throw new NullPointerException();

        if( !this.mappingBean.isCollection() && !this.mappingBean.isMap() )
            throw new BrutosException(
                String.format("is not allowed for this type: %s",
                    new Object[]{this.mappingBean.getClassType()} ) );
        
        ref = ref == null || ref.replace( " ", "" ).length() == 0? null : ref;

        if( !controller.getMappingBeans().containsKey( ref ) )
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
        
        Bean bean = (Bean) controller.getMappingBeans().get( ref );

        /*
        if( !bean.isBean() )
            throw new BrutosException(
                    "not allowed: " +
                    webFrame.getClassType().getName() );
        */

        ((CollectionBean)mappingBean).setBean( bean );
        return this;
    }

    /**
     * Constr�i o mapeamento de uma propriedade.
     * 
     * @param name Identifica��o.
     * @param propertyName Nome da propriedade
     * @param target Classe alvo do mapeamento.
     * @return Construtor da propriedade.
     */
    public BeanBuilder buildProperty( String name, String propertyName, Class target ){
        String beanName = this.mappingBean.getName() + "#" + propertyName;
        
        BeanBuilder beanBuilder = 
                this.controllerBuilder.buildMappingBean(beanName, target);

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

        PropertyBean propertyBean =
            (PropertyBean) this.createDependencyBean(name, propertyName, true,
                enumProperty, temporalProperty, mapping, scope, value, nullable, 
                type, null);

        getLogger()
            .info(
                String.format("%s added property %s",
                new Object[]{
                    this.getPrefixLogger(),
                    propertyName} ) );
        
        Configuration validatorConfig = new Configuration();
        propertyBean.setValidator( validatorProvider.getValidator( validatorConfig ) );
        this.mappingBean.getFields().put( propertyName, propertyBean );

        return new PropertyBuilder( validatorConfig );
    }

    /**
     * Constr�i o mapeamento de um argumento do construtor.
     *
     * @param name Identifica��o.
     * @param target Classe alvo do mapeamento.
     * @return Construtor do argumento.
     */
    public BeanBuilder buildConstructorArg( String name, Class target ){
        String beanName = this.mappingBean.getName()
                + "#[" + this.mappingBean.getConstructor().size() + "]";

        BeanBuilder beanBuilder =
                this.controllerBuilder.buildMappingBean(beanName, target);

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
    public BeanBuilder buildConstructorArg( String name, Class classType, Class target ){
        String beanName = this.mappingBean.getName()
                + "#[" + this.mappingBean.getConstructor().size() + "]";

        BeanBuilder beanBuilder =
                this.controllerBuilder.buildMappingBean(beanName, target);

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
    public ConstructorBuilder addContructorArg( String name,
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
    public ConstructorBuilder addContructorArg( String name,
            String temporalProperty ){
        return addContructorArg( name, EnumerationType.ORDINAL, 
                temporalProperty, null, ScopeType.PARAM, null, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor que não possui valor.
     *
     * @return Construtor do argumento.
     */
    public ConstructorBuilder addNullContructorArg(){
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
    public ConstructorBuilder addContructorArg( String name,
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
    public ConstructorBuilder addMappedContructorArg( String name, String mapping ){
        return addContructorArg( name, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, ScopeType.PARAM, null, false, null, null );
    }

    /**
     * Faz o mapeamento de um argumento do construtor.
     *
     * @param name Nome do par�metro.
     * @return Construtor do argumento.
     */
    public ConstructorBuilder addContructorArg( String name ){
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
    public ConstructorBuilder addContructorArg( String name, ScopeType scope ){
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
    public ConstructorBuilder addStaticContructorArg( String name, Object value ){
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
    public ConstructorBuilder addContructorArg( String name,
            EnumerationType enumProperty, String temporalProperty, String mapping,
            ScopeType scope, Object value, boolean nullable, Type factory, Class type ){

        ConstructorArgBean arg =
            (ConstructorArgBean) this.createDependencyBean(name, null, false,
                enumProperty, temporalProperty, mapping, scope, value, nullable, factory, type);

        getLogger()
            .info(
                String.format("%s added constructor arg %s",
                new Object[]{
                    this.getPrefixLogger(),
                    String.valueOf(this.mappingBean.getConstructor().size())} ) );
        
        Configuration validatorConfig = new Configuration();
        arg.setValidator( validatorProvider.getValidator( validatorConfig ) );
        this.mappingBean.getConstructor().addConstructorArg(arg);
        return new ConstructorBuilder( validatorConfig );
    }

    private DependencyBean createDependencyBean( String name, String propertyName,
            boolean propertyNameRequired, EnumerationType enumProperty,
            String temporalProperty, String mapping, ScopeType scope,
            Object value, boolean nullable, Type factory, Class type ){

        name = 
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;

        propertyName =
            propertyName == null || propertyName.replace( " ", "" ).length() == 0?
                null :
                propertyName;

        temporalProperty = 
            temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0?
                null :
                temporalProperty;

        mapping =
            mapping == null || mapping.replace( " ", "" ).length() == 0?
                null :
                mapping;

        if( propertyNameRequired ){
            if( propertyName == null )
                throw new BrutosException( "the property name is required!" );
            else
            if( this.mappingBean.getFields().containsKey( propertyName ) )
                throw new BrutosException( "duplicate property name: " + propertyName );
        }

        DependencyBean dependencyBean =
                propertyNameRequired? 
                    (DependencyBean)new PropertyBean(this.mappingBean) :
                    new ConstructorArgBean(this.mappingBean);
        
        dependencyBean.setEnumProperty( enumProperty );
        dependencyBean.setParameterName( name );
        dependencyBean.setNullable(nullable);
        
        if( propertyNameRequired )
            ((PropertyBean)dependencyBean).setName(propertyName);
        
        dependencyBean.setTemporalType( temporalProperty );
        dependencyBean.setValue(value);
        dependencyBean.setScope( applicationContext.getScopes().get(scope) );

        BeanInstance bean = new BeanInstance( null, mappingBean.getClassType() );

        if( propertyName != null && !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                mappingBean.getClassType().getName() + "." + propertyName );

        if( mapping != null )
            dependencyBean.setMapping( mapping );

        if( factory != null )
            dependencyBean.setType( factory );
        else{
            try{
                if( propertyNameRequired ){
                    dependencyBean.setType(
                            TypeManager.getType(
                                bean.getGenericType(propertyName),
                                enumProperty,
                                temporalProperty ) );
                }
                else
                if( type != null )
                    dependencyBean.setType(TypeManager.getType(type, enumProperty, 
                            temporalProperty));
                
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException(
                        String.format( "%s.%s : %s" ,
                            new Object[]{
                                controller.getClassType().getName(),
                                propertyName,
                                e.getMessage()} ) );
            }
        }
        Configuration validatorConfig = new Configuration();
        dependencyBean.setValidator( validatorProvider.getValidator( validatorConfig ) );
        return dependencyBean;
    }

    public ControllerBuilder getControllerBuilder(){
        return this.controllerBuilder;
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
