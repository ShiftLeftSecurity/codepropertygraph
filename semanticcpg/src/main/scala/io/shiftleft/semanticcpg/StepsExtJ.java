package io.shiftleft.semanticcpg;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StepsExtJ {
  public Class nodeType();
}
