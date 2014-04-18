package de.ahaus.dennis.javautils.impl.junit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dennis Ahaus
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassUnderTest {

	/**
	 * @return
	 */
	Class<?> value();

	/**
	 * @return
	 */
	OnFailBehavior onFailBehavior() default OnFailBehavior.JUNIT_ASSERT;

	/**
	 * @return
	 */
	MethodType methods() default MethodType.DECLARED;

}
