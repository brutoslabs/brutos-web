package org.brandao.brutos.annotation.web.helper;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.WebActionStrategyType;
import org.brandao.brutos.web.HttpStatus;

public class ResponseErrorsTestHelper {

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
		
		@ResponseErrors(code=HttpStatus.NOT_FOUND)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ControllerLevelController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração de uma exceção */
		
		@ResponseErrors(
			code=HttpStatus.NOT_FOUND,
			exceptions=@ResponseError(code=HttpStatus.BAD_REQUEST, target=NullPointerException.class)
		)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ControllerLevelExceptionController{
			
			@Action("/action")
			@View("view")
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração de uma exceção */
		
		@ResponseErrors(
			code=HttpStatus.NOT_FOUND,
			exceptions=
				@ResponseError(
					code=HttpStatus.BAD_REQUEST,
					rendered=true,
					view="exp", 
					target=NullPointerException.class
				)
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
		
		@ResponseErrors(code=HttpStatus.BAD_REQUEST)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ActionLevelController{
			
			@Action("/action")
			@View("view")
			@ResponseErrors(code=HttpStatus.NOT_FOUND)
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Exceção com status e vista */
		
		@ResponseErrors(code=HttpStatus.BAD_REQUEST)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ActionLevelWithStatusAndViewController{
			
			@Action("/action")
			@View("view")
			@ResponseErrors(code=HttpStatus.NOT_FOUND, view="npe")
			public void action(){
				throw new NullPointerException();
			}
			
		}
		
		/* Alteração da configuração de uma exceção */
		
		@ResponseErrors(
			code=HttpStatus.ALREADY_REPORTED,
			exceptions=@ResponseError(code=HttpStatus.CONFLICT, target=NullPointerException.class)
		)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ActionLevelExceptionController{
			
			@Action("/action")
			@View("view")
			@ResponseErrors(
					code=HttpStatus.NOT_FOUND,
					exceptions=@ResponseError(code=HttpStatus.BAD_REQUEST, target=NullPointerException.class)
				)
			public void action(){
				throw new NullPointerException();
			}
			
		}

		/* Alteração da configuração de uma exceção */
		
		@ResponseErrors(
			code=HttpStatus.BAD_GATEWAY,
			exceptions=
				@ResponseError(
					code=HttpStatus.CONFLICT,
					rendered=true,
					view="xxx", 
					target=NullPointerException.class
				)
		)
		@ActionStrategy(WebActionStrategyType.DETACHED)
		public static class ActionLevelExceptionWithViewController{
			
			@Action("/action")
			@View("view")
			@ResponseErrors(
					code=HttpStatus.NOT_FOUND,
					exceptions=
						@ResponseError(
							code=HttpStatus.BAD_REQUEST,
							rendered=true,
							view="exp", 
							target=NullPointerException.class
						)
				)
			public void action(){
				throw new NullPointerException();
			}
			
		}
		
	}
	
}
