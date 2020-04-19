package io.shiftleft.semanticcpg;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Doc {
  public String msg();
  public String longMsg() default "";
  public String example() default "";
}
