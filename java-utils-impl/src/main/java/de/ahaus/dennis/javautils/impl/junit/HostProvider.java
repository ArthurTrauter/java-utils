package de.ahaus.dennis.javautils.impl.junit;

import java.util.List;

public interface HostProvider<T> {

	public HostProvider<T> iterate(List<T> elements, Visitor<T> visitor);

	public HostProvider<T> iterate(Visitor<T> visitor);

}
