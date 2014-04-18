package de.ahaus.dennis.javautils.impl.junit.annotations;

/**
 * @author Dennis Ahaus
 * 
 */
@SuppressWarnings("serial")
public class MethodNotUnderTestException extends RuntimeException {

	public MethodNotUnderTestException() {
		super();
	}

	public MethodNotUnderTestException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MethodNotUnderTestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MethodNotUnderTestException(String arg0) {
		super(arg0);
	}

	public MethodNotUnderTestException(Throwable arg0) {
		super(arg0);
	}

}
