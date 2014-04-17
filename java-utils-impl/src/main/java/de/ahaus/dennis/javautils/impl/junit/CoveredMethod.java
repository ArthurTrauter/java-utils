package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CoveredMethod {

	Method coveredMethod;

	List<Method> coveredByMethods = new ArrayList<>();

	public CoveredMethod(Method coveredMethod) {
		super();
		this.coveredMethod = coveredMethod;
	}

	public Method getCoveredMethod() {
		return coveredMethod;
	}

	public void setCoveredMethod(Method coveredMethod) {
		this.coveredMethod = coveredMethod;
	}

	public List<Method> getCoveredByMethods() {
		return coveredByMethods;
	}

	public void setCoveredByMethods(List<Method> coveredByMethods) {
		this.coveredByMethods = coveredByMethods;
	}

}
