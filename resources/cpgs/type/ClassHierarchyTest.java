package io.shiftleft.testcode.type;

public class ClassHierarchyTest {

  interface Interface1 {

  }

  interface Interface2 {

  }

  static class Base {

  }

  static class Derived extends Base {

  }

  static class Derived2 extends Derived {

  }

  static class InterfaceImplementor implements Interface1, Interface2 {

  }
}
