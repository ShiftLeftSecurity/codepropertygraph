package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.{CpgFactory, LanguageFrontend}
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.passes.dataflows._
import io.shiftleft.passes.dataflows.steps.{DataFlowObject, FlowPrettyPrinter}
import io.shiftleft.codepropertygraph.generated.nodes

class CDataFlowTests extends WordSpec with Matchers {
  val cpgFactory = new CpgFactory(LanguageFrontend.Fuzzyc)
  "Test 1" in {
    val cpg = cpgFactory.buildCpg(
      """
        |
        | void flows1(FILE *fd, int mode)
        | {
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
    )

    val source = cpg.identifier.name("sz")
    val sink = cpg.call.name("read")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 6
  }

  "Test 2" in {
    val cpg = cpgFactory.buildCpg(
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
    )
    val source = cpg.identifier
    val sink = cpg.call.name("free")
    val flows = sink.reachableByFlows(source).l

    flows.size shouldBe 6

    flows.maxBy(e => e.size).map(point => point.asInstanceOf[nodes.Call].code) shouldBe
          List[String](
            "*p = head",
            "p->next",
            "q = p->next",
            "p = q",
            "free(p)"
          )
  }

  "Test 3" in {
    val cpg = cpgFactory.buildCpg(
      """
        | void flow(void) {
        |   int a = 0x37;
        |   int b=a;
        |   int c=0x31;
        |   int z = b + c;
        |   z++;
        |   int p = &z;
        |   int x = z;
        | }
      """.stripMargin
    )

    val source = cpg.identifier
    val sink = cpg.identifier.name("z")
    val flows = sink.reachableByFlows(source).l
  }

  "Test 4" in {
    val cpg = cpgFactory.buildCpg(
    """
      | void flow(int a){
      |   int z = a;
      |   int b = z;
      |
      |   return b;
      | }
    """.stripMargin)

    val source = cpg.identifier.name("a")
    val sink = cpg.identifier.name("b")
    val flows = sink.reachableByFlows(source).l

    //println(sink.reachableByFlows(source).p)
  }

}
