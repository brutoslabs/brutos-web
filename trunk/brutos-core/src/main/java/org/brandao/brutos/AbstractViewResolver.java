package org.brandao.brutos;

public abstract class AbstractViewResolver implements ViewResolver {

	private ApplicationContext context;

	public String getView(ControllerBuilder controllerBuilder,
			ActionBuilder actionBuilder, Class<?> exception, String view) {

		String autoResolver = this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_AUTO,
				BrutosConstants.DEFAULT_VIEW_RESOLVER);

		if (!autoResolver.toLowerCase().equals("true"))
			return view;

		if (exception != null) {
			if (actionBuilder != null) {
				return this.getExceptionView(controllerBuilder.getClassType(),
						actionBuilder.getExecutor(), exception, view);
			} else {
				return this.getExceptionView(controllerBuilder.getClassType(),
						exception, view);
			}
		} else if (actionBuilder != null) {
			return this.getActionView(controllerBuilder.getClassType(),
					actionBuilder.getExecutor(), view);
		} else {
			return this.getControllerView(controllerBuilder.getClassType(),
					view);
		}
	}

	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public String getPrefix() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_PREFIX,
				BrutosConstants.DEFAULT_PREFIX_VIEW);
	}

	public String getSuffix() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_SUFFIX,
				BrutosConstants.DEFAULT_SUFFIX_VIEW);
	}

	public String getIndexName() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_INDEX,
				BrutosConstants.DEFAULT_INDEX_VIEW);
	}

	public String getSeparator() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_SEPARATOR,
				BrutosConstants.DEFAULT_SEPARATOR_VIEW);
	}

}
