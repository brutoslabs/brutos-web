package org.brandao.brutos.mapping;

import java.util.Collection;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.validator.ValidatorException;

public class CollectionBean extends Bean {

	private DependencyBean collection;

	public CollectionBean(Controller controller, Bean parent) {
		super(controller, parent);
	}

	public void setCollection(DependencyBean collection) {
		this.collection = collection;
	}

	public DependencyBean getCollection() {
		return this.collection;
	}

	protected Object get(String prefix, long index,
			ValidatorException exceptionHandler) {

		if (collection != null)
			return collection.getValue(prefix, index, exceptionHandler, null);
		else
			throw new MappingException(String.format(
					"element of the collection is not defined: %s",
					new Object[] { this.getName() }));
	}

	public Object getValue(boolean force) {
		return getValue(null, null, -1, null, force);
	}

	public Object getValue(Object instance) {
		return getValue(instance, null, -1, null, false);
	}

	public Object getValue() {
		return getValue(null);
	}

	public Object getValue(Object instance, String prefix, long otherIndex,
			ValidatorException exceptionHandler, boolean force) {
		try {

			ValidatorException vex = new ValidatorException();

			instance = getInstance(instance, prefix, otherIndex, vex, force);
			Collection collectionBean = (Collection) instance;

			long index = 0;
			Object beanInstance;

			while ((beanInstance = get(prefix, index, vex)) != null) {
				collectionBean.add(beanInstance);
				index++;
			}

			if (!collectionBean.isEmpty() || force) {
				if (exceptionHandler == null) {
					if (!vex.getCauses().isEmpty())
						throw vex;
					else
						return collectionBean;
				} else {
					exceptionHandler.addCauses(vex.getCauses());
					return collectionBean;
				}
			} else
				return null;

		} catch (ValidatorException e) {
			throw e;
		} catch (BrutosException e) {
			throw e;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected Object getInstance(Object instance, String prefix, long index,
			ValidatorException exceptionHandler, boolean force)
			throws InstantiationException, IllegalAccessException {

		if (instance == null) {
			instance = super.getValue(instance, prefix, index,
					exceptionHandler, force);
		}

		return instance;
	}

	public boolean isBean() {
		return false;
	}

	public boolean isCollection() {
		return true;
	}

	public boolean isMap() {
		return false;
	}

}
