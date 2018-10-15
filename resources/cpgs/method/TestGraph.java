package io.shiftleft.testcode.method;

public class TestGraph {
  public String methodWithLiteral() {
    return "myLiteral";
  }

  public void multipleTopLevelExpressionMethod() {
    someFunction("FOO");
    someFunction("BAR");
  }

  public void someFunction(String str) {

  }

  public int methodForCfgTest(int aaa) {
    int temp = aaa + 1;
    return temp;
  }
}
