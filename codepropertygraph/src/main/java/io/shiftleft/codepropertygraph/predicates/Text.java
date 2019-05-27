package io.shiftleft.codepropertygraph.predicates;

import org.apache.tinkerpop.gremlin.process.traversal.P;

import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Text implements BiPredicate<Object, Object> {

  REGEX {
    @Override
    public boolean test(Object testValueObject, Object regexPatternObject) {
      if (!(regexPatternObject instanceof Pattern)) {
        throw new AssertionError("testValueObject is not of type String.");
      }

      if (!(testValueObject instanceof String)) {
        throw new AssertionError("testValueObject is not of type String.");
      }
      Pattern regexPattern = (Pattern) regexPatternObject;
      String testValue = (String) testValueObject;

      Matcher matcher = regexPattern.matcher(testValue);
      return matcher.matches();
    }
  };

  // TODO: delete once java traversals are gone
  public static P<String> textRegex(final String value) {
    return new P(Text.REGEX, Pattern.compile(value));
  }

  public static P<String> textRegexContains(final String value) {
    return new P(Text.REGEX, Pattern.compile(".*" + value + ".*"));
  }
}
