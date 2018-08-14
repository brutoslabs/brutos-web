package org.brandao.brutos.annotation.web.helper;

import junit.framework.TestCase;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.annotation.web.WebActionStrategyType;
import org.brandao.brutos.web.HttpStatus;
import org.brandao.brutos.web.WebResultAction;

public class ResponseErrorTestHelper {

	public static class Values{
		
	}

	public static class Entities{
		
	}
	
	public static class Controllers{
		
		/* Configuração padrão */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class DefaultConfigController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração padrão */
		
		@ResponseError(code=HttpStatus.NOT_FOUND, target=NullPointerException.class)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ControllerLevelController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração de uma exceção */
		
		@ResponseError(
				code=HttpStatus.BAD_REQUEST,
				rendered=true,
				view="exp", 
				target=NullPointerException.class
		)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ControllerLevelExceptionWithViewController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/*---- Action level----*/
		
		
		/* Configuração padrão */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class DefaultConfigActionController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração padrão */
		
		@ResponseError(code=HttpStatus.BAD_REQUEST, target=NullPointerException.class)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ActionLevelController{
			
			@Action("/action")
			@View("view")
			@ResponseError(code=HttpStatus.NOT_FOUND, target=NullPointerException.class)
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração de uma exceção */
		
		@ResponseError(
				code=HttpStatus.CONFLICT,
				rendered=true,
				view="xxx", 
				target=NullPointerException.class
			)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ActionLevelExceptionWithViewController{
			
			@Action("/action")
			@View("view")
			@ResponseError(
					code=HttpStatus.BAD_REQUEST,
					rendered=true,
					view="exp", 
					target=NullPointerException.class
			)
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Exceção delegada a um método */
		
		/* configuração padrão */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodDefaultConfigController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError(code=HttpStatus.BAD_REQUEST, target=NullPointerException.class)
			public void npeException(){
				
			}
		}

		/* definindo view */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodAndViewController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError(code=HttpStatus.BAD_REQUEST, rendered=true, view="npe", target=NullPointerException.class)
			public void npeException(){
				
			}
		}

		/* definindo view com anotação*/
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodAndViewAnnotationController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError(code=HttpStatus.BAD_REQUEST, target=NullPointerException.class)
			@View("npe")
			public void npeException(@Basic(bean="exception")Throwable exception){
				TestCase.assertNotNull(exception);
			}
		}

		/* multiplas exceções em nível de controlador */

		@ActionStrategy(WebActionStrategyType.DETACHED)
		@ResponseError(code=HttpStatus.BAD_REQUEST, 
		target={IllegalStateException.class,NullPointerException.class})
		public static class ExceptionAliasControllerLevelController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

		}

		/* multiplas exceções em nível de ação */

		@ActionStrategy(WebActionStrategyType.DETACHED)
		@ResponseError(code=HttpStatus.CONFLICT, 
		target={IllegalStateException.class,NullPointerException.class})
		public static class ExceptionAliasActionLevelController{
			
			@Action("/action")
			@View("view")
			@ResponseError(code=HttpStatus.BAD_REQUEST, 
			target={IllegalStateException.class,NullPointerException.class})
			public void action(){
				throw new NullPointerException();
			}

		}
		
		/* método com multiplas exceções */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodAndAliasAnnotationController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError(code=HttpStatus.BAD_REQUEST, 
					target={IllegalStateException.class,NullPointerException.class})
			public void npeException(@Basic(bean="exception")Throwable exception){
				TestCase.assertNotNull(exception);
			}
		}

		/* método com multiplas exceções e status da resposta */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodAndStatusAnnotationController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError({IllegalStateException.class,NullPointerException.class})
			@ResponseStatus(HttpStatus.BAD_REQUEST)
			public void npeException(@Basic(bean="exception")Throwable exception){
				TestCase.assertNotNull(exception);
			}
		}
		
		
		/* método com multiplas exceções e montagem da resposta */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodAndBuildResponseController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError({IllegalStateException.class,NullPointerException.class})
			public WebResultAction npeException(WebResultAction result){
				result.setResponseStatus(HttpStatus.BAD_REQUEST);
				result.setReason("my bad request");
				return result;
			}
		}

		/* método com multiplas exceções e montagem da resposta */
		
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ExceptionWithMethodAndBuildResponseWithViewController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}

			@ResponseError({IllegalStateException.class,NullPointerException.class})
			public WebResultAction npeException(@Basic(bean="exception")Throwable exception, WebResultAction result){
				result
					.setResponseStatus(HttpStatus.BAD_REQUEST)
					.setView("exp")
					.add("ex", exception);
				return result;
			}
		}
		
	}
	
}
