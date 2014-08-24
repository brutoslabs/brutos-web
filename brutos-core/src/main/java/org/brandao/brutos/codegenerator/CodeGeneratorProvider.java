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


package org.brandao.brutos.codegenerator;

import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.proxy.ProxyFactory;

/**
 * Responsável por gerenciar e prover as fábricas de proxy da aplicação. 
 * Ele possui um método que disponibiliza e cria quando necessário a fábrica 
 * de proxy de uma determinada entidade.
 * <p>O método {@link #getProvider(java.util.Properties)} é responsável por
 * criar o geador de código a partir da configuração da aplicação.
 * O gerador de código é definido no início da aplicação por meio
 * da propriedade <code>org.brandao.brutos.proxy.provider</code>. A implementação
 * padrão é {@link JavassistCodeGeneratorProvider}</p>
 * <p>O método {@link #getProxyFactory(java.lang.Class)} é responsável por retornar
 * e criar, quando necessário, a fábrica de proxy de uma determinada entidade.
 * Ela é criada na primeira tentativa de se obtê-la. Sendo lançada
 * uma exceção, caso ocorra algum problema na sua criação.</p>
 * 
 * @author Brandao
 */
public abstract class CodeGeneratorProvider {

    /**
     * Obtém uma instância do gerador de códigos.
     * @param properties Configuração da Aplicação.
     * @return Gerador de códigos.
     */
    public static CodeGeneratorProvider getProvider( Properties properties ){
        
        Logger logger = LoggerProvider
                .getCurrentLoggerProvider()
                    .getLogger(CodeGeneratorProvider.class.getName());
        
        String providerName =
                properties
                    .getProperty(
                        BrutosConstants.PROXY_PROVIDER_CLASS,
                        BrutosConstants.DEFAULT_PROXY_PROVIDER_CLASS);
        CodeGeneratorProvider provider = null;

        logger.info("CodeGenerator provider: " + providerName);
        
        try{
            Class providerClass = ClassUtil.get(providerName);
            provider = (CodeGeneratorProvider)ClassUtil.getInstance(providerClass);
        }
        catch( ClassNotFoundException e ){
            throw new BrutosException( e );
        }
        catch( InstantiationException e ){
            throw new BrutosException( e );
        }
        catch( IllegalAccessException e ){
            throw new BrutosException( e );
        }

        return provider;
    }

    /**
     * Obtém e instancia quando necessário a fabrica de proxy de uma determinada
     * entidade.
     * @param classEntity Entidade.
     * @return Fábrica de proxy da entidade.
     * @throws BrutosException Lançada se ocorrer algum problema ao tentar 
     * obter ou criar a fábrica de proxy da entidade
     */
    public abstract ProxyFactory getProxyFactory( Class classEntity )
            throws BrutosException;
    
}
