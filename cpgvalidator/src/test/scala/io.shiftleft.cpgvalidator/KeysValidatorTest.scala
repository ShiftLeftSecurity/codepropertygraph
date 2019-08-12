package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.cpgvalidator.validators.KeysValidator
import gremlin.scala._
import io.shiftleft.TinkerGraphTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.Cardinality
import org.scalatest.{Matchers, WordSpec}

class KeysValidatorTest extends WordSpec with Matchers {

  private class TestValidationErrorRegistry extends ValidationErrorRegistry {
    def getErrors: Iterable[ValidationError] = validationErrors.values.flatten
  }

  private class TestKeysValidator extends KeysValidator {

    override val errorRegistry: TestValidationErrorRegistry =
      new TestValidationErrorRegistry

  }

  private def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = TinkerGraphTestInstance.create
    val cpg = Cpg(graph.graph)
    try fun(cpg)
    finally cpg.close()
  }

  "report no validation errors for a key with Cardinality one" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.METHOD_PARAMETER_IN
      node.property("NAME", "someMethod")
      node.property("TYPE_FULL_NAME", "someMethod")
      node.property("ORDER", 1)
      node.property("CODE", "some code;")
      node.property("EVALUATION_STRATEGY", "someStrategy")
      validator.validate(cpg) shouldBe true
    }
  }

  "report a validation error for a key with Cardinality one but is not set" in {
    withNewBaseCpg { cpg =>
      val validator = new TestKeysValidator()
      val node = cpg.graph + NodeTypes.METHOD
      validator.validate(cpg) shouldBe false
      validator.errorRegistry.getErrors should contain(
        KeyError(node, "NAME", Cardinality.One)
      )
    }
  }

  "report no validation errors for a key with Cardinality list (with some values)" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.TYPE_DECL
      node.setPropertyList("INHERITS_FROM_TYPE_FULL_NAME", List("a", "b"))
      node.property("NAME", "SomeTypeDecl")
      node.property("AST_PARENT_TYPE", "SomeParentType")
      node.property("AST_PARENT_FULL_NAME", "SomeTypeDecl")
      node.property("IS_EXTERNAL", false)
      node.property("ORDER", 1)
      node.property("FULL_NAME", "SomeTypeDecl")
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality list when this property is not set" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.TYPE_DECL
      node.property("NAME", "SomeTypeDecl")
      node.property("AST_PARENT_TYPE", "SomeParentType")
      node.property("AST_PARENT_FULL_NAME", "SomeTypeDecl")
      node.property("IS_EXTERNAL", false)
      node.property("ORDER", 1)
      node.property("FULL_NAME", "SomeTypeDecl")
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality list when this list is empty)" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.TYPE_DECL
      node.setPropertyList("INHERITS_FROM_TYPE_FULL_NAME", List.empty)
      node.property("NAME", "SomeTypeDecl")
      node.property("AST_PARENT_TYPE", "SomeParentType")
      node.property("AST_PARENT_FULL_NAME", "SomeTypeDecl")
      node.property("IS_EXTERNAL", false)
      node.property("ORDER", 1)
      node.property("FULL_NAME", "SomeTypeDecl")
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when exactly 1 is present" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.ANNOTATION_LITERAL
      node.property("LINE_NUMBER", 1)
      node.property("NAME", "SomeAnnotation")
      node.property("ORDER", 1)
      node.property("CODE", "some code;")
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when this property is empty" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.ANNOTATION_LITERAL
      node.property("LINE_NUMBER", Option.empty)
      node.property("NAME", "SomeAnnotation")
      node.property("ORDER", 1)
      node.property("CODE", "some code;")
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when this property is not set" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator()
      val node = cpg.graph + NodeTypes.ANNOTATION_LITERAL
      node.property("NAME", "SomeAnnotation")
      node.property("ORDER", 1)
      node.property("CODE", "some code;")
      validator.validate(cpg) shouldBe true
    }
  }

}
