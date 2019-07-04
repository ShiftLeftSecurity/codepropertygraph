
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
    

//       case class NewAnnotation(code: String  ="" , name: String  ="" , fullName: String  ="" ) extends NewNode with AnnotationBase {
//         override val label = "ANNOTATION"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("FULL_NAME" -> fullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewAnnotationLiteral(code: String  ="" , name: String  ="" , order: Integer  = -1) extends NewNode with AnnotationLiteralBase {
//         override val label = "ANNOTATION_LITERAL"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("ORDER" -> order )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewAnnotationParameter(code: String  ="" ) extends NewNode with AnnotationParameterBase {
//         override val label = "ANNOTATION_PARAMETER"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewAnnotationParameterAssign(code: String  ="" ) extends NewNode with AnnotationParameterAssignBase {
//         override val label = "ANNOTATION_PARAMETER_ASSIGN"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewArrayInitializer(code: String  ="" ) extends NewNode with ArrayInitializerBase {
//         override val label = "ARRAY_INITIALIZER"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewBlock(code: String  ="" , order: Integer  = -1, argumentIndex: Integer  = -1, typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with BlockBase {
//         override val label = "BLOCK"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewCall(code: String  ="" , name: String  ="" , order: Integer  = -1, argumentIndex: Integer  = -1, dispatchType: String  ="" , signature: String  ="" , typeFullName: String  ="" , methodInstFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, resolved: Option[JBoolean]  = None, sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with CallBase {
//         override val label = "CALL"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("DISPATCH_TYPE" -> dispatchType ),
// ("SIGNATURE" -> signature ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("METHOD_INST_FULL_NAME" -> methodInstFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("RESOLVED" -> resolved ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewCallChain(val methods: List[MethodBase] = List(), val calls: List[CallBase] = List()) extends NewNode with CallChainBase {
//         override val label = "CALL_CHAIN"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("methods" -> methods) + ("calls" -> calls)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewCallSite(val method: MethodBase , val call: CallBase , val callerMethod: MethodBase ) extends NewNode with CallSiteBase {
//         override val label = "CALL_SITE"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("method" -> (method :: Nil)) + ("call" -> (call :: Nil)) + ("callerMethod" -> (callerMethod :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewClosureBinding(closureBindingId: Option[String]  = None, evaluationStrategy: String  ="" , closureOriginalName: Option[String]  = None) extends NewNode with ClosureBindingBase {
//         override val label = "CLOSURE_BINDING"
//         override val properties: Map[String, Any] = 
//             Map(("CLOSURE_BINDING_ID" -> closureBindingId ),
// ("EVALUATION_STRATEGY" -> evaluationStrategy ),
// ("CLOSURE_ORIGINAL_NAME" -> closureOriginalName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewComment(lineNumber: Option[Integer]  = None, code: String  ="" ) extends NewNode with CommentBase {
//         override val label = "COMMENT"
//         override val properties: Map[String, Any] = 
//             Map(("LINE_NUMBER" -> lineNumber ),
// ("CODE" -> code )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewConfigFile(name: String  ="" , content: String  ="" ) extends NewNode with ConfigFileBase {
//         override val label = "CONFIG_FILE"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("CONTENT" -> content )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewDependency(version: String  ="" , name: String  ="" , dependencyGroupId: Option[String]  = None) extends NewNode with DependencyBase {
//         override val label = "DEPENDENCY"
//         override val properties: Map[String, Any] = 
//             Map(("VERSION" -> version ),
// ("NAME" -> name ),
// ("DEPENDENCY_GROUP_ID" -> dependencyGroupId )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewDetachedTrackingPoint(val cfgNode: CfgNodeBase ) extends NewNode with DetachedTrackingPointBase {
//         override val label = "DETACHED_TRACKING_POINT"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("cfgNode" -> (cfgNode :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewFile(name: String  ="" ) extends NewNode with FileBase {
//         override val label = "FILE"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewFinding(name: String  ="" , category: String  ="" , title: String  ="" , description: String  ="" , score: Integer  = -1, link: String  ="" , vulnDescr: String  ="" , parameter: String  ="" , methodName: String  ="" , lineNo: String  ="" , filename: String  ="" , val ioflows: List[IoflowBase] = List(), val methods: List[MethodBase] = List()) extends NewNode with FindingBase {
//         override val label = "FINDING"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("CATEGORY" -> category ),
// ("TITLE" -> title ),
// ("DESCRIPTION" -> description ),
// ("SCORE" -> score ),
// ("LINK" -> link ),
// ("VULN_DESCR" -> vulnDescr ),
// ("PARAMETER" -> parameter ),
// ("METHOD_NAME" -> methodName ),
// ("LINE_NO" -> lineNo ),
// ("FILENAME" -> filename )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("ioflows" -> ioflows) + ("methods" -> methods)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewFlow(val points: List[ProgramPointBase] = List(), val source: SourceBase , val sink: SinkBase , val transformations: List[TransformationBase] = List(), val branchPoints: List[TrackingPointBase] = List(), val cfgNodes: List[CfgNodeBase] = List()) extends NewNode with FlowBase {
//         override val label = "FLOW"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("points" -> points) + ("source" -> (source :: Nil)) + ("sink" -> (sink :: Nil)) + ("transformations" -> transformations) + ("branchPoints" -> branchPoints) + ("cfgNodes" -> cfgNodes)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewFramework(name: String  ="" ) extends NewNode with FrameworkBase {
//         override val label = "FRAMEWORK"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewFrameworkData(name: String  ="" , content: String  ="" ) extends NewNode with FrameworkDataBase {
//         override val label = "FRAMEWORK_DATA"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("CONTENT" -> content )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewIdentifier(code: String  ="" , name: String  ="" , order: Integer  = -1, argumentIndex: Integer  = -1, typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with IdentifierBase {
//         override val label = "IDENTIFIER"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewIoflow(fingerprint: String  ="" , literalsToSink: List[String] = List(), val dataTags: List[TagBase] = List(), val sourceDescriptorTags: List[TagBase] = List(), val sinkDescriptorTags: List[TagBase] = List(), val source: SourceBase , val sink: SinkBase , val transforms: List[TransformBase] = List(), val sourceDescriptorFlows: List[FlowBase] = List(), val sinkDescriptorFlows: List[FlowBase] = List(), val primaryFlow: FlowBase , val triggerMethods: List[MethodBase] = List()) extends NewNode with IoflowBase {
//         override val label = "IOFLOW"
//         override val properties: Map[String, Any] = 
//             Map(("FINGERPRINT" -> fingerprint ),
// ("LITERALS_TO_SINK" -> literalsToSink )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("dataTags" -> dataTags) + ("sourceDescriptorTags" -> sourceDescriptorTags) + ("sinkDescriptorTags" -> sinkDescriptorTags) + ("source" -> (source :: Nil)) + ("sink" -> (sink :: Nil)) + ("transforms" -> transforms) + ("sourceDescriptorFlows" -> sourceDescriptorFlows) + ("sinkDescriptorFlows" -> sinkDescriptorFlows) + ("primaryFlow" -> (primaryFlow :: Nil)) + ("triggerMethods" -> triggerMethods)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewLiteral(code: String  ="" , name: String  ="" , order: Integer  = -1, argumentIndex: Integer  = -1, typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with LiteralBase {
//         override val label = "LITERAL"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewLocal(code: String  ="" , name: String  ="" , closureBindingId: Option[String]  = None, typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None) extends NewNode with LocalBase {
//         override val label = "LOCAL"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("CLOSURE_BINDING_ID" -> closureBindingId ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewLocation(symbol: String  ="" , methodFullName: String  ="" , methodShortName: String  ="" , packageName: String  ="" , lineNumber: Option[Integer]  = None, className: String  ="" , nodeLabel: String  ="" , filename: String  ="" , val node: Option[Node] = None) extends NewNode with LocationBase {
//         override val label = "LOCATION"
//         override val properties: Map[String, Any] = 
//             Map(("SYMBOL" -> symbol ),
// ("METHOD_FULL_NAME" -> methodFullName ),
// ("METHOD_SHORT_NAME" -> methodShortName ),
// ("PACKAGE_NAME" -> packageName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("CLASS_NAME" -> className ),
// ("NODE_LABEL" -> nodeLabel ),
// ("FILENAME" -> filename )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("node" -> node.toList)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMatchInfo(pattern: String  ="" , category: String  ="" ) extends NewNode with MatchInfoBase {
//         override val label = "MATCH_INFO"
//         override val properties: Map[String, Any] = 
//             Map(("PATTERN" -> pattern ),
// ("CATEGORY" -> category )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMember(code: String  ="" , name: String  ="" , typeFullName: String  ="" ) extends NewNode with MemberBase {
//         override val label = "MEMBER"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("NAME" -> name ),
// ("TYPE_FULL_NAME" -> typeFullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMetaData(language: String  ="" , version: String  ="" , spid: Option[String]  = None) extends NewNode with MetaDataBase {
//         override val label = "META_DATA"
//         override val properties: Map[String, Any] = 
//             Map(("LANGUAGE" -> language ),
// ("VERSION" -> version ),
// ("SPID" -> spid )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethod(name: String  ="" , fullName: String  ="" , signature: String  ="" , astParentType: String  ="" , astParentFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, hasMapping: Option[JBoolean]  = None, depthFirstOrder: Option[Integer]  = None, binarySignature: Option[String]  = None) extends NewNode with MethodBase {
//         override val label = "METHOD"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("FULL_NAME" -> fullName ),
// ("SIGNATURE" -> signature ),
// ("AST_PARENT_TYPE" -> astParentType ),
// ("AST_PARENT_FULL_NAME" -> astParentFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("HAS_MAPPING" -> hasMapping ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder ),
// ("BINARY_SIGNATURE" -> binarySignature )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethodInst(name: String  ="" , fullName: String  ="" , signature: String  ="" , methodFullName: String  ="" ) extends NewNode with MethodInstBase {
//         override val label = "METHOD_INST"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("FULL_NAME" -> fullName ),
// ("SIGNATURE" -> signature ),
// ("METHOD_FULL_NAME" -> methodFullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethodParameterIn(code: String  ="" , order: Integer  = -1, name: String  ="" , evaluationStrategy: String  ="" , typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None) extends NewNode with MethodParameterInBase {
//         override val label = "METHOD_PARAMETER_IN"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("ORDER" -> order ),
// ("NAME" -> name ),
// ("EVALUATION_STRATEGY" -> evaluationStrategy ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethodParameterOut(code: String  ="" , order: Integer  = -1, name: String  ="" , evaluationStrategy: String  ="" , typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None) extends NewNode with MethodParameterOutBase {
//         override val label = "METHOD_PARAMETER_OUT"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("ORDER" -> order ),
// ("NAME" -> name ),
// ("EVALUATION_STRATEGY" -> evaluationStrategy ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethodRef(code: String  ="" , order: Integer  = -1, argumentIndex: Integer  = -1, methodInstFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with MethodRefBase {
//         override val label = "METHOD_REF"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("METHOD_INST_FULL_NAME" -> methodInstFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethodReturn(code: String  ="" , evaluationStrategy: String  ="" , typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, depthFirstOrder: Option[Integer]  = None) extends NewNode with MethodReturnBase {
//         override val label = "METHOD_RETURN"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("EVALUATION_STRATEGY" -> evaluationStrategy ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewMethodSummary(isStatic: JBoolean , isExternal: JBoolean , binarySignature: Option[String]  = None, val method: MethodBase , val parameters: List[MethodParameterInBase] = List(), val outParameters: List[MethodParameterOutBase] = List(), val returnParameter: MethodReturnBase , val paramTypes: List[TypeBase] = List(), val returnParameterType: TypeBase , val tags: List[TagBase] = List(), val paramTags: List[TagsBase] = List(), val outParamTags: List[TagsBase] = List(), val returnParamTags: List[TagBase] = List(), val annotationParameters: List[SpAnnotationParameterBase] = List(), val modifiers: List[ModifierBase] = List(), val routes: List[RouteBase] = List()) extends NewNode with MethodSummaryBase {
//         override val label = "METHOD_SUMMARY"
//         override val properties: Map[String, Any] = 
//             Map(("IS_STATIC" -> isStatic ),
// ("IS_EXTERNAL" -> isExternal ),
// ("BINARY_SIGNATURE" -> binarySignature )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("method" -> (method :: Nil)) + ("parameters" -> parameters) + ("outParameters" -> outParameters) + ("returnParameter" -> (returnParameter :: Nil)) + ("paramTypes" -> paramTypes) + ("returnParameterType" -> (returnParameterType :: Nil)) + ("tags" -> tags) + ("paramTags" -> paramTags) + ("outParamTags" -> outParamTags) + ("returnParamTags" -> returnParamTags) + ("annotationParameters" -> annotationParameters) + ("modifiers" -> modifiers) + ("routes" -> routes)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewModifier(modifierType: String  ="" ) extends NewNode with ModifierBase {
//         override val label = "MODIFIER"
//         override val properties: Map[String, Any] = 
//             Map(("MODIFIER_TYPE" -> modifierType )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewNamespace(name: String  ="" ) extends NewNode with NamespaceBase {
//         override val label = "NAMESPACE"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewNamespaceBlock(name: String  ="" , fullName: String  ="" ) extends NewNode with NamespaceBlockBase {
//         override val label = "NAMESPACE_BLOCK"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("FULL_NAME" -> fullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewProgramPoint(val elem: TrackingPointBase , val method: Option[MethodBase] = None, val methodTags: List[TagBase] = List(), val paramTags: List[TagBase] = List()) extends NewNode with ProgramPointBase {
//         override val label = "PROGRAM_POINT"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("elem" -> (elem :: Nil)) + ("method" -> method.toList) + ("methodTags" -> methodTags) + ("paramTags" -> paramTags)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewRead(val triggerCallChains: List[CallChainBase] = List(), val descriptorFlows: List[FlowBase] = List(), val source: SourceBase ) extends NewNode with ReadBase {
//         override val label = "READ"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("triggerCallChains" -> triggerCallChains) + ("descriptorFlows" -> descriptorFlows) + ("source" -> (source :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewReturn(lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, order: Integer  = -1, argumentIndex: Integer  = -1, code: String  ="" , sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with ReturnBase {
//         override val label = "RETURN"
//         override val properties: Map[String, Any] = 
//             Map(("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("CODE" -> code ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewRoute(path: String  ="" ) extends NewNode with RouteBase {
//         override val label = "ROUTE"
//         override val properties: Map[String, Any] = 
//             Map(("PATH" -> path )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSensitiveDataType(fullName: String  ="" , val names: List[MatchInfoBase] = List(), val members: List[SensitiveMemberBase] = List()) extends NewNode with SensitiveDataTypeBase {
//         override val label = "SENSITIVE_DATA_TYPE"
//         override val properties: Map[String, Any] = 
//             Map(("FULL_NAME" -> fullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("names" -> names) + ("members" -> members)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSensitiveMember(name: String  ="" , val names: List[MatchInfoBase] = List()) extends NewNode with SensitiveMemberBase {
//         override val label = "SENSITIVE_MEMBER"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("names" -> names)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSensitiveReference(val ioflows: List[IoflowBase] = List()) extends NewNode with SensitiveReferenceBase {
//         override val label = "SENSITIVE_REFERENCE"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("ioflows" -> ioflows)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSensitiveVariable(name: String  ="" , val names: List[MatchInfoBase] = List(), val evalType: TypeBase ) extends NewNode with SensitiveVariableBase {
//         override val label = "SENSITIVE_VARIABLE"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("names" -> names) + ("evalType" -> (evalType :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSink(sinkType: String  ="" , val node: TrackingPointBase , val method: MethodBase , val methodTags: List[TagBase] = List(), val callingMethod: Option[MethodBase] = None, val callsite: Option[CallBase] = None, val parameterIn: Option[MethodParameterInBase] = None, val parameterInTags: List[TagBase] = List()) extends NewNode with SinkBase {
//         override val label = "SINK"
//         override val properties: Map[String, Any] = 
//             Map(("SINK_TYPE" -> sinkType )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("node" -> (node :: Nil)) + ("method" -> (method :: Nil)) + ("methodTags" -> methodTags) + ("callingMethod" -> callingMethod.toList) + ("callsite" -> callsite.toList) + ("parameterIn" -> parameterIn.toList) + ("parameterInTags" -> parameterInTags)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSource(sourceType: String  ="" , val node: TrackingPointBase , val method: MethodBase , val methodTags: List[TagBase] = List(), val callingMethod: Option[MethodBase] = None, val callsite: Option[CallBase] = None, val tags: List[TagBase] = List(), val nodeType: TypeBase ) extends NewNode with SourceBase {
//         override val label = "SOURCE"
//         override val properties: Map[String, Any] = 
//             Map(("SOURCE_TYPE" -> sourceType )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("node" -> (node :: Nil)) + ("method" -> (method :: Nil)) + ("methodTags" -> methodTags) + ("callingMethod" -> callingMethod.toList) + ("callsite" -> callsite.toList) + ("tags" -> tags) + ("nodeType" -> (nodeType :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSpAnnotationParameter(annotationName: String  ="" , annotationFullName: String  ="" , name: String  ="" , value: String  ="" ) extends NewNode with SpAnnotationParameterBase {
//         override val label = "SP_ANNOTATION_PARAMETER"
//         override val properties: Map[String, Any] = 
//             Map(("ANNOTATION_NAME" -> annotationName ),
// ("ANNOTATION_FULL_NAME" -> annotationFullName ),
// ("NAME" -> name ),
// ("VALUE" -> value )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewSpBlacklist(val tags: List[TagBase] = List()) extends NewNode with SpBlacklistBase {
//         override val label = "SP_BLACKLIST"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("tags" -> tags)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTag(name: String  ="" , value: String  ="" ) extends NewNode with TagBase {
//         override val label = "TAG"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("VALUE" -> value )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTags(val tags: List[TagBase] = List()) extends NewNode with TagsBase {
//         override val label = "TAGS"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("tags" -> tags)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTagNodePair(val tag: TagBase , val node: Node ) extends NewNode with TagNodePairBase {
//         override val label = "TAG_NODE_PAIR"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("tag" -> (tag :: Nil)) + ("node" -> (node :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTransform(val triggerCallChains: List[CallChainBase] = List(), val descriptorFlows: List[FlowBase] = List(), val call: CallBase , val sink: SinkBase ) extends NewNode with TransformBase {
//         override val label = "TRANSFORM"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("triggerCallChains" -> triggerCallChains) + ("descriptorFlows" -> descriptorFlows) + ("call" -> (call :: Nil)) + ("sink" -> (sink :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTransformation(val node: TrackingPointBase ) extends NewNode with TransformationBase {
//         override val label = "TRANSFORMATION"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("node" -> (node :: Nil))

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewType(name: String  ="" , fullName: String  ="" , typeDeclFullName: String  ="" ) extends NewNode with TypeBase {
//         override val label = "TYPE"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("FULL_NAME" -> fullName ),
// ("TYPE_DECL_FULL_NAME" -> typeDeclFullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTypeArgument() extends NewNode with TypeArgumentBase {
//         override val label = "TYPE_ARGUMENT"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTypeDecl(name: String  ="" , fullName: String  ="" , isExternal: JBoolean , inheritsFromTypeFullName: List[String] = List(), astParentType: String  ="" , astParentFullName: String  ="" , aliasTypeFullName: Option[String]  = None) extends NewNode with TypeDeclBase {
//         override val label = "TYPE_DECL"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("FULL_NAME" -> fullName ),
// ("IS_EXTERNAL" -> isExternal ),
// ("INHERITS_FROM_TYPE_FULL_NAME" -> inheritsFromTypeFullName ),
// ("AST_PARENT_TYPE" -> astParentType ),
// ("AST_PARENT_FULL_NAME" -> astParentFullName ),
// ("ALIAS_TYPE_FULL_NAME" -> aliasTypeFullName )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewTypeParameter(name: String  ="" , order: Integer  = -1) extends NewNode with TypeParameterBase {
//         override val label = "TYPE_PARAMETER"
//         override val properties: Map[String, Any] = 
//             Map(("NAME" -> name ),
// ("ORDER" -> order )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewUnknown(code: String  ="" , parserTypeName: String  ="" , order: Integer  = -1, argumentIndex: Integer  = -1, typeFullName: String  ="" , lineNumber: Option[Integer]  = None, lineNumberEnd: Option[Integer]  = None, columnNumber: Option[Integer]  = None, columnNumberEnd: Option[Integer]  = None, sccId: Integer  = -1, depthFirstOrder: Option[Integer]  = None) extends NewNode with UnknownBase {
//         override val label = "UNKNOWN"
//         override val properties: Map[String, Any] = 
//             Map(("CODE" -> code ),
// ("PARSER_TYPE_NAME" -> parserTypeName ),
// ("ORDER" -> order ),
// ("ARGUMENT_INDEX" -> argumentIndex ),
// ("TYPE_FULL_NAME" -> typeFullName ),
// ("LINE_NUMBER" -> lineNumber ),
// ("LINE_NUMBER_END" -> lineNumberEnd ),
// ("COLUMN_NUMBER" -> columnNumber ),
// ("COLUMN_NUMBER_END" -> columnNumberEnd ),
// ("SCC_ID" -> sccId ),
// ("DEPTH_FIRST_ORDER" -> depthFirstOrder )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewVariableInfo(varType: String  ="" , evaluationType: String  ="" , parameterIndex: Option[Integer]  = None) extends NewNode with VariableInfoBase {
//         override val label = "VARIABLE_INFO"
//         override val properties: Map[String, Any] = 
//             Map(("VAR_TYPE" -> varType ),
// ("EVALUATION_TYPE" -> evaluationType ),
// ("PARAMETER_INDEX" -> parameterIndex )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
//             .map {
//               case (k, v: Option[_]) => (k,v.get)
//               case other => other
//             }
          
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewWrite(val triggerCallChains: List[CallChainBase] = List(), val descriptorFlows: List[FlowBase] = List(), val sink: SinkBase , val flows: List[FlowBase] = List()) extends NewNode with WriteBase {
//         override val label = "WRITE"
//         override val properties: Map[String, Any] = Map.empty
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty + ("triggerCallChains" -> triggerCallChains) + ("descriptorFlows" -> descriptorFlows) + ("sink" -> (sink :: Nil)) + ("flows" -> flows)

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      

//       case class NewPackagePrefix(value: String  ="" ) extends NewNode with PackagePrefixBase {
//         override val label = "PACKAGE_PREFIX"
//         override val properties: Map[String, Any] = 
//             Map(("VALUE" -> value )).filterNot { case (k,v) =>
//                 v == null || v == None
//               }
            
//         override def containedNodesByLocalName: Map[String, List[Node]] = Map.empty

//         override def accept[T](visitor: NodeVisitor[T]): T = ???
//       }
      
