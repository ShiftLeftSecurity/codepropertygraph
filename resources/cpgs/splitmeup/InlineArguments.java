package io.shiftleft.testcode.splitmeup;

import javax.xml.ws.Action;

class InlineArguments {

  private int classMember0;
  private int classMember1;

  private static int staticClassMember0;
  private static int staticClassMember1;

  static int add(@Deprecated int arg0, int arg1) {
    return arg0 + arg1;
  }

  @Deprecated
  void addLocal(int val0, int val1) {
    int ret;

    ret = add(val0 + val1, 3);

  }

  @Action(input = "in", output = "out")
  void addClassMembers() {
    int ret;

    classMember0 = 1;

    ret = add(classMember0 + classMember1, 3);
  }

  static void addStaticClassMembers() {
    int ret;

    ret = add(staticClassMember0 + staticClassMember1, 3);
  }

  static int methodWithCycle(int max) {
    int local = 0;
    for (int i = 0; i < max; i++) {
      local++;
    }
    addStaticClassMembers();
    return local;
  }
}
