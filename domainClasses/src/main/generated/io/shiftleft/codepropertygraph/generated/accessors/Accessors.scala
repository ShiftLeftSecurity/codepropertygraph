package io.shiftleft.codepropertygraph.generated.accessors
import io.shiftleft.codepropertygraph.generated.nodes
import scala.collection.immutable.IndexedSeq

/** not supposed to be used directly by users, hence the `bootstrap` in the name */
object languagebootstrap extends ConcreteStoredConversions

object Accessors {
  /* accessors for concrete stored nodes start */
  final class AccessPropertyAliasTypeFullName(val node: nodes.StoredNode) extends AnyVal {
    def aliasTypeFullName: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 0, node.seq)
  }
  final class AccessPropertyArgumentIndex(val node: nodes.StoredNode) extends AnyVal {
    def argumentIndex: Int =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 1, node.seq(), -1: Int)
  }
  final class AccessPropertyArgumentName(val node: nodes.StoredNode) extends AnyVal {
    def argumentName: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 2, node.seq)
  }
  final class AccessPropertyAstParentFullName(val node: nodes.StoredNode) extends AnyVal {
    def astParentFullName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 3, node.seq(), "<empty>": String)
  }
  final class AccessPropertyAstParentType(val node: nodes.StoredNode) extends AnyVal {
    def astParentType: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 4, node.seq(), "<empty>": String)
  }
  final class AccessPropertyCanonicalName(val node: nodes.StoredNode) extends AnyVal {
    def canonicalName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 5, node.seq(), "<empty>": String)
  }
  final class AccessPropertyClassName(val node: nodes.StoredNode) extends AnyVal {
    def className: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 6, node.seq(), "<empty>": String)
  }
  final class AccessPropertyClassShortName(val node: nodes.StoredNode) extends AnyVal {
    def classShortName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 7, node.seq(), "<empty>": String)
  }
  final class AccessPropertyClosureBindingId(val node: nodes.StoredNode) extends AnyVal {
    def closureBindingId: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 8, node.seq)
  }
  final class AccessPropertyClosureOriginalName(val node: nodes.StoredNode) extends AnyVal {
    def closureOriginalName: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 9, node.seq)
  }
  final class AccessPropertyCode(val node: nodes.StoredNode) extends AnyVal {
    def code: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 10, node.seq(), "<empty>": String)
  }
  final class AccessPropertyColumnNumber(val node: nodes.StoredNode) extends AnyVal {
    def columnNumber: Option[Int] =
      flatgraph.Accessors.getNodePropertyOption[Int](node.graph, node.nodeKind, 11, node.seq)
  }
  final class AccessPropertyColumnNumberEnd(val node: nodes.StoredNode) extends AnyVal {
    def columnNumberEnd: Option[Int] =
      flatgraph.Accessors.getNodePropertyOption[Int](node.graph, node.nodeKind, 12, node.seq)
  }
  final class AccessPropertyContainedRef(val node: nodes.StoredNode) extends AnyVal {
    def containedRef: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 13, node.seq(), "<empty>": String)
  }
  final class AccessPropertyContent(val node: nodes.StoredNode) extends AnyVal {
    def content: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 14, node.seq(), "<empty>": String)
  }
  final class AccessPropertyControlStructureType(val node: nodes.StoredNode) extends AnyVal {
    def controlStructureType: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 15, node.seq(), "<empty>": String)
  }
  final class AccessPropertyDependencyGroupId(val node: nodes.StoredNode) extends AnyVal {
    def dependencyGroupId: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 16, node.seq)
  }
  final class AccessPropertyDispatchType(val node: nodes.StoredNode) extends AnyVal {
    def dispatchType: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 17, node.seq(), "<empty>": String)
  }
  final class AccessPropertyDynamicTypeHintFullName(val node: nodes.StoredNode) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] =
      flatgraph.Accessors.getNodePropertyMulti[String](node.graph, node.nodeKind, 18, node.seq)
  }
  final class AccessPropertyEvaluationStrategy(val node: nodes.StoredNode) extends AnyVal {
    def evaluationStrategy: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 19, node.seq(), "<empty>": String)
  }
  final class AccessPropertyExplicitAs(val node: nodes.StoredNode) extends AnyVal {
    def explicitAs: Option[Boolean] =
      flatgraph.Accessors.getNodePropertyOption[Boolean](node.graph, node.nodeKind, 20, node.seq)
  }
  final class AccessPropertyFilename(val node: nodes.StoredNode) extends AnyVal {
    def filename: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 21, node.seq(), "<empty>": String)
  }
  final class AccessPropertyFullName(val node: nodes.StoredNode) extends AnyVal {
    def fullName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 22, node.seq(), "<empty>": String)
  }
  final class AccessPropertyHash(val node: nodes.StoredNode) extends AnyVal {
    def hash: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 23, node.seq)
  }
  final class AccessPropertyImportedAs(val node: nodes.StoredNode) extends AnyVal {
    def importedAs: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 24, node.seq)
  }
  final class AccessPropertyImportedEntity(val node: nodes.StoredNode) extends AnyVal {
    def importedEntity: Option[String] =
      flatgraph.Accessors.getNodePropertyOption[String](node.graph, node.nodeKind, 25, node.seq)
  }
  final class AccessPropertyIndex(val node: nodes.StoredNode) extends AnyVal {
    def index: Int = flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 26, node.seq(), -1: Int)
  }
  final class AccessPropertyInheritsFromTypeFullName(val node: nodes.StoredNode) extends AnyVal {
    def inheritsFromTypeFullName: IndexedSeq[String] =
      flatgraph.Accessors.getNodePropertyMulti[String](node.graph, node.nodeKind, 27, node.seq)
  }
  final class AccessPropertyIsExplicit(val node: nodes.StoredNode) extends AnyVal {
    def isExplicit: Option[Boolean] =
      flatgraph.Accessors.getNodePropertyOption[Boolean](node.graph, node.nodeKind, 28, node.seq)
  }
  final class AccessPropertyIsExternal(val node: nodes.StoredNode) extends AnyVal {
    def isExternal: Boolean =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 29, node.seq(), false: Boolean)
  }
  final class AccessPropertyIsVariadic(val node: nodes.StoredNode) extends AnyVal {
    def isVariadic: Boolean =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 30, node.seq(), false: Boolean)
  }
  final class AccessPropertyIsWildcard(val node: nodes.StoredNode) extends AnyVal {
    def isWildcard: Option[Boolean] =
      flatgraph.Accessors.getNodePropertyOption[Boolean](node.graph, node.nodeKind, 31, node.seq)
  }
  final class AccessPropertyKey(val node: nodes.StoredNode) extends AnyVal {
    def key: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 32, node.seq(), "<empty>": String)
  }
  final class AccessPropertyLanguage(val node: nodes.StoredNode) extends AnyVal {
    def language: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 33, node.seq(), "<empty>": String)
  }
  final class AccessPropertyLineNumber(val node: nodes.StoredNode) extends AnyVal {
    def lineNumber: Option[Int] =
      flatgraph.Accessors.getNodePropertyOption[Int](node.graph, node.nodeKind, 34, node.seq)
  }
  final class AccessPropertyLineNumberEnd(val node: nodes.StoredNode) extends AnyVal {
    def lineNumberEnd: Option[Int] =
      flatgraph.Accessors.getNodePropertyOption[Int](node.graph, node.nodeKind, 35, node.seq)
  }
  final class AccessPropertyMethodFullName(val node: nodes.StoredNode) extends AnyVal {
    def methodFullName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 36, node.seq(), "<empty>": String)
  }
  final class AccessPropertyMethodShortName(val node: nodes.StoredNode) extends AnyVal {
    def methodShortName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 37, node.seq(), "<empty>": String)
  }
  final class AccessPropertyModifierType(val node: nodes.StoredNode) extends AnyVal {
    def modifierType: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 38, node.seq(), "<empty>": String)
  }
  final class AccessPropertyName(val node: nodes.StoredNode) extends AnyVal {
    def name: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 39, node.seq(), "<empty>": String)
  }
  final class AccessPropertyNodeLabel(val node: nodes.StoredNode) extends AnyVal {
    def nodeLabel: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 40, node.seq(), "<empty>": String)
  }
  final class AccessPropertyOffset(val node: nodes.StoredNode) extends AnyVal {
    def offset: Option[Int] = flatgraph.Accessors.getNodePropertyOption[Int](node.graph, node.nodeKind, 41, node.seq)
  }
  final class AccessPropertyOffsetEnd(val node: nodes.StoredNode) extends AnyVal {
    def offsetEnd: Option[Int] = flatgraph.Accessors.getNodePropertyOption[Int](node.graph, node.nodeKind, 42, node.seq)
  }
  final class AccessPropertyOrder(val node: nodes.StoredNode) extends AnyVal {
    def order: Int = flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 43, node.seq(), -1: Int)
  }
  final class AccessPropertyOverlays(val node: nodes.StoredNode) extends AnyVal {
    def overlays: IndexedSeq[String] =
      flatgraph.Accessors.getNodePropertyMulti[String](node.graph, node.nodeKind, 44, node.seq)
  }
  final class AccessPropertyPackageName(val node: nodes.StoredNode) extends AnyVal {
    def packageName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 45, node.seq(), "<empty>": String)
  }
  final class AccessPropertyParserTypeName(val node: nodes.StoredNode) extends AnyVal {
    def parserTypeName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 46, node.seq(), "<empty>": String)
  }
  final class AccessPropertyPossibleTypes(val node: nodes.StoredNode) extends AnyVal {
    def possibleTypes: IndexedSeq[String] =
      flatgraph.Accessors.getNodePropertyMulti[String](node.graph, node.nodeKind, 47, node.seq)
  }
  final class AccessPropertyRoot(val node: nodes.StoredNode) extends AnyVal {
    def root: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 48, node.seq(), "<empty>": String)
  }
  final class AccessPropertySignature(val node: nodes.StoredNode) extends AnyVal {
    def signature: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 49, node.seq(), "": String)
  }
  final class AccessPropertySymbol(val node: nodes.StoredNode) extends AnyVal {
    def symbol: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 50, node.seq(), "<empty>": String)
  }
  final class AccessPropertyTypeDeclFullName(val node: nodes.StoredNode) extends AnyVal {
    def typeDeclFullName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 51, node.seq(), "<empty>": String)
  }
  final class AccessPropertyTypeFullName(val node: nodes.StoredNode) extends AnyVal {
    def typeFullName: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 52, node.seq(), "<empty>": String)
  }
  final class AccessPropertyValue(val node: nodes.StoredNode) extends AnyVal {
    def value: String = flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 53, node.seq(), "": String)
  }
  final class AccessPropertyVersion(val node: nodes.StoredNode) extends AnyVal {
    def version: String =
      flatgraph.Accessors.getNodePropertySingle(node.graph, node.nodeKind, 54, node.seq(), "<empty>": String)
  }
  /* accessors for concrete stored nodes end */

  /* accessors for base nodes start */
  final class AccessAnnotationBase(val node: nodes.AnnotationBase) extends AnyVal {
    def fullName: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyFullName(stored).fullName
      case newNode: nodes.NewAnnotation => newNode.fullName
    }
    def name: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyName(stored).name
      case newNode: nodes.NewAnnotation => newNode.name
    }
  }
  final class AccessAnnotationliteralBase(val node: nodes.AnnotationLiteralBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode            => new AccessPropertyName(stored).name
      case newNode: nodes.NewAnnotationLiteral => newNode.name
    }
  }
  final class AccessAnnotationparameterBase(val node: nodes.AnnotationParameterBase)             extends AnyVal {}
  final class AccessAnnotationparameterassignBase(val node: nodes.AnnotationParameterAssignBase) extends AnyVal {}
  final class AccessArrayinitializerBase(val node: nodes.ArrayInitializerBase)                   extends AnyVal {}
  final class AccessBindingBase(val node: nodes.BindingBase) extends AnyVal {
    def methodFullName: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyMethodFullName(stored).methodFullName
      case newNode: nodes.NewBinding => newNode.methodFullName
    }
    def name: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyName(stored).name
      case newNode: nodes.NewBinding => newNode.name
    }
    def signature: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertySignature(stored).signature
      case newNode: nodes.NewBinding => newNode.signature
    }
  }
  final class AccessBlockBase(val node: nodes.BlockBase) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewBlock  => newNode.dynamicTypeHintFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewBlock  => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewBlock  => newNode.typeFullName
    }
  }
  final class AccessCallBase(val node: nodes.CallBase) extends AnyVal {
    def dispatchType: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyDispatchType(stored).dispatchType
      case newNode: nodes.NewCall   => newNode.dispatchType
    }
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewCall   => newNode.dynamicTypeHintFullName
    }
    def methodFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyMethodFullName(stored).methodFullName
      case newNode: nodes.NewCall   => newNode.methodFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewCall   => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewCall   => newNode.typeFullName
    }
  }
  final class AccessClosurebindingBase(val node: nodes.ClosureBindingBase) extends AnyVal {
    def closureBindingId: Option[String] = node match {
      case stored: nodes.StoredNode         => new AccessPropertyClosureBindingId(stored).closureBindingId
      case newNode: nodes.NewClosureBinding => newNode.closureBindingId
    }
    def closureOriginalName: Option[String] = node match {
      case stored: nodes.StoredNode         => new AccessPropertyClosureOriginalName(stored).closureOriginalName
      case newNode: nodes.NewClosureBinding => newNode.closureOriginalName
    }
    def evaluationStrategy: String = node match {
      case stored: nodes.StoredNode         => new AccessPropertyEvaluationStrategy(stored).evaluationStrategy
      case newNode: nodes.NewClosureBinding => newNode.evaluationStrategy
    }
  }
  final class AccessCommentBase(val node: nodes.CommentBase) extends AnyVal {
    def filename: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyFilename(stored).filename
      case newNode: nodes.NewComment => newNode.filename
    }
  }
  final class AccessConfigfileBase(val node: nodes.ConfigFileBase) extends AnyVal {
    def content: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyContent(stored).content
      case newNode: nodes.NewConfigFile => newNode.content
    }
    def name: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyName(stored).name
      case newNode: nodes.NewConfigFile => newNode.name
    }
  }
  final class AccessControlstructureBase(val node: nodes.ControlStructureBase) extends AnyVal {
    def controlStructureType: String = node match {
      case stored: nodes.StoredNode           => new AccessPropertyControlStructureType(stored).controlStructureType
      case newNode: nodes.NewControlStructure => newNode.controlStructureType
    }
    def parserTypeName: String = node match {
      case stored: nodes.StoredNode           => new AccessPropertyParserTypeName(stored).parserTypeName
      case newNode: nodes.NewControlStructure => newNode.parserTypeName
    }
  }
  final class AccessDependencyBase(val node: nodes.DependencyBase) extends AnyVal {
    def dependencyGroupId: Option[String] = node match {
      case stored: nodes.StoredNode     => new AccessPropertyDependencyGroupId(stored).dependencyGroupId
      case newNode: nodes.NewDependency => newNode.dependencyGroupId
    }
    def name: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyName(stored).name
      case newNode: nodes.NewDependency => newNode.name
    }
    def version: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyVersion(stored).version
      case newNode: nodes.NewDependency => newNode.version
    }
  }
  final class AccessFieldidentifierBase(val node: nodes.FieldIdentifierBase) extends AnyVal {
    def canonicalName: String = node match {
      case stored: nodes.StoredNode          => new AccessPropertyCanonicalName(stored).canonicalName
      case newNode: nodes.NewFieldIdentifier => newNode.canonicalName
    }
  }
  final class AccessFileBase(val node: nodes.FileBase) extends AnyVal {
    def content: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyContent(stored).content
      case newNode: nodes.NewFile   => newNode.content
    }
    def hash: Option[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyHash(stored).hash
      case newNode: nodes.NewFile   => newNode.hash
    }
    def name: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyName(stored).name
      case newNode: nodes.NewFile   => newNode.name
    }
  }
  final class AccessFindingBase(val node: nodes.FindingBase) extends AnyVal {}
  final class AccessIdentifierBase(val node: nodes.IdentifierBase) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode     => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewIdentifier => newNode.dynamicTypeHintFullName
    }
    def name: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyName(stored).name
      case newNode: nodes.NewIdentifier => newNode.name
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode     => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewIdentifier => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewIdentifier => newNode.typeFullName
    }
  }
  final class AccessImportBase(val node: nodes.ImportBase) extends AnyVal {
    def explicitAs: Option[Boolean] = node match {
      case stored: nodes.StoredNode => new AccessPropertyExplicitAs(stored).explicitAs
      case newNode: nodes.NewImport => newNode.explicitAs
    }
    def importedAs: Option[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyImportedAs(stored).importedAs
      case newNode: nodes.NewImport => newNode.importedAs
    }
    def importedEntity: Option[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyImportedEntity(stored).importedEntity
      case newNode: nodes.NewImport => newNode.importedEntity
    }
    def isExplicit: Option[Boolean] = node match {
      case stored: nodes.StoredNode => new AccessPropertyIsExplicit(stored).isExplicit
      case newNode: nodes.NewImport => newNode.isExplicit
    }
    def isWildcard: Option[Boolean] = node match {
      case stored: nodes.StoredNode => new AccessPropertyIsWildcard(stored).isWildcard
      case newNode: nodes.NewImport => newNode.isWildcard
    }
  }
  final class AccessJumplabelBase(val node: nodes.JumpLabelBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode    => new AccessPropertyName(stored).name
      case newNode: nodes.NewJumpLabel => newNode.name
    }
    def parserTypeName: String = node match {
      case stored: nodes.StoredNode    => new AccessPropertyParserTypeName(stored).parserTypeName
      case newNode: nodes.NewJumpLabel => newNode.parserTypeName
    }
  }
  final class AccessJumptargetBase(val node: nodes.JumpTargetBase) extends AnyVal {
    def argumentIndex: Int = node match {
      case stored: nodes.StoredNode     => new AccessPropertyArgumentIndex(stored).argumentIndex
      case newNode: nodes.NewJumpTarget => newNode.argumentIndex
    }
    def name: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyName(stored).name
      case newNode: nodes.NewJumpTarget => newNode.name
    }
    def parserTypeName: String = node match {
      case stored: nodes.StoredNode     => new AccessPropertyParserTypeName(stored).parserTypeName
      case newNode: nodes.NewJumpTarget => newNode.parserTypeName
    }
  }
  final class AccessKeyvaluepairBase(val node: nodes.KeyValuePairBase) extends AnyVal {
    def key: String = node match {
      case stored: nodes.StoredNode       => new AccessPropertyKey(stored).key
      case newNode: nodes.NewKeyValuePair => newNode.key
    }
    def value: String = node match {
      case stored: nodes.StoredNode       => new AccessPropertyValue(stored).value
      case newNode: nodes.NewKeyValuePair => newNode.value
    }
  }
  final class AccessLiteralBase(val node: nodes.LiteralBase) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewLiteral => newNode.dynamicTypeHintFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewLiteral => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewLiteral => newNode.typeFullName
    }
  }
  final class AccessLocalBase(val node: nodes.LocalBase) extends AnyVal {
    def closureBindingId: Option[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyClosureBindingId(stored).closureBindingId
      case newNode: nodes.NewLocal  => newNode.closureBindingId
    }
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewLocal  => newNode.dynamicTypeHintFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewLocal  => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewLocal  => newNode.typeFullName
    }
  }
  final class AccessLocationBase(val node: nodes.LocationBase) extends AnyVal {
    def className: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyClassName(stored).className
      case newNode: nodes.NewLocation => newNode.className
    }
    def classShortName: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyClassShortName(stored).classShortName
      case newNode: nodes.NewLocation => newNode.classShortName
    }
    def filename: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyFilename(stored).filename
      case newNode: nodes.NewLocation => newNode.filename
    }
    def lineNumber: Option[Int] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyLineNumber(stored).lineNumber
      case newNode: nodes.NewLocation => newNode.lineNumber
    }
    def methodFullName: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyMethodFullName(stored).methodFullName
      case newNode: nodes.NewLocation => newNode.methodFullName
    }
    def methodShortName: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyMethodShortName(stored).methodShortName
      case newNode: nodes.NewLocation => newNode.methodShortName
    }
    def nodeLabel: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyNodeLabel(stored).nodeLabel
      case newNode: nodes.NewLocation => newNode.nodeLabel
    }
    def packageName: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyPackageName(stored).packageName
      case newNode: nodes.NewLocation => newNode.packageName
    }
    def symbol: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertySymbol(stored).symbol
      case newNode: nodes.NewLocation => newNode.symbol
    }
  }
  final class AccessMemberBase(val node: nodes.MemberBase) extends AnyVal {
    def astParentFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyAstParentFullName(stored).astParentFullName
      case newNode: nodes.NewMember => newNode.astParentFullName
    }
    def astParentType: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyAstParentType(stored).astParentType
      case newNode: nodes.NewMember => newNode.astParentType
    }
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewMember => newNode.dynamicTypeHintFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewMember => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewMember => newNode.typeFullName
    }
  }
  final class AccessMetadataBase(val node: nodes.MetaDataBase) extends AnyVal {
    def hash: Option[String] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyHash(stored).hash
      case newNode: nodes.NewMetaData => newNode.hash
    }
    def language: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyLanguage(stored).language
      case newNode: nodes.NewMetaData => newNode.language
    }
    def overlays: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyOverlays(stored).overlays
      case newNode: nodes.NewMetaData => newNode.overlays
    }
    def root: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyRoot(stored).root
      case newNode: nodes.NewMetaData => newNode.root
    }
    def version: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyVersion(stored).version
      case newNode: nodes.NewMetaData => newNode.version
    }
  }
  final class AccessMethodBase(val node: nodes.MethodBase) extends AnyVal {
    def astParentFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyAstParentFullName(stored).astParentFullName
      case newNode: nodes.NewMethod => newNode.astParentFullName
    }
    def astParentType: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyAstParentType(stored).astParentType
      case newNode: nodes.NewMethod => newNode.astParentType
    }
    def columnNumberEnd: Option[Int] = node match {
      case stored: nodes.StoredNode => new AccessPropertyColumnNumberEnd(stored).columnNumberEnd
      case newNode: nodes.NewMethod => newNode.columnNumberEnd
    }
    def filename: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyFilename(stored).filename
      case newNode: nodes.NewMethod => newNode.filename
    }
    def fullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyFullName(stored).fullName
      case newNode: nodes.NewMethod => newNode.fullName
    }
    def hash: Option[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyHash(stored).hash
      case newNode: nodes.NewMethod => newNode.hash
    }
    def isExternal: Boolean = node match {
      case stored: nodes.StoredNode => new AccessPropertyIsExternal(stored).isExternal
      case newNode: nodes.NewMethod => newNode.isExternal
    }
    def lineNumberEnd: Option[Int] = node match {
      case stored: nodes.StoredNode => new AccessPropertyLineNumberEnd(stored).lineNumberEnd
      case newNode: nodes.NewMethod => newNode.lineNumberEnd
    }
    def offset: Option[Int] = node match {
      case stored: nodes.StoredNode => new AccessPropertyOffset(stored).offset
      case newNode: nodes.NewMethod => newNode.offset
    }
    def offsetEnd: Option[Int] = node match {
      case stored: nodes.StoredNode => new AccessPropertyOffsetEnd(stored).offsetEnd
      case newNode: nodes.NewMethod => newNode.offsetEnd
    }
    def signature: String = node match {
      case stored: nodes.StoredNode => new AccessPropertySignature(stored).signature
      case newNode: nodes.NewMethod => newNode.signature
    }
  }
  final class AccessMethodparameterinBase(val node: nodes.MethodParameterInBase) extends AnyVal {
    def closureBindingId: Option[String] = node match {
      case stored: nodes.StoredNode            => new AccessPropertyClosureBindingId(stored).closureBindingId
      case newNode: nodes.NewMethodParameterIn => newNode.closureBindingId
    }
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewMethodParameterIn => newNode.dynamicTypeHintFullName
    }
    def evaluationStrategy: String = node match {
      case stored: nodes.StoredNode            => new AccessPropertyEvaluationStrategy(stored).evaluationStrategy
      case newNode: nodes.NewMethodParameterIn => newNode.evaluationStrategy
    }
    def index: Int = node match {
      case stored: nodes.StoredNode            => new AccessPropertyIndex(stored).index
      case newNode: nodes.NewMethodParameterIn => newNode.index
    }
    def isVariadic: Boolean = node match {
      case stored: nodes.StoredNode            => new AccessPropertyIsVariadic(stored).isVariadic
      case newNode: nodes.NewMethodParameterIn => newNode.isVariadic
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode            => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewMethodParameterIn => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode            => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewMethodParameterIn => newNode.typeFullName
    }
  }
  final class AccessMethodparameteroutBase(val node: nodes.MethodParameterOutBase) extends AnyVal {
    def evaluationStrategy: String = node match {
      case stored: nodes.StoredNode             => new AccessPropertyEvaluationStrategy(stored).evaluationStrategy
      case newNode: nodes.NewMethodParameterOut => newNode.evaluationStrategy
    }
    def index: Int = node match {
      case stored: nodes.StoredNode             => new AccessPropertyIndex(stored).index
      case newNode: nodes.NewMethodParameterOut => newNode.index
    }
    def isVariadic: Boolean = node match {
      case stored: nodes.StoredNode             => new AccessPropertyIsVariadic(stored).isVariadic
      case newNode: nodes.NewMethodParameterOut => newNode.isVariadic
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode             => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewMethodParameterOut => newNode.typeFullName
    }
  }
  final class AccessMethodrefBase(val node: nodes.MethodRefBase) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode    => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewMethodRef => newNode.dynamicTypeHintFullName
    }
    def methodFullName: String = node match {
      case stored: nodes.StoredNode    => new AccessPropertyMethodFullName(stored).methodFullName
      case newNode: nodes.NewMethodRef => newNode.methodFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode    => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewMethodRef => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode    => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewMethodRef => newNode.typeFullName
    }
  }
  final class AccessMethodreturnBase(val node: nodes.MethodReturnBase) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode       => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewMethodReturn => newNode.dynamicTypeHintFullName
    }
    def evaluationStrategy: String = node match {
      case stored: nodes.StoredNode       => new AccessPropertyEvaluationStrategy(stored).evaluationStrategy
      case newNode: nodes.NewMethodReturn => newNode.evaluationStrategy
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode       => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewMethodReturn => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode       => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewMethodReturn => newNode.typeFullName
    }
  }
  final class AccessModifierBase(val node: nodes.ModifierBase) extends AnyVal {
    def modifierType: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyModifierType(stored).modifierType
      case newNode: nodes.NewModifier => newNode.modifierType
    }
  }
  final class AccessNamespaceBase(val node: nodes.NamespaceBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode    => new AccessPropertyName(stored).name
      case newNode: nodes.NewNamespace => newNode.name
    }
  }
  final class AccessNamespaceblockBase(val node: nodes.NamespaceBlockBase) extends AnyVal {
    def filename: String = node match {
      case stored: nodes.StoredNode         => new AccessPropertyFilename(stored).filename
      case newNode: nodes.NewNamespaceBlock => newNode.filename
    }
    def fullName: String = node match {
      case stored: nodes.StoredNode         => new AccessPropertyFullName(stored).fullName
      case newNode: nodes.NewNamespaceBlock => newNode.fullName
    }
    def name: String = node match {
      case stored: nodes.StoredNode         => new AccessPropertyName(stored).name
      case newNode: nodes.NewNamespaceBlock => newNode.name
    }
  }
  final class AccessReturnBase(val node: nodes.ReturnBase) extends AnyVal {}
  final class AccessTagBase(val node: nodes.TagBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyName(stored).name
      case newNode: nodes.NewTag    => newNode.name
    }
    def value: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyValue(stored).value
      case newNode: nodes.NewTag    => newNode.value
    }
  }
  final class AccessTagnodepairBase(val node: nodes.TagNodePairBase) extends AnyVal {}
  final class AccessTemplatedomBase(val node: nodes.TemplateDomBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode      => new AccessPropertyName(stored).name
      case newNode: nodes.NewTemplateDom => newNode.name
    }
  }
  final class AccessTypeBase(val node: nodes.TypeBase) extends AnyVal {
    def fullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyFullName(stored).fullName
      case newNode: nodes.NewType   => newNode.fullName
    }
    def name: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyName(stored).name
      case newNode: nodes.NewType   => newNode.name
    }
    def typeDeclFullName: String = node match {
      case stored: nodes.StoredNode => new AccessPropertyTypeDeclFullName(stored).typeDeclFullName
      case newNode: nodes.NewType   => newNode.typeDeclFullName
    }
  }
  final class AccessTypeargumentBase(val node: nodes.TypeArgumentBase) extends AnyVal {}
  final class AccessTypedeclBase(val node: nodes.TypeDeclBase) extends AnyVal {
    def aliasTypeFullName: Option[String] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyAliasTypeFullName(stored).aliasTypeFullName
      case newNode: nodes.NewTypeDecl => newNode.aliasTypeFullName
    }
    def astParentFullName: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyAstParentFullName(stored).astParentFullName
      case newNode: nodes.NewTypeDecl => newNode.astParentFullName
    }
    def astParentType: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyAstParentType(stored).astParentType
      case newNode: nodes.NewTypeDecl => newNode.astParentType
    }
    def filename: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyFilename(stored).filename
      case newNode: nodes.NewTypeDecl => newNode.filename
    }
    def fullName: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyFullName(stored).fullName
      case newNode: nodes.NewTypeDecl => newNode.fullName
    }
    def inheritsFromTypeFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyInheritsFromTypeFullName(stored).inheritsFromTypeFullName
      case newNode: nodes.NewTypeDecl => newNode.inheritsFromTypeFullName
    }
    def isExternal: Boolean = node match {
      case stored: nodes.StoredNode   => new AccessPropertyIsExternal(stored).isExternal
      case newNode: nodes.NewTypeDecl => newNode.isExternal
    }
    def name: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyName(stored).name
      case newNode: nodes.NewTypeDecl => newNode.name
    }
    def offset: Option[Int] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyOffset(stored).offset
      case newNode: nodes.NewTypeDecl => newNode.offset
    }
    def offsetEnd: Option[Int] = node match {
      case stored: nodes.StoredNode   => new AccessPropertyOffsetEnd(stored).offsetEnd
      case newNode: nodes.NewTypeDecl => newNode.offsetEnd
    }
  }
  final class AccessTypeparameterBase(val node: nodes.TypeParameterBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode        => new AccessPropertyName(stored).name
      case newNode: nodes.NewTypeParameter => newNode.name
    }
  }
  final class AccessTyperefBase(val node: nodes.TypeRefBase) extends AnyVal {
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewTypeRef => newNode.dynamicTypeHintFullName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewTypeRef => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewTypeRef => newNode.typeFullName
    }
  }
  final class AccessUnknownBase(val node: nodes.UnknownBase) extends AnyVal {
    def containedRef: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyContainedRef(stored).containedRef
      case newNode: nodes.NewUnknown => newNode.containedRef
    }
    def dynamicTypeHintFullName: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyDynamicTypeHintFullName(stored).dynamicTypeHintFullName
      case newNode: nodes.NewUnknown => newNode.dynamicTypeHintFullName
    }
    def parserTypeName: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyParserTypeName(stored).parserTypeName
      case newNode: nodes.NewUnknown => newNode.parserTypeName
    }
    def possibleTypes: IndexedSeq[String] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyPossibleTypes(stored).possibleTypes
      case newNode: nodes.NewUnknown => newNode.possibleTypes
    }
    def typeFullName: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyTypeFullName(stored).typeFullName
      case newNode: nodes.NewUnknown => newNode.typeFullName
    }
  }
  final class AccessAstnodeBase(val node: nodes.AstNodeBase) extends AnyVal {
    def code: String = node match {
      case stored: nodes.StoredNode  => new AccessPropertyCode(stored).code
      case newNode: nodes.AstNodeNew => newNode.code
    }
    def columnNumber: Option[Int] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyColumnNumber(stored).columnNumber
      case newNode: nodes.AstNodeNew => newNode.columnNumber
    }
    def lineNumber: Option[Int] = node match {
      case stored: nodes.StoredNode  => new AccessPropertyLineNumber(stored).lineNumber
      case newNode: nodes.AstNodeNew => newNode.lineNumber
    }
    def order: Int = node match {
      case stored: nodes.StoredNode  => new AccessPropertyOrder(stored).order
      case newNode: nodes.AstNodeNew => newNode.order
    }
  }
  final class AccessCallreprBase(val node: nodes.CallReprBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertyName(stored).name
      case newNode: nodes.CallReprNew => newNode.name
    }
    def signature: String = node match {
      case stored: nodes.StoredNode   => new AccessPropertySignature(stored).signature
      case newNode: nodes.CallReprNew => newNode.signature
    }
  }
  final class AccessCfgnodeBase(val node: nodes.CfgNodeBase) extends AnyVal {}
  final class AccessExpressionBase(val node: nodes.ExpressionBase) extends AnyVal {
    def argumentIndex: Int = node match {
      case stored: nodes.StoredNode     => new AccessPropertyArgumentIndex(stored).argumentIndex
      case newNode: nodes.ExpressionNew => newNode.argumentIndex
    }
    def argumentName: Option[String] = node match {
      case stored: nodes.StoredNode     => new AccessPropertyArgumentName(stored).argumentName
      case newNode: nodes.ExpressionNew => newNode.argumentName
    }
  }
  final class AccessDeclarationBase(val node: nodes.DeclarationBase) extends AnyVal {
    def name: String = node match {
      case stored: nodes.StoredNode      => new AccessPropertyName(stored).name
      case newNode: nodes.DeclarationNew => newNode.name
    }
  }
  /* accessors for base nodes end */
}

import Accessors.*
trait ConcreteStoredConversions extends ConcreteBaseConversions {
  implicit def accessPropertyAliasTypeFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasAliasTypeFullNameEMT]
  ): AccessPropertyAliasTypeFullName = new AccessPropertyAliasTypeFullName(node)
  implicit def accessPropertyArgumentIndex(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasArgumentIndexEMT]
  ): AccessPropertyArgumentIndex = new AccessPropertyArgumentIndex(node)
  implicit def accessPropertyArgumentName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasArgumentNameEMT]
  ): AccessPropertyArgumentName = new AccessPropertyArgumentName(node)
  implicit def accessPropertyAstParentFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasAstParentFullNameEMT]
  ): AccessPropertyAstParentFullName = new AccessPropertyAstParentFullName(node)
  implicit def accessPropertyAstParentType(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasAstParentTypeEMT]
  ): AccessPropertyAstParentType = new AccessPropertyAstParentType(node)
  implicit def accessPropertyCanonicalName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasCanonicalNameEMT]
  ): AccessPropertyCanonicalName = new AccessPropertyCanonicalName(node)
  implicit def accessPropertyClassName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasClassNameEMT]
  ): AccessPropertyClassName = new AccessPropertyClassName(node)
  implicit def accessPropertyClassShortName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasClassShortNameEMT]
  ): AccessPropertyClassShortName = new AccessPropertyClassShortName(node)
  implicit def accessPropertyClosureBindingId(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasClosureBindingIdEMT]
  ): AccessPropertyClosureBindingId = new AccessPropertyClosureBindingId(node)
  implicit def accessPropertyClosureOriginalName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasClosureOriginalNameEMT]
  ): AccessPropertyClosureOriginalName = new AccessPropertyClosureOriginalName(node)
  implicit def accessPropertyCode(node: nodes.StoredNode & nodes.StaticType[nodes.HasCodeEMT]): AccessPropertyCode =
    new AccessPropertyCode(node)
  implicit def accessPropertyColumnNumber(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasColumnNumberEMT]
  ): AccessPropertyColumnNumber = new AccessPropertyColumnNumber(node)
  implicit def accessPropertyColumnNumberEnd(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasColumnNumberEndEMT]
  ): AccessPropertyColumnNumberEnd = new AccessPropertyColumnNumberEnd(node)
  implicit def accessPropertyContainedRef(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasContainedRefEMT]
  ): AccessPropertyContainedRef = new AccessPropertyContainedRef(node)
  implicit def accessPropertyContent(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasContentEMT]
  ): AccessPropertyContent = new AccessPropertyContent(node)
  implicit def accessPropertyControlStructureType(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasControlStructureTypeEMT]
  ): AccessPropertyControlStructureType = new AccessPropertyControlStructureType(node)
  implicit def accessPropertyDependencyGroupId(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasDependencyGroupIdEMT]
  ): AccessPropertyDependencyGroupId = new AccessPropertyDependencyGroupId(node)
  implicit def accessPropertyDispatchType(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasDispatchTypeEMT]
  ): AccessPropertyDispatchType = new AccessPropertyDispatchType(node)
  implicit def accessPropertyDynamicTypeHintFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasDynamicTypeHintFullNameEMT]
  ): AccessPropertyDynamicTypeHintFullName = new AccessPropertyDynamicTypeHintFullName(node)
  implicit def accessPropertyEvaluationStrategy(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasEvaluationStrategyEMT]
  ): AccessPropertyEvaluationStrategy = new AccessPropertyEvaluationStrategy(node)
  implicit def accessPropertyExplicitAs(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasExplicitAsEMT]
  ): AccessPropertyExplicitAs = new AccessPropertyExplicitAs(node)
  implicit def accessPropertyFilename(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasFilenameEMT]
  ): AccessPropertyFilename = new AccessPropertyFilename(node)
  implicit def accessPropertyFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasFullNameEMT]
  ): AccessPropertyFullName = new AccessPropertyFullName(node)
  implicit def accessPropertyHash(node: nodes.StoredNode & nodes.StaticType[nodes.HasHashEMT]): AccessPropertyHash =
    new AccessPropertyHash(node)
  implicit def accessPropertyImportedAs(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasImportedAsEMT]
  ): AccessPropertyImportedAs = new AccessPropertyImportedAs(node)
  implicit def accessPropertyImportedEntity(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasImportedEntityEMT]
  ): AccessPropertyImportedEntity = new AccessPropertyImportedEntity(node)
  implicit def accessPropertyIndex(node: nodes.StoredNode & nodes.StaticType[nodes.HasIndexEMT]): AccessPropertyIndex =
    new AccessPropertyIndex(node)
  implicit def accessPropertyInheritsFromTypeFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasInheritsFromTypeFullNameEMT]
  ): AccessPropertyInheritsFromTypeFullName = new AccessPropertyInheritsFromTypeFullName(node)
  implicit def accessPropertyIsExplicit(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasIsExplicitEMT]
  ): AccessPropertyIsExplicit = new AccessPropertyIsExplicit(node)
  implicit def accessPropertyIsExternal(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasIsExternalEMT]
  ): AccessPropertyIsExternal = new AccessPropertyIsExternal(node)
  implicit def accessPropertyIsVariadic(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasIsVariadicEMT]
  ): AccessPropertyIsVariadic = new AccessPropertyIsVariadic(node)
  implicit def accessPropertyIsWildcard(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasIsWildcardEMT]
  ): AccessPropertyIsWildcard = new AccessPropertyIsWildcard(node)
  implicit def accessPropertyKey(node: nodes.StoredNode & nodes.StaticType[nodes.HasKeyEMT]): AccessPropertyKey =
    new AccessPropertyKey(node)
  implicit def accessPropertyLanguage(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasLanguageEMT]
  ): AccessPropertyLanguage = new AccessPropertyLanguage(node)
  implicit def accessPropertyLineNumber(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasLineNumberEMT]
  ): AccessPropertyLineNumber = new AccessPropertyLineNumber(node)
  implicit def accessPropertyLineNumberEnd(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasLineNumberEndEMT]
  ): AccessPropertyLineNumberEnd = new AccessPropertyLineNumberEnd(node)
  implicit def accessPropertyMethodFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasMethodFullNameEMT]
  ): AccessPropertyMethodFullName = new AccessPropertyMethodFullName(node)
  implicit def accessPropertyMethodShortName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasMethodShortNameEMT]
  ): AccessPropertyMethodShortName = new AccessPropertyMethodShortName(node)
  implicit def accessPropertyModifierType(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasModifierTypeEMT]
  ): AccessPropertyModifierType = new AccessPropertyModifierType(node)
  implicit def accessPropertyName(node: nodes.StoredNode & nodes.StaticType[nodes.HasNameEMT]): AccessPropertyName =
    new AccessPropertyName(node)
  implicit def accessPropertyNodeLabel(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasNodeLabelEMT]
  ): AccessPropertyNodeLabel = new AccessPropertyNodeLabel(node)
  implicit def accessPropertyOffset(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasOffsetEMT]
  ): AccessPropertyOffset = new AccessPropertyOffset(node)
  implicit def accessPropertyOffsetEnd(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasOffsetEndEMT]
  ): AccessPropertyOffsetEnd = new AccessPropertyOffsetEnd(node)
  implicit def accessPropertyOrder(node: nodes.StoredNode & nodes.StaticType[nodes.HasOrderEMT]): AccessPropertyOrder =
    new AccessPropertyOrder(node)
  implicit def accessPropertyOverlays(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasOverlaysEMT]
  ): AccessPropertyOverlays = new AccessPropertyOverlays(node)
  implicit def accessPropertyPackageName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasPackageNameEMT]
  ): AccessPropertyPackageName = new AccessPropertyPackageName(node)
  implicit def accessPropertyParserTypeName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasParserTypeNameEMT]
  ): AccessPropertyParserTypeName = new AccessPropertyParserTypeName(node)
  implicit def accessPropertyPossibleTypes(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasPossibleTypesEMT]
  ): AccessPropertyPossibleTypes = new AccessPropertyPossibleTypes(node)
  implicit def accessPropertyRoot(node: nodes.StoredNode & nodes.StaticType[nodes.HasRootEMT]): AccessPropertyRoot =
    new AccessPropertyRoot(node)
  implicit def accessPropertySignature(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasSignatureEMT]
  ): AccessPropertySignature = new AccessPropertySignature(node)
  implicit def accessPropertySymbol(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasSymbolEMT]
  ): AccessPropertySymbol = new AccessPropertySymbol(node)
  implicit def accessPropertyTypeDeclFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasTypeDeclFullNameEMT]
  ): AccessPropertyTypeDeclFullName = new AccessPropertyTypeDeclFullName(node)
  implicit def accessPropertyTypeFullName(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasTypeFullNameEMT]
  ): AccessPropertyTypeFullName = new AccessPropertyTypeFullName(node)
  implicit def accessPropertyValue(node: nodes.StoredNode & nodes.StaticType[nodes.HasValueEMT]): AccessPropertyValue =
    new AccessPropertyValue(node)
  implicit def accessPropertyVersion(
    node: nodes.StoredNode & nodes.StaticType[nodes.HasVersionEMT]
  ): AccessPropertyVersion = new AccessPropertyVersion(node)
}

trait ConcreteBaseConversions extends AbstractBaseConversions0 {
  implicit def accessAnnotationbase(node: nodes.AnnotationBase): AccessAnnotationBase = new AccessAnnotationBase(node)
  implicit def accessAnnotationliteralbase(node: nodes.AnnotationLiteralBase): AccessAnnotationliteralBase =
    new AccessAnnotationliteralBase(node)
  implicit def accessAnnotationparameterbase(node: nodes.AnnotationParameterBase): AccessAnnotationparameterBase =
    new AccessAnnotationparameterBase(node)
  implicit def accessAnnotationparameterassignbase(
    node: nodes.AnnotationParameterAssignBase
  ): AccessAnnotationparameterassignBase = new AccessAnnotationparameterassignBase(node)
  implicit def accessArrayinitializerbase(node: nodes.ArrayInitializerBase): AccessArrayinitializerBase =
    new AccessArrayinitializerBase(node)
  implicit def accessBindingbase(node: nodes.BindingBase): AccessBindingBase = new AccessBindingBase(node)
  implicit def accessBlockbase(node: nodes.BlockBase): AccessBlockBase       = new AccessBlockBase(node)
  implicit def accessCallbase(node: nodes.CallBase): AccessCallBase          = new AccessCallBase(node)
  implicit def accessClosurebindingbase(node: nodes.ClosureBindingBase): AccessClosurebindingBase =
    new AccessClosurebindingBase(node)
  implicit def accessCommentbase(node: nodes.CommentBase): AccessCommentBase          = new AccessCommentBase(node)
  implicit def accessConfigfilebase(node: nodes.ConfigFileBase): AccessConfigfileBase = new AccessConfigfileBase(node)
  implicit def accessControlstructurebase(node: nodes.ControlStructureBase): AccessControlstructureBase =
    new AccessControlstructureBase(node)
  implicit def accessDependencybase(node: nodes.DependencyBase): AccessDependencyBase = new AccessDependencyBase(node)
  implicit def accessFieldidentifierbase(node: nodes.FieldIdentifierBase): AccessFieldidentifierBase =
    new AccessFieldidentifierBase(node)
  implicit def accessFilebase(node: nodes.FileBase): AccessFileBase                   = new AccessFileBase(node)
  implicit def accessFindingbase(node: nodes.FindingBase): AccessFindingBase          = new AccessFindingBase(node)
  implicit def accessIdentifierbase(node: nodes.IdentifierBase): AccessIdentifierBase = new AccessIdentifierBase(node)
  implicit def accessImportbase(node: nodes.ImportBase): AccessImportBase             = new AccessImportBase(node)
  implicit def accessJumplabelbase(node: nodes.JumpLabelBase): AccessJumplabelBase    = new AccessJumplabelBase(node)
  implicit def accessJumptargetbase(node: nodes.JumpTargetBase): AccessJumptargetBase = new AccessJumptargetBase(node)
  implicit def accessKeyvaluepairbase(node: nodes.KeyValuePairBase): AccessKeyvaluepairBase =
    new AccessKeyvaluepairBase(node)
  implicit def accessLiteralbase(node: nodes.LiteralBase): AccessLiteralBase    = new AccessLiteralBase(node)
  implicit def accessLocalbase(node: nodes.LocalBase): AccessLocalBase          = new AccessLocalBase(node)
  implicit def accessLocationbase(node: nodes.LocationBase): AccessLocationBase = new AccessLocationBase(node)
  implicit def accessMemberbase(node: nodes.MemberBase): AccessMemberBase       = new AccessMemberBase(node)
  implicit def accessMetadatabase(node: nodes.MetaDataBase): AccessMetadataBase = new AccessMetadataBase(node)
  implicit def accessMethodbase(node: nodes.MethodBase): AccessMethodBase       = new AccessMethodBase(node)
  implicit def accessMethodparameterinbase(node: nodes.MethodParameterInBase): AccessMethodparameterinBase =
    new AccessMethodparameterinBase(node)
  implicit def accessMethodparameteroutbase(node: nodes.MethodParameterOutBase): AccessMethodparameteroutBase =
    new AccessMethodparameteroutBase(node)
  implicit def accessMethodrefbase(node: nodes.MethodRefBase): AccessMethodrefBase = new AccessMethodrefBase(node)
  implicit def accessMethodreturnbase(node: nodes.MethodReturnBase): AccessMethodreturnBase =
    new AccessMethodreturnBase(node)
  implicit def accessModifierbase(node: nodes.ModifierBase): AccessModifierBase    = new AccessModifierBase(node)
  implicit def accessNamespacebase(node: nodes.NamespaceBase): AccessNamespaceBase = new AccessNamespaceBase(node)
  implicit def accessNamespaceblockbase(node: nodes.NamespaceBlockBase): AccessNamespaceblockBase =
    new AccessNamespaceblockBase(node)
  implicit def accessReturnbase(node: nodes.ReturnBase): AccessReturnBase = new AccessReturnBase(node)
  implicit def accessTagbase(node: nodes.TagBase): AccessTagBase          = new AccessTagBase(node)
  implicit def accessTagnodepairbase(node: nodes.TagNodePairBase): AccessTagnodepairBase = new AccessTagnodepairBase(
    node
  )
  implicit def accessTemplatedombase(node: nodes.TemplateDomBase): AccessTemplatedomBase = new AccessTemplatedomBase(
    node
  )
  implicit def accessTypebase(node: nodes.TypeBase): AccessTypeBase = new AccessTypeBase(node)
  implicit def accessTypeargumentbase(node: nodes.TypeArgumentBase): AccessTypeargumentBase =
    new AccessTypeargumentBase(node)
  implicit def accessTypedeclbase(node: nodes.TypeDeclBase): AccessTypedeclBase = new AccessTypedeclBase(node)
  implicit def accessTypeparameterbase(node: nodes.TypeParameterBase): AccessTypeparameterBase =
    new AccessTypeparameterBase(node)
  implicit def accessTyperefbase(node: nodes.TypeRefBase): AccessTyperefBase = new AccessTyperefBase(node)
  implicit def accessUnknownbase(node: nodes.UnknownBase): AccessUnknownBase = new AccessUnknownBase(node)
}

trait AbstractBaseConversions0 extends AbstractBaseConversions1 {
  implicit def accessAstnodebase(node: nodes.AstNodeBase): AccessAstnodeBase          = new AccessAstnodeBase(node)
  implicit def accessCallreprbase(node: nodes.CallReprBase): AccessCallreprBase       = new AccessCallreprBase(node)
  implicit def accessCfgnodebase(node: nodes.CfgNodeBase): AccessCfgnodeBase          = new AccessCfgnodeBase(node)
  implicit def accessExpressionbase(node: nodes.ExpressionBase): AccessExpressionBase = new AccessExpressionBase(node)
}

trait AbstractBaseConversions1 {
  implicit def accessDeclarationbase(node: nodes.DeclarationBase): AccessDeclarationBase = new AccessDeclarationBase(
    node
  )
}
