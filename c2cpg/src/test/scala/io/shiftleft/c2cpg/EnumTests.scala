package io.shiftleft.c2cpg

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes.{Call, FieldIdentifier, Identifier}
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass
import org.scalatest.Inside
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EnumTests extends AnyWordSpec with Matchers with Inside {

  private object EnumFixture {
    def apply(code: String)(f: Cpg => Unit): Unit = {
      File.usingTemporaryDirectory("c2cpgtest") { dir =>
        val file = dir / "file1.c"
        file.write(code)

        val cpg = Cpg.emptyCpg
        val keyPool = new IntervalKeyPool(1001, 2000)
        val typesKeyPool = new IntervalKeyPool(2001, 3000)
        val filenames = List(file.path.toAbsolutePath.toString)
        val astCreationPass = new AstCreationPass(filenames, cpg, keyPool, Config())
        astCreationPass.createAndApply()
        new CfgCreationPass(cpg).createAndApply()
        new StubRemovalPass(cpg).createAndApply()
        new TypeNodePass(astCreationPass.usedTypes(), cpg, Some(typesKeyPool)).createAndApply()
        f(cpg)
      }
    }
  }

  "Enums" should {

    "be correct for simple enum" in EnumFixture("""
        |enum color
        |{
        |    red,
        |    yellow,
        |    green = 20,
        |    blue
        |};""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(color) =>
          color.name shouldBe "color"
          inside(color.member.l) {
            case List(red, yellow, green, blue) =>
              red.name shouldBe "red"
              yellow.name shouldBe "yellow"
              green.name shouldBe "green"
              blue.name shouldBe "blue"
          }
          inside(color.astChildren.isCall.code.l) {
            case List(greenInit) =>
              greenInit shouldBe "green = 20"
          }
      }
    }

    "be correct for simple enum typedef" in EnumFixture("""
                                                  |typedef enum color
                                                  |{
                                                  |    red,
                                                  |    yellow,
                                                  |    green = 20,
                                                  |    blue
                                                  |} C;""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(color) =>
          color.name shouldBe "C"
          color.aliasTypeFullName shouldBe Some("color")
          inside(color.astChildren.isMember.l) {
            case List(red, yellow, green, blue) =>
              red.name shouldBe "red"
              yellow.name shouldBe "yellow"
              green.name shouldBe "green"
              blue.name shouldBe "blue"
          }
          inside(color.astChildren.isCall.code.l) {
            case List(greenInit) =>
              greenInit shouldBe "green = 20"
          }
      }
    }

    "be correct for simple enum class" in EnumFixture("""
        |enum class altitude: char
        |{ 
        |     high='h',
        |     low='l', // C++11 allows the extra comma
        |}; """.stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(altitude) =>
          altitude.name shouldBe "altitude"
          inside(altitude.member.l) {
            case List(high, low) =>
              high.name shouldBe "high"
              high.typeFullName shouldBe "char"
              low.name shouldBe "low"
              low.typeFullName shouldBe "char"
          }
          inside(altitude.astChildren.isCall.code.l) {
            case List(highInit, lowInit) =>
              highInit shouldBe "high='h'"
              lowInit shouldBe "low='l'"
          }
      }
    }

    "be correct for simple enum with type" in EnumFixture("""
        enum smallenum: int
        |{
        |    a,
        |    b,
        |    c
        |};""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(smallenum) =>
          smallenum.name shouldBe "smallenum"
          inside(smallenum.member.l) {
            case List(a, b, c) =>
              a.name shouldBe "a"
              a.typeFullName shouldBe "int"
              b.name shouldBe "b"
              b.typeFullName shouldBe "int"
              c.name shouldBe "c"
              c.typeFullName shouldBe "int"
          }
      }
    }

    "be correct for anonymous enum" in EnumFixture("""
         |enum
         |{
         |    d,
         |    e,
         |    f
         |};""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(anon) =>
          anon.name shouldBe "anonymous_enum_0"
          inside(anon.member.l) {
            case List(d, e, f) =>
              d.name shouldBe "d"
              e.name shouldBe "e"
              f.name shouldBe "f"
          }
      }
    }

    "be correct for enum access" in EnumFixture("""
       |enum X: int
       |{
       |    a,
       |    b
       |};
       |int x = X::a;
       |""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(x) =>
          x.name shouldBe "X"
          inside(x.member.l) {
            case List(a, b) =>
              a.name shouldBe "a"
              a.typeFullName shouldBe "int"
              b.name shouldBe "b"
              b.typeFullName shouldBe "int"
          }
          inside(cpg.call.l) {
            case List(assign, ma) =>
              assign.code shouldBe "x = X::a"
              ma.code shouldBe "X::a"
              inside(ma.ast.l) {
                case List(call: Call, x: Identifier, a: FieldIdentifier) =>
                  call.name shouldBe Operators.fieldAccess
                  x.order shouldBe 1
                  a.order shouldBe 2
              }
          }
      }
    }

  }

}
