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

class NamespaceTests extends AnyWordSpec with Matchers with Inside {

  private object NamespaceFixture {
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

  "Namespaces" should {

    "be correct for nested namespaces" in NamespaceFixture(
      """
        |namespace Q {
        |    namespace V {   // V is a member of Q, and is fully defined within Q
        |        class C { int m(); }; // C is a member of V and is fully defined within V
        |                               // C::m is only declared
        |        int f(); // f is a member of V, but is only declared here
        |    }
        | 
        |    int V::f() // definition of V's member f outside of V
        |                // f's enclosing namespaces are still the global namespace, Q, and Q::V
        |    {
        |        extern void h(); // This declares ::Q::V::h
        |        return 0;
        |    }
        | 
        |    int V::C::m() // definition of V::C::m outside of the namespace (and the class body)
        |                   // enclosing namespaces are the global namespace, Q, and Q::V
        |    { return 0 ; }
        |}
        |""".stripMargin) { cpg =>
      inside(cpg.method.fullName.l) {
        case List(f, h, m) =>
          f shouldBe "Q.V.f"
          // TODO: this might be a bug. h is declared inside of f but as extern.
          //  Looks like extern is not part of the Eclipse CDT AST:
          h shouldBe "Q.V.f.h"
          m shouldBe "Q.V.C.m"
      }

      inside(cpg.namespaceBlock.l) {
        case List(_, q, v) =>
          q.fullName shouldBe "Q"
          v.fullName shouldBe "Q.V"

          inside(v.typeDecl.l) {
            case List(c) =>
              c.name shouldBe "C"
              c.fullName shouldBe "Q.V.C"
          }

          inside(q.method.l) {
            case List(f, m) =>
              f.name shouldBe "f"
              f.fullName shouldBe "Q.V.f"
              m.name shouldBe "m"
              m.fullName shouldBe "Q.V.C.m"
          }
      }

    }

    "be correct for nested namespaces in C++17 style" in NamespaceFixture(
      """
        |namespace Q::V {
        |   class C { int m(); }; // C is a member of V and is fully defined within V
        |                               // C::m is only declared
        |   int f(); // f is a member of V, but is only declared here
        |}
        | 
        |int V::f() // definition of V's member f outside of V
        |            // f's enclosing namespaces are still the global namespace, Q, and Q::V
        |{
        |    extern void h(); // This declares ::Q::V::h
        |    return 0;
        |}
        | 
        |int V::C::m() // definition of V::C::m outside of the namespace (and the class body)
        |               // enclosing namespaces are the global namespace, Q, and Q::V
        |{ return 0; }
        |""".stripMargin) { cpg =>
      inside(cpg.method.fullName.l) {
        case List(m1, f1, f2, h, m2) =>
          // TODO: this looks strange too it first glance. But as Eclipse CDT does not provide any
          //  mapping from definitions outside of namespace into them we cant reconstruct proper full-names.
          m1 shouldBe "Q.V.C.m"
          f1 shouldBe "Q.V.f"
          f2 shouldBe "V.f"
          h shouldBe "V.f.h"
          m2 shouldBe "V.C.m"
      }

      inside(cpg.namespaceBlock.l) {
        case List(_, q, v) =>
          q.fullName shouldBe "Q"
          v.fullName shouldBe "Q.V"

          inside(v.typeDecl.l) {
            case List(c) =>
              c.name shouldBe "C"
              c.fullName shouldBe "Q.V.C"
          }
      }
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
      inside(cpg.namespaceBlock.l) {
        case List(_, unnamed1, a, unnamed2) =>
          unnamed1.fullName shouldBe "anonymous_namespace_0"
          a.fullName shouldBe "A"
          unnamed2.fullName shouldBe "A.anonymous_namespace_1"
      }

      inside(cpg.method.fullName.l) {
        case List(f, g, h) =>
          f shouldBe "f"
          g shouldBe "A.g"
          h shouldBe "h"
      }

      inside(cpg.method.nameExact("h").ast.isCall.code.l) {
        case List(c1, c2, c3, c4) =>
          c1 shouldBe "i++"
          c2 shouldBe "A::i++"
          c3 shouldBe "A::i"
          c4 shouldBe "j++"
      }
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
      inside(cpg.namespaceBlock.l) {
        case List(_, a, x) =>
          a.fullName shouldBe "A"
          x.fullName shouldBe "X"
      }

      inside(cpg.method.fullName.l) {
        case List(f, g, h) =>
          f shouldBe "f"
          g shouldBe "A.g"
          h shouldBe "h"
      }

      inside(cpg.call.l) {
        case List(f, g) =>
          f.name shouldBe "f"
          f.methodFullName shouldBe "f"
          g.name shouldBe "g"
          g.methodFullName shouldBe "A.g"
      }
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
      inside(cpg.namespaceBlock.l) {
        case List(_, a1, a2) =>
          // TODO: how to handle namespace extension?
          a1.fullName shouldBe "A"
          a2.fullName shouldBe "A"
      }

      inside(cpg.method.l) {
        case List(f1, f2, foo, bar) =>
          f1.fullName shouldBe "A.f"
          f1.signature shouldBe "void A.f (int)"
          f2.fullName shouldBe "A.f"
          f2.signature shouldBe "void A.f (char)"
          foo.fullName shouldBe "foo"
          bar.fullName shouldBe "bar"
      }

      inside(cpg.call.l) {
        case List(c1, c2) =>
          c1.name shouldBe "f"
          c1.methodFullName shouldBe "A.f"
          c2.name shouldBe "f"
          c2.methodFullName shouldBe "A.f"
      }
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
      inside(cpg.namespaceBlock.l) {
        case List(_, foo, bar, baz, fbz) =>
          foo.name shouldBe "foo"
          foo.fullName shouldBe "foo"
          bar.name shouldBe "bar"
          bar.fullName shouldBe "foo.bar"
          baz.name shouldBe "baz"
          baz.fullName shouldBe "foo.bar.baz"

          inside(baz.ast.isIdentifier.l) {
            case List(qux) =>
              qux.name shouldBe "qux"
              qux.typeFullName shouldBe "int"
          }

          fbz.name shouldBe "fbz"
          fbz.fullName shouldBe "foo.bar.baz"
      }

      inside(cpg.call.l) {
        case List(c1, c2, c3) =>
          c1.code shouldBe "qux = 42"
          c2.code shouldBe "x = fbz::qux"
          c3.code shouldBe "fbz::qux"
          inside(c3.ast.l) {
            case List(call: Call, x: Identifier, a: FieldIdentifier) =>
              call.name shouldBe Operators.fieldAccess
              x.order shouldBe 1
              x.name shouldBe "fbz"
              a.order shouldBe 2
              a.code shouldBe "qux"
          }
      }
    }

  }

}
