package me.david.timbernocheat.checkbase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Disable {

    String reason() default "No Reason Provided";

}
