package org.brandao.brutos.annotation.configuration.web;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.configuration.BasicAnnotationConfig;
import org.brandao.brutos.annotation.web.ResponseError;

@Stereotype(
		target=
			Basic.class, 
		majorVersion=1,
		executeAfter = {
			Controller.class,
			Bean.class, 
			Action.class,
			ThrowSafe.class,
			ResponseError.class
		}
	)
public class WebBasicAnnotationConfig 
	extends BasicAnnotationConfig{

}
