package io.shiftleft.c2cpg

import better.files.File
import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

//import io.shiftleft.codepropertygraph.generated.nodes._
//import io.shiftleft.semanticcpg.language._
//import overflowdb.traversal._

class NamespaceTests extends AnyWordSpec with Matchers {

  "Namespaces" should {

    "be correct for nested namespaces" in NamespaceFixture(
      """
        |namespace Q {
        |    namespace V {   // V is a member of Q, and is fully defined within Q
        |        class C { void m(); }; // C is a member of V and is fully defined within V
        |                               // C::m is only declared
        |        void f(); // f is a member of V, but is only declared here
        |    }
        | 
        |    void V::f() // definition of V's member f outside of V
        |                // f's enclosing namespaces are still the global namespace, Q, and Q::V
        |    {
        |        extern void h(); // This declares ::Q::V::h
        |    }
        | 
        |    void V::C::m() // definition of V::C::m outside of the namespace (and the class body)
        |                   // enclosing namespaces are the global namespace, Q, and Q::V
        |    {}
        |}
        |""".stripMargin) { cpg =>
      fail("Failed for: " + cpg)
    }

    "be correct for nested namespaces in C++17 style" in NamespaceFixture(
      """
        |namespace Q::V {
        |   class C { void m(); }; // C is a member of V and is fully defined within V
        |                               // C::m is only declared
        |   void f(); // f is a member of V, but is only declared here
        |}
        | 
        |void V::f() // definition of V's member f outside of V
        |            // f's enclosing namespaces are still the global namespace, Q, and Q::V
        |{
        |    extern void h(); // This declares ::Q::V::h
        |}
        | 
        |void V::C::m() // definition of V::C::m outside of the namespace (and the class body)
        |               // enclosing namespaces are the global namespace, Q, and Q::V
        |{}
        |""".stripMargin) { cpg =>
      fail("Failed for: " + cpg)
    }

  }

}

object NamespaceFixture {
  def apply(code: String)(f: Cpg => Unit): Unit = {
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / "file1.c"
      file.write(code)

      val cpg = Cpg.emptyCpg
      val keyPool = new IntervalKeyPool(1001, 2000)
      val typesKeyPool = new IntervalKeyPool(2001, 3000)
      val filenames = List(file.path.toAbsolutePath.toString)
      val astCreationPass = new AstCreationPass(filenames, cpg, keyPool)
      astCreationPass.createAndApply()
      new StubRemovalPass(cpg).createAndApply()
      new TypeNodePass(astCreationPass.usedTypes(), cpg, Some(typesKeyPool)).createAndApply()
      f(cpg)
    }
  }
}
