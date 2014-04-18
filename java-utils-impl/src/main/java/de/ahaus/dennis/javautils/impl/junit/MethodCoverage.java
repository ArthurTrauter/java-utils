package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dennis Ahaus
 * 
 */
public class MethodCoverage {

	private Method coveredMethod;
	List<Method> coveredByMethods = new ArrayList<Method>();

	/**
	 * @param coveredMethod
	 */
	public MethodCoverage(Method coveredMethod) {
		super();
		this.coveredMethod = coveredMethod;
	}

	/**
	 * @return
	 */
	public Method getCoveredMethod() {
		return coveredMethod;
	}

	/**
	 * @return
	 */
	public List<Method> getCoveredByMethods() {
		return coveredByMethods;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getSimpleName());
		buf.append("[");
		buf.append("coveredMethod=" + getCoveredMethod().toString());
		buf.append("; coveredBy={");
		for (Method m : getCoveredByMethods()) {
			buf.append(m.toString() + "; ");
		}
		buf.append("}");
		buf.append("]");
		return buf.toString();
	}
}
