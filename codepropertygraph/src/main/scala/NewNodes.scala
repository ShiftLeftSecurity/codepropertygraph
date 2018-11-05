
    package io.shiftleft.codepropertygraph.generated.nodes

    import java.lang.{Boolean => JBoolean, Long => JLong}
    import java.util.{Map => JMap, Set => JSet}

    /** base type for all nodes that can be added to a graph, e.g. the diffgraph */
    trait NewNode extends Node {
      def label: String
      def properties: Map[String, Any]
      def containedNodesByLocalName: Map[String, List[Node]]
      def allContainedNodes: List[Node] = containedNodesByLocalName.values.flatten.toList
    }
    

      case class NewArrayInitializer() extends NewNode with ArrayInitializerBase {
        override val label = "ARRAY_INITIALIZER"
        override val properties: Map[String, Any] = Map.empty
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewBlock(lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with BlockBase {
        override val label = "BLOCK"
        override val properties: Map[String, Any] = 
            Map(("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewCall(code: String, name: String, order: Integer, argumentIndex: Integer, dispatchType: String, signature: String, typeFullName: String, methodInstFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with CallBase {
        override val label = "CALL"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("NAME" -> name ),
("ORDER" -> order ),
("ARGUMENT_INDEX" -> argumentIndex ),
("DISPATCH_TYPE" -> dispatchType ),
("SIGNATURE" -> signature ),
("TYPE_FULL_NAME" -> typeFullName ),
("METHOD_INST_FULL_NAME" -> methodInstFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewClosureBinding(closureBindingId: Option[String], evaluationStrategy: String) extends NewNode with ClosureBindingBase {
        override val label = "CLOSURE_BINDING"
        override val properties: Map[String, Any] = 
            Map(("CLOSURE_BINDING_ID" -> closureBindingId ),
("EVALUATION_STRATEGY" -> evaluationStrategy )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewFile(name: String) extends NewNode with FileBase {
        override val label = "FILE"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewIdentifier(code: String, name: String, order: Integer, argumentIndex: Integer, typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with IdentifierBase {
        override val label = "IDENTIFIER"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("NAME" -> name ),
("ORDER" -> order ),
("ARGUMENT_INDEX" -> argumentIndex ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewLiteral(code: String, name: String, order: Integer, argumentIndex: Integer, typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with LiteralBase {
        override val label = "LITERAL"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("NAME" -> name ),
("ORDER" -> order ),
("ARGUMENT_INDEX" -> argumentIndex ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewLocal(code: String, name: String, closureBindingId: Option[String], typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with LocalBase {
        override val label = "LOCAL"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("NAME" -> name ),
("CLOSURE_BINDING_ID" -> closureBindingId ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMember(code: String, name: String, typeFullName: String) extends NewNode with MemberBase {
        override val label = "MEMBER"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("NAME" -> name ),
("TYPE_FULL_NAME" -> typeFullName )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMetaData(language: String, version: String) extends NewNode with MetaDataBase {
        override val label = "META_DATA"
        override val properties: Map[String, Any] = 
            Map(("LANGUAGE" -> language ),
("VERSION" -> version )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMethod(name: String, fullName: String, signature: String, astParentType: String, astParentFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with MethodBase {
        override val label = "METHOD"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("FULL_NAME" -> fullName ),
("SIGNATURE" -> signature ),
("AST_PARENT_TYPE" -> astParentType ),
("AST_PARENT_FULL_NAME" -> astParentFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMethodInst(name: String, fullName: String, signature: String, methodFullName: String) extends NewNode with MethodInstBase {
        override val label = "METHOD_INST"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("FULL_NAME" -> fullName ),
("SIGNATURE" -> signature ),
("METHOD_FULL_NAME" -> methodFullName )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMethodParameterIn(code: String, order: Integer, name: String, evaluationStrategy: String, typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with MethodParameterInBase {
        override val label = "METHOD_PARAMETER_IN"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("ORDER" -> order ),
("NAME" -> name ),
("EVALUATION_STRATEGY" -> evaluationStrategy ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMethodParameterOut(code: String, order: Integer, name: String, evaluationStrategy: String, typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with MethodParameterOutBase {
        override val label = "METHOD_PARAMETER_OUT"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("ORDER" -> order ),
("NAME" -> name ),
("EVALUATION_STRATEGY" -> evaluationStrategy ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMethodRef(code: String, order: Integer, argumentIndex: Integer, methodInstFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with MethodRefBase {
        override val label = "METHOD_REF"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("ORDER" -> order ),
("ARGUMENT_INDEX" -> argumentIndex ),
("METHOD_INST_FULL_NAME" -> methodInstFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewMethodReturn(code: String, evaluationStrategy: String, typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with MethodReturnBase {
        override val label = "METHOD_RETURN"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("EVALUATION_STRATEGY" -> evaluationStrategy ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewModifier(modifierType: String) extends NewNode with ModifierBase {
        override val label = "MODIFIER"
        override val properties: Map[String, Any] = 
            Map(("MODIFIER_TYPE" -> modifierType )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewNamespace(name: String) extends NewNode with NamespaceBase {
        override val label = "NAMESPACE"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewNamespaceBlock(name: String, fullName: String) extends NewNode with NamespaceBlockBase {
        override val label = "NAMESPACE_BLOCK"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("FULL_NAME" -> fullName )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewReturn(lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer], order: Integer, argumentIndex: Integer, code: String) extends NewNode with ReturnBase {
        override val label = "RETURN"
        override val properties: Map[String, Any] = 
            Map(("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd ),
("ORDER" -> order ),
("ARGUMENT_INDEX" -> argumentIndex ),
("CODE" -> code )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewTag(name: String, value: String) extends NewNode with TagBase {
        override val label = "TAG"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("VALUE" -> value )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewType(name: String, fullName: String, typeDeclFullName: String) extends NewNode with TypeBase {
        override val label = "TYPE"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("FULL_NAME" -> fullName ),
("TYPE_DECL_FULL_NAME" -> typeDeclFullName )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewTypeArgument() extends NewNode with TypeArgumentBase {
        override val label = "TYPE_ARGUMENT"
        override val properties: Map[String, Any] = Map.empty
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewTypeDecl(name: String, fullName: String, isExternal: JBoolean, inheritsFromTypeFullName: List[String], astParentType: String, astParentFullName: String) extends NewNode with TypeDeclBase {
        override val label = "TYPE_DECL"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("FULL_NAME" -> fullName ),
("IS_EXTERNAL" -> isExternal ),
("INHERITS_FROM_TYPE_FULL_NAME" -> inheritsFromTypeFullName ),
("AST_PARENT_TYPE" -> astParentType ),
("AST_PARENT_FULL_NAME" -> astParentFullName )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewTypeParameter(name: String, order: Integer) extends NewNode with TypeParameterBase {
        override val label = "TYPE_PARAMETER"
        override val properties: Map[String, Any] = 
            Map(("NAME" -> name ),
("ORDER" -> order )).filterNot { case (k,v) =>
                v == null || v == None
              }
            
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      

      case class NewUnknown(code: String, parserTypeName: String, order: Integer, argumentIndex: Integer, typeFullName: String, lineNumber: Option[Integer], lineNumberEnd: Option[Integer], columnNumber: Option[Integer], columnNumberEnd: Option[Integer]) extends NewNode with UnknownBase {
        override val label = "UNKNOWN"
        override val properties: Map[String, Any] = 
            Map(("CODE" -> code ),
("PARSER_TYPE_NAME" -> parserTypeName ),
("ORDER" -> order ),
("ARGUMENT_INDEX" -> argumentIndex ),
("TYPE_FULL_NAME" -> typeFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
                v == null || v == None
              }
            .map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          
        override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty
      }
      
