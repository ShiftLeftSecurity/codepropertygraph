package io.shiftleft.cpgvalidator

import overflowdb._
import io.shiftleft.codepropertygraph.generated.{nodes, NodeKeysOdb, NodeTypes}
import io.shiftleft.cpgvalidator.validators.KeysValidator
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.Cardinality
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

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
        NodeKeysOdb.NAME -> "someMethod",
        NodeKeysOdb.TYPE_FULL_NAME -> "someMethod",
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.CODE -> "some code",
        NodeKeysOdb.EVALUATION_STRATEGY -> "someStrategy"
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
        NodeKeysOdb.NAME -> "SomeTypeDecl",
        NodeKeysOdb.AST_PARENT_TYPE -> "SomeParentType",
        NodeKeysOdb.AST_PARENT_FULL_NAME -> "SomeTypeDecl",
        NodeKeysOdb.IS_EXTERNAL -> false,
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.FULL_NAME -> "SomeTypeDecl",
        NodeKeysOdb.FILENAME -> "",
        NodeKeysOdb.INHERITS_FROM_TYPE_FULL_NAME -> List("a", "b"),
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
        NodeKeysOdb.NAME -> "SomeTypeDecl",
        NodeKeysOdb.AST_PARENT_TYPE -> "SomeParentType",
        NodeKeysOdb.AST_PARENT_FULL_NAME -> "SomeTypeDecl",
        NodeKeysOdb.IS_EXTERNAL -> false,
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.FULL_NAME -> "SomeTypeDecl",
        NodeKeysOdb.FILENAME -> "",
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality list when this list is empty)" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.TYPE_DECL,
        NodeKeysOdb.NAME -> "SomeTypeDecl",
        NodeKeysOdb.AST_PARENT_TYPE -> "SomeParentType",
        NodeKeysOdb.AST_PARENT_FULL_NAME -> "SomeTypeDecl",
        NodeKeysOdb.IS_EXTERNAL -> false,
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.FULL_NAME -> "SomeTypeDecl",
        NodeKeysOdb.FILENAME -> "",
        NodeKeysOdb.INHERITS_FROM_TYPE_FULL_NAME -> Nil,
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when exactly 1 is present" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.ANNOTATION_LITERAL,
        NodeKeysOdb.LINE_NUMBER -> 1,
        NodeKeysOdb.NAME -> "SomeAnnotation",
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.ARGUMENT_INDEX -> 1,
        NodeKeysOdb.CODE -> "some code;"
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when this property is empty" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.ANNOTATION_LITERAL,
//        NodeKeysOdb.LINE_NUMBER intentionally not set
        NodeKeysOdb.NAME -> "SomeAnnotation",
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.ARGUMENT_INDEX -> 1,
        NodeKeysOdb.CODE -> "some code;"
      )
      validator.validate(cpg) shouldBe true
    }
  }

  "report no validation errors for a key with Cardinality zeroOrOne when this property is not set" in {
    withNewBaseCpg { cpg =>
      val validator = new KeysValidator(new ValidationErrorRegistry)
      cpg.graph + (
        NodeTypes.ANNOTATION_LITERAL,
        NodeKeysOdb.NAME -> "SomeAnnotation",
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.ARGUMENT_INDEX -> 1,
        NodeKeysOdb.CODE -> "some code;"
      )
      validator.validate(cpg) shouldBe true
    }
  }

}
