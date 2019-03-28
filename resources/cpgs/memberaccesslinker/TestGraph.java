package io.shiftleft.testcode.memberaccesslinker;

public class TestGraph {
  static class Base {
    int aaa;
  }

  static class Derived extends Base {
  }

  void method(int x) {
    Derived obj = new Derived();
    obj.aaa = x;
  }
}
