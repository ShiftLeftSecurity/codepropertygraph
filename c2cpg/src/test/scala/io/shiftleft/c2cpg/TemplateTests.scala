package io.shiftleft.c2cpg

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass
import org.scalatest.Inside
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TemplateTests extends AnyWordSpec with Matchers with Inside {

  private object TemplateFixture {
    def apply(code: String)(f: Cpg => Unit): Unit = {
      File.usingTemporaryDirectory("c2cpgtest") { dir =>
        val file = dir / "file1.cpp"
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

  "Templates" should {

    "be correct for class templates" in TemplateFixture("""
        |template<class T> class X;
        |template<typename A, typename B> class Y;
        |using A = X<int>;
        |using B = Y<int, char>;
        |""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(x, y, a, b) =>
          x.name shouldBe "X"
          x.fullName shouldBe "X"
          x.aliasTypeFullName shouldBe Some("X<T>")
          y.name shouldBe "Y"
          y.fullName shouldBe "Y"
          y.aliasTypeFullName shouldBe Some("Y<A,B>")
          a.name shouldBe "A"
          a.fullName shouldBe "A"
          a.aliasTypeFullName shouldBe Some("X<int>")
          b.name shouldBe "B"
          b.fullName shouldBe "B"
          b.aliasTypeFullName shouldBe Some("Y<int,char>")
      }
    }

    "be correct for struct templates" in TemplateFixture("""
        |template<typename A, typename B> struct Foo;
        |""".stripMargin) { cpg =>
      inside(cpg.typeDecl.l) {
        case List(foo) =>
          foo.name shouldBe "Foo"
          foo.fullName shouldBe "Foo"
          foo.aliasTypeFullName shouldBe Some("Foo<A,B>")
      }
    }

    "be correct for function templates" in TemplateFixture("""
       |template<class T, class U>
       |void x(T a, U b) {};
       |
       |template<class T, class U>
       |void y(T a, U b);
       |""".stripMargin) { cpg =>
      inside(cpg.method.l) {
        case List(x, y) =>
          x.name shouldBe "x"
          x.fullName shouldBe "x"
          x.signature shouldBe "void x<T,U> (T,U)"
          y.name shouldBe "y"
          y.fullName shouldBe "y"
          y.signature shouldBe "void y<T,U> (T,U)"
      }
    }

  }

}
