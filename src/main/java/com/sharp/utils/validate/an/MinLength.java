package com.sharp.utils.validate.an;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MinLength {
    int value();

    String msg() default "";
}
