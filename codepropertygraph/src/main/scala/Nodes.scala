
      package io.shiftleft.codepropertygraph.generated.nodes

      import io.shiftleft.codepropertygraph.generated
      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Iterator => JIterator, LinkedList => JLinkedList, List => JList, Map => JMap, Set => JSet}
      import gremlin.scala._
      import org.apache.tinkerpop.gremlin.structure.Direction
      import org.apache.tinkerpop.gremlin.structure.Edge
      import org.apache.tinkerpop.gremlin.structure.VertexProperty
      import org.apache.tinkerpop.gremlin.util.iterator.MultiIterator
      import scala.collection.JavaConverters._
      import shapeless.HNil

      trait Node extends gremlin.scala.dsl.DomainRoot

      trait StoredNode extends Node {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        def underlying: Vertex
      }

      trait HasEvalType extends StoredNode {
        def evalType: Type = {
          // this.vertices(Direction.OUT, generated.EdgeTypes.EVAL_TYPE).next.asInstanceOf[Type]
          ???
        }
      }
      trait DeclarationBase extends Node with HasName 
                trait Declaration extends StoredNode with DeclarationBase
            
trait DataFlowObjectBase extends Node with HasCode with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd 
                trait DataFlowObject extends StoredNode with DataFlowObjectBase
            
trait ExpressionBase extends Node with HasOrder with DataFlowObjectBase
                trait Expression extends DataFlowObject with ExpressionBase
            
trait LocalLikeBase extends Node with HasName 
                trait LocalLike extends StoredNode with LocalLikeBase
            trait HasArgumentIndex { def argumentIndex: Integer }
trait HasAstParentFullName { def astParentFullName: String }
trait HasAstParentType { def astParentType: String }
trait HasClosureBindingId { def closureBindingId: Option[String] }
trait HasCode { def code: String }
trait HasColumnNumber { def columnNumber: Option[Integer] }
trait HasColumnNumberEnd { def columnNumberEnd: Option[Integer] }
trait HasDispatchType { def dispatchType: String }
trait HasEvaluationStrategy { def evaluationStrategy: String }
trait HasFullName { def fullName: String }
trait HasInheritsFromTypeFullName { def inheritsFromTypeFullName: List[String] }
trait HasIsExternal { def isExternal: JBoolean }
trait HasLanguage { def language: String }
trait HasLineNumber { def lineNumber: Option[Integer] }
trait HasLineNumberEnd { def lineNumberEnd: Option[Integer] }
trait HasMethodFullName { def methodFullName: String }
trait HasMethodInstFullName { def methodInstFullName: String }
trait HasModifierType { def modifierType: String }
trait HasName { def name: String }
trait HasOrder { def order: Integer }
trait HasParserTypeName { def parserTypeName: String }
trait HasSignature { def signature: String }
trait HasTypeDeclFullName { def typeDeclFullName: String }
trait HasTypeFullName { def typeFullName: String }
trait HasValue { def value: String }
trait HasVersion { def version: String }

      trait ArrayInitializerBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class ArrayInitializer(@underlying _underlying: Option[Vertex] )
          extends StoredNode   with ArrayInitializerBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        
      }
      

      trait BlockBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Block(@underlying _underlying: Option[Vertex] , LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode  with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with BlockBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait CallBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Call(@underlying _underlying: Option[Vertex] , CODE: String, NAME: String, ORDER: Integer, ARGUMENT_INDEX: Integer, DISPATCH_TYPE: String, SIGNATURE: String, TYPE_FULL_NAME: String, METHOD_INST_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Expression with HasCode with HasName with HasOrder with HasArgumentIndex with HasDispatchType with HasSignature with HasTypeFullName with HasMethodInstFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with CallBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val name: String = NAME
val order: Integer = ORDER
val argumentIndex: Integer = ARGUMENT_INDEX
val dispatchType: String = DISPATCH_TYPE
val signature: String = SIGNATURE
val typeFullName: String = TYPE_FULL_NAME
val methodInstFullName: String = METHOD_INST_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait ClosureBindingBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class ClosureBinding(@underlying _underlying: Option[Vertex] , CLOSURE_BINDING_ID: Option[String], EVALUATION_STRATEGY: String)
          extends StoredNode  with HasClosureBindingId with HasEvaluationStrategy with ClosureBindingBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val closureBindingId: Option[String] = CLOSURE_BINDING_ID
val evaluationStrategy: String = EVALUATION_STRATEGY
      }
      

      trait FileBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class File(@underlying _underlying: Option[Vertex] , NAME: String)
          extends StoredNode  with HasName with FileBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
      }
      

      trait IdentifierBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Identifier(@underlying _underlying: Option[Vertex] , CODE: String, NAME: String, ORDER: Integer, ARGUMENT_INDEX: Integer, TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with DataFlowObject with Expression with LocalLike with HasCode with HasName with HasOrder with HasArgumentIndex with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with IdentifierBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val name: String = NAME
val order: Integer = ORDER
val argumentIndex: Integer = ARGUMENT_INDEX
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait LiteralBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Literal(@underlying _underlying: Option[Vertex] , CODE: String, NAME: String, ORDER: Integer, ARGUMENT_INDEX: Integer, TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Declaration with DataFlowObject with Expression with HasCode with HasName with HasOrder with HasArgumentIndex with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with LiteralBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val name: String = NAME
val order: Integer = ORDER
val argumentIndex: Integer = ARGUMENT_INDEX
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait LocalBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Local(@underlying _underlying: Option[Vertex] , CODE: String, NAME: String, CLOSURE_BINDING_ID: Option[String], TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Declaration with LocalLike with HasCode with HasName with HasClosureBindingId with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with LocalBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val name: String = NAME
val closureBindingId: Option[String] = CLOSURE_BINDING_ID
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait MemberBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Member(@underlying _underlying: Option[Vertex] , CODE: String, NAME: String, TYPE_FULL_NAME: String)
          extends StoredNode with Declaration with HasCode with HasName with HasTypeFullName with MemberBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val name: String = NAME
val typeFullName: String = TYPE_FULL_NAME
      }
      

      trait MetaDataBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class MetaData(@underlying _underlying: Option[Vertex] , LANGUAGE: String, VERSION: String)
          extends StoredNode  with HasLanguage with HasVersion with MetaDataBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val language: String = LANGUAGE
val version: String = VERSION
      }
      

      trait MethodBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Method(@underlying _underlying: Option[Vertex] , NAME: String, FULL_NAME: String, SIGNATURE: String, AST_PARENT_TYPE: String, AST_PARENT_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Declaration with HasName with HasFullName with HasSignature with HasAstParentType with HasAstParentFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with MethodBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val fullName: String = FULL_NAME
val signature: String = SIGNATURE
val astParentType: String = AST_PARENT_TYPE
val astParentFullName: String = AST_PARENT_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait MethodInstBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class MethodInst(@underlying _underlying: Option[Vertex] , NAME: String, FULL_NAME: String, SIGNATURE: String, METHOD_FULL_NAME: String)
          extends StoredNode  with HasName with HasFullName with HasSignature with HasMethodFullName with MethodInstBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val fullName: String = FULL_NAME
val signature: String = SIGNATURE
val methodFullName: String = METHOD_FULL_NAME
      }
      

      trait MethodParameterInBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class MethodParameterIn(@underlying _underlying: Option[Vertex] , CODE: String, ORDER: Integer, NAME: String, EVALUATION_STRATEGY: String, TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Declaration with DataFlowObject with LocalLike with HasCode with HasOrder with HasName with HasEvaluationStrategy with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with MethodParameterInBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val order: Integer = ORDER
val name: String = NAME
val evaluationStrategy: String = EVALUATION_STRATEGY
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait MethodParameterOutBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class MethodParameterOut(@underlying _underlying: Option[Vertex] , CODE: String, ORDER: Integer, NAME: String, EVALUATION_STRATEGY: String, TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Declaration with DataFlowObject with HasCode with HasOrder with HasName with HasEvaluationStrategy with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with MethodParameterOutBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val order: Integer = ORDER
val name: String = NAME
val evaluationStrategy: String = EVALUATION_STRATEGY
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait MethodRefBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class MethodRef(@underlying _underlying: Option[Vertex] , CODE: String, ORDER: Integer, ARGUMENT_INDEX: Integer, METHOD_INST_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Expression with HasCode with HasOrder with HasArgumentIndex with HasMethodInstFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with MethodRefBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val order: Integer = ORDER
val argumentIndex: Integer = ARGUMENT_INDEX
val methodInstFullName: String = METHOD_INST_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait MethodReturnBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class MethodReturn(@underlying _underlying: Option[Vertex] , CODE: String, EVALUATION_STRATEGY: String, TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with DataFlowObject with HasCode with HasEvaluationStrategy with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with MethodReturnBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val evaluationStrategy: String = EVALUATION_STRATEGY
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      

      trait ModifierBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Modifier(@underlying _underlying: Option[Vertex] , MODIFIER_TYPE: String)
          extends StoredNode  with HasModifierType with ModifierBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val modifierType: String = MODIFIER_TYPE
      }
      

      trait NamespaceBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Namespace(@underlying _underlying: Option[Vertex] , NAME: String)
          extends StoredNode  with HasName with NamespaceBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
      }
      

      trait NamespaceBlockBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class NamespaceBlock(@underlying _underlying: Option[Vertex] , NAME: String, FULL_NAME: String)
          extends StoredNode  with HasName with HasFullName with NamespaceBlockBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val fullName: String = FULL_NAME
      }
      

      trait ReturnBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Return(@underlying _underlying: Option[Vertex] , LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer], ORDER: Integer, ARGUMENT_INDEX: Integer, CODE: String)
          extends StoredNode with Expression with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with HasOrder with HasArgumentIndex with HasCode with ReturnBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
val order: Integer = ORDER
val argumentIndex: Integer = ARGUMENT_INDEX
val code: String = CODE
      }
      

      trait TagBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Tag(@underlying _underlying: Option[Vertex] , NAME: String, VALUE: String)
          extends StoredNode  with HasName with HasValue with TagBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val value: String = VALUE
      }
      

      trait TypeBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Type(@underlying _underlying: Option[Vertex] , NAME: String, FULL_NAME: String, TYPE_DECL_FULL_NAME: String)
          extends StoredNode  with HasName with HasFullName with HasTypeDeclFullName with TypeBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val fullName: String = FULL_NAME
val typeDeclFullName: String = TYPE_DECL_FULL_NAME
      }
      

      trait TypeArgumentBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class TypeArgument(@underlying _underlying: Option[Vertex] )
          extends StoredNode   with TypeArgumentBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        
      }
      

      trait TypeDeclBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class TypeDecl(@underlying _underlying: Option[Vertex] , NAME: String, FULL_NAME: String, IS_EXTERNAL: JBoolean, INHERITS_FROM_TYPE_FULL_NAME: List[String], AST_PARENT_TYPE: String, AST_PARENT_FULL_NAME: String)
          extends StoredNode  with HasName with HasFullName with HasIsExternal with HasInheritsFromTypeFullName with HasAstParentType with HasAstParentFullName with TypeDeclBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val fullName: String = FULL_NAME
val isExternal: JBoolean = IS_EXTERNAL
val inheritsFromTypeFullName: List[String] = INHERITS_FROM_TYPE_FULL_NAME
val astParentType: String = AST_PARENT_TYPE
val astParentFullName: String = AST_PARENT_FULL_NAME
      }
      

      trait TypeParameterBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class TypeParameter(@underlying _underlying: Option[Vertex] , NAME: String, ORDER: Integer)
          extends StoredNode  with HasName with HasOrder with TypeParameterBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val name: String = NAME
val order: Integer = ORDER
      }
      

      trait UnknownBase extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class Unknown(@underlying _underlying: Option[Vertex] , CODE: String, PARSER_TYPE_NAME: String, ORDER: Integer, ARGUMENT_INDEX: Integer, TYPE_FULL_NAME: String, LINE_NUMBER: Option[Integer], LINE_NUMBER_END: Option[Integer], COLUMN_NUMBER: Option[Integer], COLUMN_NUMBER_END: Option[Integer])
          extends StoredNode with Expression with HasCode with HasParserTypeName with HasOrder with HasArgumentIndex with HasTypeFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with UnknownBase {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get

        val code: String = CODE
val parserTypeName: String = PARSER_TYPE_NAME
val order: Integer = ORDER
val argumentIndex: Integer = ARGUMENT_INDEX
val typeFullName: String = TYPE_FULL_NAME
val lineNumber: Option[Integer] = LINE_NUMBER
val lineNumberEnd: Option[Integer] = LINE_NUMBER_END
val columnNumber: Option[Integer] = COLUMN_NUMBER
val columnNumberEnd: Option[Integer] = COLUMN_NUMBER_END
      }
      
