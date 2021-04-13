package io.shiftleft.cpgvalidator

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, Properties, nodes}
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.Cardinality
import io.shiftleft.cpgvalidator.validators.KeysValidator
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class KeysValidatorTest extends AnyWordSpec with Matchers {

  private class TestValidationErrorRegistry extends ValidationErrorRegistry {
    def getErrors: Iterable[ValidationError] = validationErrors.values.flatten
  }

  private def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    val cpg = Cpg(graph)
    try fun(cpg)
    finally cpg.close()
  }

  "report no validation errors for a key with Cardinality one" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.METHOD_PARAMETER_IN,
        Properties.NAME -> "someMethod",
        Properties.TYPE_FULL_NAME -> "someMethod",
        Properties.ORDER -> 1,
        Properties.CODE -> "some code",
        Properties.EVALUATION_STRATEGY -> "someStrategy"
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report a validation error for a key with Cardinality one but is not set" in {
    withNewBaseCpg { cpg =>
      val errorRegistry = new TestValidationErrorRegistry()
      val validator = new KeysValidator(errorRegistry)
      val node = cpg.graph + NodeTypes.METHOD
      validator.validate(cpg) shouldBe false
      errorRegistry.getErrors should contain(
        KeyError(node, "NAME", Cardinality.One)
      )
    }
  }

  "report no validation errors for a key with Cardinality list (with some values)" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      val node = cpg.graph + (
        NodeTypes.TYPE_DECL,
        Properties.NAME -> "SomeTypeDecl",
        Properties.AST_PARENT_TYPE -> "SomeParentType",
        Properties.AST_PARENT_FULL_NAME -> "SomeTypeDecl",
        Properties.IS_EXTERNAL -> false,
        Properties.ORDER -> 1,
        Properties.FULL_NAME -> "SomeTypeDecl",
        Properties.FILENAME -> "",
        Properties.INHERITS_FROM_TYPE_FULL_NAME -> List("a", "b"),
      )
      validator.validate(cpg) shouldBe true
      node.asInstanceOf[nodes.TypeDecl].inheritsFromTypeFullName shouldBe (List("a", "b"))
    }
  }

  "report no validation errors for a key with Cardinality list when this property is not set" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.TYPE_DECL,
        Properties.NAME -> "SomeTypeDecl",
        Properties.AST_PARENT_TYPE -> "SomeParentType",
        Properties.AST_PARENT_FULL_NAME -> "SomeTypeDecl",
        Properties.IS_EXTERNAL -> false,
        Properties.ORDER -> 1,
        Properties.FULL_NAME -> "SomeTypeDecl",
        Properties.FILENAME -> "",
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality list when this list is empty)" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.TYPE_DECL,
        Properties.NAME -> "SomeTypeDecl",
        Properties.AST_PARENT_TYPE -> "SomeParentType",
        Properties.AST_PARENT_FULL_NAME -> "SomeTypeDecl",
        Properties.IS_EXTERNAL -> false,
        Properties.ORDER -> 1,
        Properties.FULL_NAME -> "SomeTypeDecl",
        Properties.FILENAME -> "",
        Properties.INHERITS_FROM_TYPE_FULL_NAME -> Nil,
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when exactly 1 is present" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.ANNOTATION_LITERAL,
        Properties.LINE_NUMBER -> 1,
        Properties.NAME -> "SomeAnnotation",
        Properties.ORDER -> 1,
        Properties.ARGUMENT_INDEX -> 1,
        Properties.CODE -> "some code;"
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when this property is empty" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.ANNOTATION_LITERAL,
//        Properties.LINE_NUMBER intentionally not set
        Properties.NAME -> "SomeAnnotation",
        Properties.ORDER -> 1,
        Properties.ARGUMENT_INDEX -> 1,
        Properties.CODE -> "some code;"
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when this property is not set" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.ANNOTATION_LITERAL,
        Properties.NAME -> "SomeAnnotation",
        Properties.ORDER -> 1,
        Properties.ARGUMENT_INDEX -> 1,
        Properties.CODE -> "some code;"
      )
      validator.validate(cpg) shouldBe true
    }
  }

}
