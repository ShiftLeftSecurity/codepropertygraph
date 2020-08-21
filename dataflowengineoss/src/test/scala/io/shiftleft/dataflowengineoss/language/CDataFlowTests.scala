package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.generated.nodes.CfgNode
import io.shiftleft.semanticcpg.language._

class CDataFlowTests1 extends DataFlowCodeToCpgSuite {

  override val code =
    """
      |
      | void flows1(FILE *fd, int mode) {
      |     char buff[40];
      |
      |     int sz = 0;
      |     if (mode == 1) sz = 20;
      |     if (mode == 2) sz = 200;
      |     if (mode == 3) sz = 41;
      |     if (mode == 5) sz = -5;
      |
      |     read(fd, buff, sz);
      | }
      """.stripMargin

  "Test 1: flow from function call read to multiple versions of the same variable" in {

    def source = cpg.identifier.name("sz")

    def sink = cpg.call.name("read")

    def flows = sink.reachableByFlows(source)

    flows.foreach { x =>
      println("flow ======")
      x.elements.foreach {
        case n: CfgNode => println(n.code)
        case _          => println("other")
      }
    }

    cpg.method("flows1").plotDotDdg

    flows.size shouldBe 6

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("sz = 200", 8),
          ("read(fd, buff, sz)", 12)
        ),
        List[(String, Option[Integer])](
          ("sz = -5", 10),
          ("read(fd, buff, sz)", 12)
        ),
        List[(String, Option[Integer])](
          ("sz = 41", 9),
          ("read(fd, buff, sz)", 12)
        ),
        List[(String, Option[Integer])](
          ("sz = 0", 6),
          ("read(fd, buff, sz)", 12)
        ),
        List[(String, Option[Integer])](
          ("sz = 20", 7),
          ("read(fd, buff, sz)", 12)
        ),
        List[(String, Option[Integer])](
          ("read(fd, buff, sz)", 12)
        )
      )

    // pretty printing for flows
    val flowsPretty = flows.p.mkString
    flowsPretty.should(include("sz = 20"))
    flowsPretty.should(include("read(fd, buff, sz)"))
    val tmpSourceFile = flows.head.elements.head.method.filename
    flowsPretty.should(include(tmpSourceFile))
  }
}

class CDataFlowTests2 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        |struct node {
        | int value;
        | struct node *next;
        |};
        |void free_list(struct node *head) {
        | struct node *q;
        | for (struct node *p = head; p != NULL; p = q) {
        | q = p->next;
        | free(p);
        | }
        |}
      """.stripMargin

  "Test 2: flow with pointers" in {
    implicit val callResolver = NoResolve
    val source = cpg.identifier
    val sink = cpg.method.name("free").parameter.argument
    val flows = sink
      .reachableByFlows(source)
      .l
      .map(flowToResultPairs)
      .distinct

    flows.size shouldBe 5

    flows.toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("*p = head", 8),
          ("p != NULL", 8),
          ("free(p)", 10)
        ),
        List[(String, Option[Integer])](
          ("q = p->next", 9),
          ("p = q", 8),
          ("p != NULL", 8),
          ("free(p)", 10)
        ),
        List[(String, Option[Integer])](
          ("p = q", 8),
          ("p != NULL", 8),
          ("free(p)", 10)
        ),
        List[(String, Option[Integer])](
          ("p != NULL", 8),
          ("free(p)", 10)
        ),
        List[(String, Option[Integer])](
          ("free(p)", 10)
        )
      )
  }
}

class CDataFlowTests3 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | int method(int y){
        |  int a = 10;
        |  if (a < y){
        |    foo(a);
        |  }
        | }
      """.stripMargin

  "Test 3: flow from function call argument" in {
    implicit val callResolver = NoResolve
    val source = cpg.identifier.name("a")
    val sink = cpg.method.name("foo").parameter.argument
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 3

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("a = 10", 3),
          ("a < y", 4),
          ("foo(a)", 5)
        ),
        List[(String, Option[Integer])](
          ("a < y", 4),
          ("foo(a)", 5)
        ),
        List[(String, Option[Integer])](
          ("foo(a)", 5)
        ),
      )
  }
}

class CDataFlowTests4 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | void flow(void) {
        |   int a = 0x37;
        |   int b=a;
        |   int c=0x31;
        |   int z = b + c;
        |   z++;
        |   int* p = &z;
        |   int x = z;
        | }
      """.stripMargin

  "Test 4: flow chains from x to a" in {
    val source = cpg.identifier.name("a")
    val sink = cpg.identifier.name("x")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2
    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("a = 0x37", 3),
          ("b=a", 4),
          ("b + c", 6),
          ("z = b + c", 6),
          ("z++", 7),
          ("x = z", 9)
        ),
        List[(String, Option[Integer])](
          ("b=a", 4),
          ("b + c", 6),
          ("z = b + c", 6),
          ("z++", 7),
          ("x = z", 9)
        )
      )
  }
}

class CDataFlowTests5 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | int flow(int a){
        |   int z = a;
        |   int b = z;
        |
        |   return b;
        | }
      """.stripMargin

  "Test 5: flow from method return to a" in {
    val source = cpg.identifier.name("a")
    val sink = cpg.methodReturn
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 1

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("z = a", 3),
          ("b = z", 4),
          ("return b;", 6),
          ("RET", 2)
        ))
  }
}

class CDataFlowTests6 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | int nested(int a){
        |   int x;
        |   int z = 0x37;
        |   if(a < 10){
        |     if( a < 5){
        |       if(a < 2){
        |          x = a;
        |       }
        |     }
        |   } else
        |     x = z;
        |
        |   return x;
        | }
      """.stripMargin

  "Test 6: flow with nested if-statements from method return to a" in {
    val source = cpg.call.code("a < 10").argument.code("a")
    val sink = cpg.methodReturn
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 1

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("a < 10", Some(5)),
          ("a < 5", Some(6)),
          ("a < 2", Some(7)),
          ("x = a", 8),
          ("return x;", 14),
          ("RET", 2)
        ))
  }
}

class CDataFlowTests7 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | int nested(int a){
        |   int x;
        |   int z = 0x37;
        |   if(a < 10){
        |     if( a < 5){
        |       if(a < 2){
        |          x = a;
        |       }
        |     }
        |   } else
        |     x = z;
        |
        |   return x;
        | }
      """.stripMargin

  "Test 7: flow with nested if-statements from method return to x" in {
    val source = cpg.identifier.name("x")
    val sink = cpg.methodReturn
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 3

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("x = z", 12),
          ("return x;", 14),
          ("RET", 2)
        ),
        List[(String, Option[Integer])](
          ("x = a", 8),
          ("return x;", 14),
          ("RET", 2)
        ),
        List[(String, Option[Integer])](
          ("return x;", 14),
          ("RET", 2)
        )
      )
  }
}

class CDataFlowTests8 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | void param(int x){
        |    int a = x;
        |    int b = a;
        |    int z = foo(b);
        |  }
      """.stripMargin

  "Test 8: flow chain from function argument of foo to a" in {
    implicit val callResolver = NoResolve
    val source = cpg.identifier.name("a")
    val sink = cpg.method.name("foo").parameter.argument
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2

    flows.map(flowToResultPairs).toSet shouldBe
      Set(List[(String, Option[Integer])](
            ("a = x", 3),
            ("b = a", 4),
            ("foo(b)", 5)
          ),
          List[(String, Option[Integer])](
            ("b = a", 4),
            ("foo(b)", 5)
          ))

  }
}

class CDataFlowTests9 extends DataFlowCodeToCpgSuite {
  override val code = """
                          | void param(int x){
                          |    int a = x;
                          |    int b = a;
                          |    int z = foo(b);
                          |  }
      """.stripMargin

  "Test 9: flow from function foo to a" in {
    val source = cpg.identifier.name("a")
    val sink = cpg.call.name("foo").argument(1)
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2

    flows.map(flowToResultPairs).toSet shouldBe
      Set(List[(String, Option[Integer])](
            ("a = x", 3),
            ("b = a", 4),
            ("foo(b)", 5)
          ),
          List[(String, Option[Integer])](
            ("b = a", 4),
            ("foo(b)", 5)
          ))
  }
}

class CDataFlowTests10 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | struct node {
        | int value1;
        | int value2;
        |};
        |
        |void test(void){
        |  int x = 10;
        |  struct node n;
        |  n.value1 = x;
        |  n.value2 = n.value1;
        |}
      """.stripMargin

  "Test 10: flow with member access in expression to identifier x" in {
    val source = cpg.identifier.name("x")
    val sink = cpg.call.code("n.value2")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("x = 10", 8),
          ("n.value1 = x", 10),
          ("n.value2 = n.value1", 11)
        ),
        List[(String, Option[Integer])](
          ("n.value1 = x", 10),
          ("n.value2 = n.value1", 11)
        )
      )
  }
}

class CDataFlowTests11 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | void flow(void) {
        |   int a = 0x37;
        |   int b=a;
        |   int c=0x31;
        |   int z = b + c;
        |   z++;
        |   int* p = &z;
        |   int x = z;
        | }
      """.stripMargin

  "Test 11: flow chain from x to literal 0x37" in {
    val source = cpg.literal.code("0x37")
    val sink = cpg.identifier.name("x")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 1

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("a = 0x37", 3),
          ("b=a", 4),
          ("b + c", 6),
          ("z = b + c", 6),
          ("z++", Some(7)),
          ("x = z", 9)
        ))
  }
}

class CDataFlowTests12 extends DataFlowCodeToCpgSuite {
  override val code =
    """
      | void flow(void) {
      |    int a = 0x37;
      |    int b = a;
      |    int z = b;
      |    z+=a;
      | }
       """.stripMargin

  "Test 12: flow with short hand assignment operator" in {
    val source = cpg.call.code("a = 0x37").argument(2)
    val sink = cpg.call.code("z\\+=a").argument(1)
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2

    flows.map(flowToResultPairs).toSet shouldBe
      Set(List[(String, Option[Integer])](
            ("a = 0x37", 3),
            ("b = a", 4),
            ("z = b", 5),
            ("z+=a", 6)
          ),
          List[(String, Option[Integer])](
            ("a = 0x37", 3),
            ("z+=a", 6)
          ))
  }
}

class CDataFlowTests13 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | void flow(void) {
        |    int a = 0x37;
        |    int b = a;
        |    int z = b;
        |    z+=a;
        |    int w = z;
        | }
      """.stripMargin

  "Test 13: flow after short hand assignment" in {
    val source = cpg.call.code("a = 0x37").argument(1)
    val sink = cpg.identifier.name("w")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("a = 0x37", 3),
          ("b = a", 4),
          ("z = b", 5),
          ("z+=a", 6),
          ("w = z", 7)
        ),
        List[(String, Option[Integer])](
          ("a = 0x37", 3),
          ("z+=a", 6),
          ("w = z", 7)
        )
      )
  }
}

class CDataFlowTests14 extends DataFlowCodeToCpgSuite {
  override val code =
    """
        | int main(int argc, char** argv){
        |    int x = argv[1];
        |    int y = x;
        |    int z = y;
        |
        |    return 0;
        | }
      """.stripMargin

  "Test 14: flow from identifier to method parameter" in {
    val source = cpg.method.parameter
    val sink = cpg.identifier.name("y")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 2

    flows.map(flowToResultPairs).toSet shouldBe
      Set(
        List[(String, Option[Integer])](
          ("main(int argc, char** argv)", 2),
          ("x = argv[1]", 3),
          ("y = x", 4),
          ("z = y", 5)
        ),
        List[(String, Option[Integer])](
          ("main(int argc, char** argv)", 2),
          ("x = argv[1]", 3),
          ("y = x", 4)
        )
      )
  }
}

class CDataFlowTests15 extends DataFlowCodeToCpgSuite {
  override val code =
    """
  void foo(bool x, void* y) {
    void* z =  x ? f(y) : g(y);
    return;
  }
      """.stripMargin

  "Test 15: conditional expressions (joern issue #91)" in {
    val source = cpg.method.parameter.name("y")
    val sink = cpg.identifier.name("z")
    val flows = sink.reachableByFlows(source).l

    flows.map(flowToResultPairs).foreach(println)

    flows.map(flowToResultPairs).toSet shouldBe Set(
      List[(String, Option[Integer])](
        ("foo(bool x, void* y)", 2),
        ("g(y)", 3),
        ("x ? f(y) : g(y)", 3),
        ("* z =  x ? f(y) : g(y)", 3)
      ),
      List[(String, Option[Integer])](
        ("foo(bool x, void* y)", 2),
        ("f(y)", 3),
        ("x ? f(y) : g(y)", 3),
        ("* z =  x ? f(y) : g(y)", 3)
      )
    )
  }
}
