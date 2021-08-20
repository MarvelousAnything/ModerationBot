package com.yoursole1.command.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    long MAX_PERMISSIONS = 2146958591;

    String prefix() default "!";
    String exampleText() default "";
    String helpText() default "";
    long permissions() default MAX_PERMISSIONS;
}
