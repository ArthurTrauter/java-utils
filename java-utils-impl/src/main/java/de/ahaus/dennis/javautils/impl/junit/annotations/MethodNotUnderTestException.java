package de.ahaus.dennis.javautils.impl.junit.annotations;

/**
 * This exception is thrown when when the test coverage framework checks the
 * test coverage and the result is a failure. When the parameter
 * {@link ClassUnderTest#onFailBehavior()} is set to
 * {@link OnFailBehavior#EXCEPTION} this execption will be thrown to inform the
 * runtime environment (normaly a unit test) that the coverage test fails.
 * 
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
