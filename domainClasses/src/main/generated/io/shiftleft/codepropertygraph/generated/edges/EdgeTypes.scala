package io.shiftleft.codepropertygraph.generated.edges

object AliasOf {
  val Label = "ALIAS_OF"

}

class AliasOf(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 0, subSeq_4862, property_4862) {}

object Argument {
  val Label = "ARGUMENT"

}

class Argument(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 1, subSeq_4862, property_4862) {}

object Ast {
  val Label = "AST"

}

class Ast(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 2, subSeq_4862, property_4862) {}

object Binds {
  val Label = "BINDS"

}

class Binds(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 3, subSeq_4862, property_4862) {}

object BindsTo {
  val Label = "BINDS_TO"

}

class BindsTo(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 4, subSeq_4862, property_4862) {}

object Call {
  val Label = "CALL"

}

class Call(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 5, subSeq_4862, property_4862) {}

object Capture {
  val Label = "CAPTURE"

}

class Capture(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 6, subSeq_4862, property_4862) {}

object CapturedBy {
  val Label = "CAPTURED_BY"

}

class CapturedBy(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 7, subSeq_4862, property_4862) {}

object Cdg {
  val Label = "CDG"

}

class Cdg(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 8, subSeq_4862, property_4862) {}

object Cfg {
  val Label = "CFG"

}

class Cfg(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 9, subSeq_4862, property_4862) {}

object Condition {
  val Label = "CONDITION"

}

class Condition(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 10, subSeq_4862, property_4862) {}

object Contains {
  val Label = "CONTAINS"

}

class Contains(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 11, subSeq_4862, property_4862) {}

object Dominate {
  val Label = "DOMINATE"

}

class Dominate(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 12, subSeq_4862, property_4862) {}

object EvalType {
  val Label = "EVAL_TYPE"

}

class EvalType(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 13, subSeq_4862, property_4862) {}

object Imports {
  val Label = "IMPORTS"

}

class Imports(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 14, subSeq_4862, property_4862) {}

object InheritsFrom {
  val Label = "INHERITS_FROM"

}

class InheritsFrom(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 15, subSeq_4862, property_4862) {}

object IsCallForImport {
  val Label = "IS_CALL_FOR_IMPORT"

}

class IsCallForImport(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 16, subSeq_4862, property_4862) {}

object ParameterLink {
  val Label = "PARAMETER_LINK"

}

class ParameterLink(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 17, subSeq_4862, property_4862) {}

object PostDominate {
  val Label = "POST_DOMINATE"

}

class PostDominate(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 18, subSeq_4862, property_4862) {}

object ReachingDef {
  val Label                        = "REACHING_DEF"
  val propertyName: Option[String] = Some("VARIABLE")
}

class ReachingDef(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 19, subSeq_4862, property_4862) {
  override def propertyName: Option[String] = ReachingDef.propertyName
}

object Receiver {
  val Label = "RECEIVER"

}

class Receiver(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 20, subSeq_4862, property_4862) {}

object Ref {
  val Label = "REF"

}

class Ref(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 21, subSeq_4862, property_4862) {}

object SourceFile {
  val Label = "SOURCE_FILE"

}

class SourceFile(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 22, subSeq_4862, property_4862) {}

object TaggedBy {
  val Label = "TAGGED_BY"

}

class TaggedBy(src_4762: flatgraph.GNode, dst_4762: flatgraph.GNode, subSeq_4862: Int, property_4862: Any)
    extends flatgraph.Edge(src_4762, dst_4762, 23, subSeq_4862, property_4862) {}
