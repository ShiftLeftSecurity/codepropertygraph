package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TypeTests extends AnyWordSpec with Matchers {
  ExistingCpgFixture("type") { fixture =>
    "ClassMemberTest" should {

      "have ClassMemberTest as internal class" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl.name("ClassMemberTest").internal.toList

        queryResult.size shouldBe 1
      }

      "have Object as external class" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl.name("Object").external.toList

        queryResult.size shouldBe 1
      }

      "have a member called member" in {
        def queryResult: List[nodes.Member] =
          fixture.cpg.typeDecl.member.nameExact("member").toList

        queryResult.size shouldBe 1
      }

      "have a static member called static_member" in {
        def queryResult: List[nodes.Member] =
          fixture.cpg.typeDecl.member.nameExact("static_member").isStatic.toList

        queryResult.size shouldBe 1
      }

      "more than 0 members found by regex" in {
        def queryResult: List[nodes.Member] =
          fixture.cpg.typeDecl.member.name(".*").toList

        queryResult.size should be > 0
      }
    }

    "ClassHierarchyTest for type declarations" should {
      "have class Base as base class of class Derived" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*Derived")
            .baseTypeDecl
            .name(".*Base")
            .toList

        queryResult.size shouldBe 1
      }

      "have class Dervied as derived class of class Base" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*Base")
            .derivedTypeDecl
            .name(".*Derived")
            .toList

        queryResult.size shouldBe 1
      }

      "have classes Interface1, Interface2 and Object as base classes of class InterfaceImplementor" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*InterfaceImplementor")
            .baseTypeDecl
            .toList

        queryResult.size shouldBe 3
        queryResult.map(_.name).toSet shouldBe
          Set("ClassHierarchyTest$Interface1", "ClassHierarchyTest$Interface2", "Object")
      }

      "have class InterfaceImplementor as derived class of class Interface1" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*Interface1")
            .derivedTypeDecl
            .name(".*InterfaceImplementor")
            .toList

        queryResult.size shouldBe 1
      }

      "have class InterfaceImplementor as derived class of class Interface2" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*Interface2")
            .derivedTypeDecl
            .name(".*InterfaceImplementor")
            .toList

        queryResult.size shouldBe 1
      }

      "have Derived and Derived2 as transitive derived types of Base" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*Base")
            .derivedTypeDeclTransitive
            .toList

        queryResult.map(_.name).toSet shouldBe Set("ClassHierarchyTest$Derived", "ClassHierarchyTest$Derived2")
      }

      "have Base and Object as transitive base types of Derived" in {
        def queryResult: List[nodes.TypeDecl] =
          fixture.cpg.typeDecl
            .name(".*Derived")
            .baseTypeDeclTransitive
            .toList

        queryResult.map(_.name).toSet shouldBe Set("ClassHierarchyTest$Base", "Object")
      }

    }

    "ClassHierarchyTest for types" should {
      "have class Base as base class of class Derived" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*Derived")
            .baseType
            .name(".*Base")
            .toList

        queryResult.size shouldBe 1
      }

      "have class Dervied as derived class of class Base" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*Base")
            .derivedType
            .name(".*Derived")
            .toList

        queryResult.size shouldBe 1
      }

      "have classes Interface1, Interface2 and Object as base classes of class InterfaceImplementor" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*InterfaceImplementor")
            .baseType
            .toList

        queryResult.size shouldBe 3
        queryResult.map(_.name).toSet shouldBe
          Set("ClassHierarchyTest$Interface1", "ClassHierarchyTest$Interface2", "Object")
      }

      "have class InterfaceImplementor as derived class of class Interface1" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*Interface1")
            .derivedType
            .name(".*InterfaceImplementor")
            .toList

        queryResult.size shouldBe 1
      }

      "have class InterfaceImplementor as derived class of class Interface2" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*Interface2")
            .derivedType
            .name(".*InterfaceImplementor")
            .toList

        queryResult.size shouldBe 1
      }

      "have Derived and Derived2 as transitive derived types of Base" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*Base")
            .derivedTypeTransitive
            .toList

        queryResult.map(_.name).toSet shouldBe Set("ClassHierarchyTest$Derived", "ClassHierarchyTest$Derived2")
      }

      "have Base and Object as transitive base types of Derived" in {
        def queryResult: List[nodes.Type] =
          fixture.cpg.typ
            .name(".*Derived")
            .baseTypeTransitive
            .toList

        queryResult.map(_.name).toSet shouldBe Set("ClassHierarchyTest$Base", "Object")
      }

    }
  }
}
