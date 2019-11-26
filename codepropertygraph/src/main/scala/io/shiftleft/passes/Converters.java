package io.shiftleft.passes;

import java.util.List;

public class Converters {

  /**
   * Convert a scala sequence into a Java list
   * */
  public static <T> List<T> toJava(scala.collection.Seq<T> seq) {
    return scala.collection.JavaConverters.seqAsJavaList(seq);
  }

}
