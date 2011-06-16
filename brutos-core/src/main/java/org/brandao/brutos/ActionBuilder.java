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

package org.brandao.brutos;

import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ParameterMethodMapping;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Constr�i uma a��o. A a��o pode ter ou n�o par�metros. Os par�metros podem ser
 * obtidos tanto da requisi��o, sess�o ou do contexto. Podendo ser de tipo primitivo
 * ou n�o. No caso de um objeto complexo, � poss�vel usar um mapeamento predefinido.
 * Se a a��o retornar algum valor, este ser� processado e inclu�do na requisi��o,
 * para posteriormente ser usada na vis�o. As exce��es lan�adas durante a execu��o
 * da a��o podem alterar o fluxo l�gico da aplica��o. <p>No exemplo a seguir, depois
 * de executar a a��o showPerson � exibido a vis�o personView.jsp e se for lan�ada
 * a exce��o NotExistPerson a vis�o error.jsp ser� exibida</p>.
 * 
 * <pre>
 * public class MyController{
 *
 *   public void showPerson( int id ){
 *     ...
 *   }
 * }
 *
 * controllerBuilder
 *   .addAction( "show", "showPerson", "personView.jsp" )
 *   .addThrowable( NotExistPerson.class, "error.jsp", "exception", DispatcherType.FORWARD )
 *   .addParameter( "idPerson", int.class );
 *   
 * </pre>
 * 
 * @author Afonso Brandao
 */
public class ActionBuilder {
    
    Controller controller;
    MethodForm methodForm;
    ValidatorProvider validatorProvider;
    ControllerBuilder controllerBuilder;

    public ActionBuilder( MethodForm methodForm, 
            Controller controller, ValidatorProvider validatorProvider,
            ControllerBuilder controllerBuilder ) {
        this.controller = controller;
        this.methodForm = methodForm;
        this.validatorProvider = validatorProvider;
        this.controllerBuilder = controllerBuilder;
    }

    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param enumProperty Usado na configura��o de par�metros do tipo enum.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty, Class classType ){
        return addParameter( name, scope, enumProperty, null, null, null, 
                null, false, classType );
    }

    /**
     * Configura um novo parâmetro que não possui valor.
     *
     * @return Contrutor do parâmetro.
     */
    public ParameterBuilder addNullParameter(){
        return addParameter( null, null, null, null, null, null,
                null, false, null );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param temporalProperty Usado na configura��o de datas.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, String temporalProperty, Class classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, 
                temporalProperty, null, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param type Faz o processamento do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, Type type ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type, null, false, type.getClassType() );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param enumProperty Usado na configura��o de par�metros do tipo enum.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, EnumerationType enumProperty, Class classType ){
        return addParameter( name, ScopeType.REQUEST, enumProperty, null, null,
                null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, Class classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param temporalProperty Usado na configura��o de datas.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, String temporalProperty, Class classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL,
                temporalProperty, null, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param type Faz o processamento do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, Type type ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type, null, false, type.getClassType() );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameterMapping( String mapping, Class classType ){
        return addParameter( null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, null, false, classType );
    }

    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameterMapping( String name, String mapping, Class classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameterMapping( String name, String mapping, ScopeType scope, Class classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, null, false, classType );
    }
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, Class classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, null, false, classType );
    }

    /**
     * Constr�i um novo par�metro.
     *
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( Class classType ){
        String beanName = 
                this.methodForm.getName()+"#"+this.methodForm.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, classType);

        this.addParameterMapping(beanName, classType);
        return bb;
    }

    /**
     * Constrói um novo parâmetro.
     *
     * @param classType Tipo do parâmetro.
     * @param beanType Tipo do bean.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( Class classType, Class beanType ){
        String beanName =
                this.methodForm.getName()+"#"+this.methodForm.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, beanType);

        this.addParameterMapping(beanName, classType);
        return bb;
    }

    /**
     * Configura um novo par�metro.
     *
     * @param classType Tipo do par�metro.
     * @param value Valor do Par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addStaticParameter( Class classType, Object value ){
        return addParameter( null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, value, false, classType );
    }

    /**
     * Configura um novo par�metro.
     * 
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param enumProperty Usado na configura��o de par�metros do tipo enum.
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param temporalProperty Usado na configura��o de datas.
     * @param type Faz o processamento do par�metro.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type type, Object value,
            boolean nullable, Class classType ){

        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;
        
        //if( methodForm.getParameters().size() > methodForm.getParametersType().size() )
        //    throw new BrutosException( "index > " + methodForm.getParametersType().size() );
        
        /*Class classType = methodForm.
                    getParametersType().get( methodForm.getParamterSize() );*/

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();

        useBean.setNome( name );
        useBean.setScopeType( scope );
        useBean.setValidate( validatorProvider.getValidator( validatorConfig ) );
        useBean.setStaticValue(value);
        useBean.setNullable(nullable);
        
        if( mapping != null ){
            if( controller.getMappingBeans().containsKey( mapping ) )
                useBean.setMapping( controller.getMappingBean( mapping ) );
            else
                throw new BrutosException( "mapping name " + mapping + " not found!" );
                
        }
        //else
        if( type != null ){
            useBean.setType( type );
            if( classType != null &&
                    !classType.isAssignableFrom( useBean.getType().getClassType() ) )
                throw new BrutosException( 
                        "expected " + classType.getName() + " found " +
                        type.getClassType().getName() );
        }
        else
        if(classType != null){
            try{
                /*useBean.setType( Types.getType( methodForm.getGenericParameterType( methodForm.getParamterSize() ), enumProperty, temporalProperty ) );*/
                useBean.setType( Types.getType( classType ));
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException( 
                        String.format( "%s.%s(...) index %d : %s" ,
                            methodForm.getMethod().getDeclaringClass().getName(),
                            methodForm.getMethod().getName(),
                            methodForm.getParamterSize(),
                            e.getMessage() ) );
            }
            
            if( useBean.getType() == null )
                throw new UnknownTypeException( classType.getName() );
        }

        ParameterMethodMapping pmm = new ParameterMethodMapping();
        pmm.setBean( useBean );
        pmm.setParameterId( 0 );
        
        methodForm.addParameter( pmm );
        return new ParameterBuilder( validatorConfig );
    }

    /**
     * Intercepta e atribui uma identifica��o a uma determinada exce��o. O
     * objeto resultante da exce��o pode ser usando na vis�o.
     *
     * @param target Exce��o alvo.
     * @param id Identifica��o.
     * @return Contrutor do controlador.
     */
    public ActionBuilder addThrowable( Class target, String id ){
        return addThrowable( target, null, id, DispatcherType.FORWARD );
    }

    /**
     * Intercepta e atribui uma identifica��o a uma determinada exce��o. O
     * objeto resultante da exce��o pode ser usando na vis�o.
     *
     * @param target Exce��o alvo.
     * @param view Vis�o. Se omitido, ser� usada a vis�o da a��o.
     * @param id Identifica��o.
     * @param dispatcher Modo como ser� direcionado o fluxo para a vis�o.
     * @return Contrutor do controlador.
     */
    public ActionBuilder addThrowable( Class target, String view, String id, DispatcherType dispatcher ){
        view = view == null || view.replace( " ", "" ).length() == 0? null : view;
        id = id == null || id.replace( " ", "" ).length() == 0? null : id;

        if( target == null )
            throw new BrutosException( "target is required: " + controller.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(id);
        thr.setTarget(target);
        thr.setUri(view);
        thr.setRedirect( false );
        thr.setDispatcher(dispatcher);
        methodForm.setThrowsSafe(thr);
        return this;
    }

}
