package de.ahaus.dennis.javautils.impl.junit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassUnderTest {

	Class<?> value();

	OnFailBehavior onFailBehavior() default OnFailBehavior.JUNIT_ASSERT;

	MethodType methods() default MethodType.DECLARED;

}
