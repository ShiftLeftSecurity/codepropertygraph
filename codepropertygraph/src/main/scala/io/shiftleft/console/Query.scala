package io.shiftleft.console

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

case class Query(name: String,
                 author: String,
                 title: String,
                 description: String,
                 score: Double,
                 // intended to be filled by com.lihaoyi.sourcecode.Line
                 docStartLine: Int = 0,
                 traversal: Cpg => Traversal[nodes.StoredNode],
                 // intended to be filled by com.lihaoyi.sourcecode.Line
                 docEndLine: Int = 0,
                 // intended to be filled by com.lihaoyi.sourcecode.FileName
                 docFileName: String = "",
                 traversalAsString: String = "",
                 tags: List[String] = List())
