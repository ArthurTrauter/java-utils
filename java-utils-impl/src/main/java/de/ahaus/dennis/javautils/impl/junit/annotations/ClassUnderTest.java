package de.ahaus.dennis.javautils.impl.junit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Assert;

/**
 * This annotation is used to give the test coverage framework the information
 * which class is tested by the unit test.
 * <p>
 * The test coverage framework need the information which class is under test to
 * checkout which methods exists in class under test. The existing methods will
 * be scaned and saved to internal storage and later on checked against covered
 * methods existing in the testing unit.
 * <p>
 * Following parameters control the behavior of the test coverage execution:
 * <p>
 * {@link #value()} - This is the class which is under test. When you have a
 * class {@code YourService} and a testing unit {@code YourServiceTest} then the
 * annotation of YourServiceTest has to be the following:
 * 
 * <pre>
 * <code>
 * {@literal @}ClassUnderTest(<b>value=YourService.class</b>)
 * public class YourServiceTest{
 * ...
 * } 
 * </code>
 * </pre>
 * 
 * <p>
 * {@link #onFailBehavior()} - This parameter controls the behavior of test
 * failure. Means if the test coverage failure occurs should the runtime
 * environment be informed by an {@link Assert#fail()} or by a throwing
 * exception? For detailed documentation about the options see
 * {@link OnFailBehavior}.
 * <p>
 * {@link #methods()} - This parameter controls the behavior which methods of
 * the class under test should be scanned to test coverage check. Means that you
 * can control if all methods are scanned (inclusive those from parent classes)
 * or just the declared ones on the class under test. For detailed documentation
 * about the options see {@link MethodType}.
 * 
 * @author Dennis Ahaus
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassUnderTest {

	/**
	 * @return Returns the class which is under test. This is the class which
	 *         methods are scanned by the test framework to check the method
	 *         coverage. In other words this class holds the methods which has
	 *         to be covered by the unit test
	 */
	Class<?> value();

	/**
	 * @return Returns the type of behavior. The default is
	 *         {@link OnFailBehavior#JUNIT_ASSERT}
	 */
	OnFailBehavior onFailBehavior() default OnFailBehavior.JUNIT_ASSERT;

	/**
	 * @return Returns the type of methods to be scanned by the framework. The
	 *         default is {@link MethodType#DECLARED}
	 */
	MethodType methods() default MethodType.DECLARED;

}
