package de.ahaus.dennis.javautils.impl.junit;

import java.util.List;

public abstract class HostProviderAdapter<T> implements HostProvider<T> {

	@Override
	public final HostProvider<T> iterate(List<T> elements, Visitor<T> visitor) {

		for (T elem : elements) {
			visitor.before(elem);
			visitor.intercept(elem);
			visitor.after(elem);
		}

		return this;
	}

	@Override
	public final HostProvider<T> iterate(Visitor<T> visitor) {

		return iterate(getElements(), visitor);
	}

	protected abstract List<T> getElements();

}
