package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.{CpgFactory, LanguageFrontend}
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.passes.dataflows._
import io.shiftleft.passes.dataflows.steps.DataFlowObject


class CDataFlowTests extends WordSpec with Matchers {
  val cpgFactory = new CpgFactory(LanguageFrontend.Fuzzyc)

  "Example test setup." in {
    val cpg = cpgFactory.buildCpg(
      """
        |void method(int x) {
        |}
      """.stripMargin
    )
    val foo = cpg.method.name("method").l
    foo.size shouldBe 1
  }

  "Test 1" in {
    val cpg = cpgFactory.buildCpg(
      """
        |
        | void read_all_internal_bad_flows(FILE *fd, int mode)
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
        |
        | int main()
        | {
        |     // open self
        |     FILE * fd;
        |     fd = fopen("bad_reads.c", "r");
        |
        |     char buff[100];
        |     read_all_external(fd, 5, buff);
        |     read_all_external(fd, 200, buff);
        |
        |     read_size_external_buffer_internal(fd, getSize());
        |
        |     return 0;
        | }
        |
      """.stripMargin
    )

    val source = cpg.call.name("sz")
    val sink =  cpg.call.name("read")

    sink.reachableBy(source)
  }
}
