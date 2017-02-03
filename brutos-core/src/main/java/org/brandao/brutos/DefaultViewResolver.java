package org.brandao.brutos;

import org.brandao.brutos.mapping.StringUtil;

public class DefaultViewResolver extends AbstractViewResolver {

	private String getPrefix(Class controllerType) {
		String controllerName = controllerType.getSimpleName();
		// controllerName = controllerName.replaceAll("Controller$", "");
		controllerName = controllerName.toLowerCase();

		String resolvedView = this.getSeparator();
		resolvedView += controllerName;
		return resolvedView;
	}

	private String getPrefix(Class controllerType, String actionExecutor) {
		String resolvedView = this.getPrefix(controllerType);
		if (!StringUtil.isEmpty(actionExecutor)) {
			resolvedView += this.getSeparator();
			resolvedView += actionExecutor.toLowerCase();
		}
		return resolvedView;
	}

	public String getControllerView(Class controllerType, String view) {
		String resolvedView = this.getPrefix();
		resolvedView += this.getPrefix(controllerType);
		resolvedView += this.getSeparator();
		resolvedView += view == null ? this.getIndexName() : view;
		resolvedView += this.getSuffix();
		return resolvedView;
	}

	public String getActionView(Class controllerType, String actionExecutor,
			String view) {
		String resolvedView = this.getPrefix();
		resolvedView += this.getPrefix(controllerType, actionExecutor);
		resolvedView += this.getSeparator();
		resolvedView += view == null ? this.getIndexName() : view;
		resolvedView += this.getSuffix();
		return resolvedView;
	}

	public String getExceptionView(Class controllerType, String actionExecutor,
			Class exceptionType, String view) {
		String resolvedView = this.getPrefix();
		resolvedView += this.getPrefix(controllerType, actionExecutor);
		resolvedView += this.getSeparator();
		resolvedView += view == null ? exceptionType.getSimpleName()
				.toLowerCase() : view;
		resolvedView += this.getSuffix();
		return resolvedView;
	}

	public String getExceptionView(Class controllerType, Class exceptionType,
			String view) {
		String resolvedView = this.getPrefix();
		resolvedView += this.getPrefix(controllerType);
		resolvedView += this.getSeparator();
		resolvedView += view == null ? exceptionType.getSimpleName()
				.toLowerCase() : view;
		resolvedView += this.getSuffix();
		return resolvedView;
	}

}
