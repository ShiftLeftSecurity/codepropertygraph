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

    "be correct for unnamed namespaces" in NamespaceFixture(
      """
        |namespace {
        |    int i; // defines ::(unique)::i
        |}
        |void f() {
        |    i++;   // increments ::(unique)::i
        |}
        | 
        |namespace A {
        |    namespace {
        |        int i;        // A::(unique)::i
        |        int j;        // A::(unique)::j
        |    }
        |    void g() { i++; } // A::(unique)::i++
        |}
        | 
        |using namespace A; // introduces all names from A into global namespace
        |void h() {
        |    i++;    // error: ::(unique)::i and ::A::(unique)::i are both in scope
        |    A::i++; // ok, increments ::A::(unique)::i
        |    j++;    // ok, increments ::A::(unique)::j
        |}""".stripMargin) { cpg =>
      fail("Failed for: " + cpg)
    }

    "be correct for namespaces with using" in NamespaceFixture(
      """
        |void f();
        |namespace A {
        |    void g();
        |}
        | 
        |namespace X {
        |    using ::f;        // global f is now visible as ::X::f
        |    using A::g;       // A::g is now visible as ::X::g
        |}
        | 
        |void h()
        |{
        |    X::f(); // calls ::f
        |    X::g(); // calls A::g
        |}""".stripMargin) { cpg =>
      fail("Failed for: " + cpg)
    }

    "be correct for namespaces with using and synonyms" in NamespaceFixture(
      """
        |namespace A {
        |    void f(int);
        |}
        |using A::f; // ::f is now a synonym for A::f(int)
        | 
        |namespace A {     // namespace extension
        |    void f(char); // does not change what ::f means
        |}
        | 
        |void foo() {
        |    f('a'); // calls f(int), even though f(char) exists.
        |}
        | 
        |void bar() {
        |    using A::f; // this f is a synonym for both A::f(int) and A::f(char)
        |    f('a');     // calls f(char)
        |}""".stripMargin) { cpg =>
      fail("Failed for: " + cpg)
    }

    "be correct for namespaces with alias" in NamespaceFixture("""
        |namespace foo {
        |    namespace bar {
        |         namespace baz {
        |             int qux = 42;
        |         }
        |    }
        |}
        | 
        |namespace fbz = foo::bar::baz;
        | 
        |int main() {
        |    int x = fbz::qux;
        |}""".stripMargin) { cpg =>
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
