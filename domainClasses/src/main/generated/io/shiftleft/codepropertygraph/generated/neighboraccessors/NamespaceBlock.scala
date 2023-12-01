
package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForNamespaceBlock(val node: nodes.NamespaceBlock) extends AnyVal {
  /** 
 * Traverse to FILE via AST IN edge. */
def fileViaAstIn: Option[nodes.File] =   astIn.collectAll[nodes.File].nextOption()


/** 
 * Traverse to FILE via SOURCE_FILE OUT edge. */
def fileViaSourceFileOut: Iterator[nodes.File] = sourceFileOut.collectAll[nodes.File]


/** 
 * Traverse to METHOD via AST OUT edge. */
def methodViaAstOut: Iterator[nodes.Method] = astOut.collectAll[nodes.Method]


/** 
 * Traverse to NAMESPACE via REF OUT edge. */
def namespaceViaRefOut: Iterator[nodes.Namespace] = refOut.collectAll[nodes.Namespace]


/** 
 * Traverse to TYPE_DECL via AST OUT edge. */
def typeDeclViaAstOut: Iterator[nodes.TypeDecl] = astOut.collectAll[nodes.TypeDecl]


def astIn: Iterator[nodes.File] = node._astIn.cast[nodes.File]

def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]

def refOut: Iterator[nodes.Namespace] = node._refOut.cast[nodes.Namespace]

def sourceFileOut: Iterator[nodes.File] = node._sourceFileOut.cast[nodes.File]
}

final class AccessNeighborsForNamespaceBlockTraversal(val traversal: Iterator[nodes.NamespaceBlock]) extends AnyVal {
  /** 
 * Traverse to FILE via AST IN edge. */
def fileViaAstIn: Iterator[nodes.File] = traversal.flatMap(_.fileViaAstIn)


/** 
 * Traverse to FILE via SOURCE_FILE OUT edge. */
def fileViaSourceFileOut: Iterator[nodes.File] = traversal.flatMap(_.fileViaSourceFileOut)


/** 
 * Traverse to METHOD via AST OUT edge. */
def methodViaAstOut: Iterator[nodes.Method] = traversal.flatMap(_.methodViaAstOut)


/** 
 * Traverse to NAMESPACE via REF OUT edge. */
def namespaceViaRefOut: Iterator[nodes.Namespace] = traversal.flatMap(_.namespaceViaRefOut)


/** 
 * Traverse to TYPE_DECL via AST OUT edge. */
def typeDeclViaAstOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaAstOut)


def astIn: Iterator[nodes.File] = traversal.flatMap(_.astIn)

def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)

def refOut: Iterator[nodes.Namespace] = traversal.flatMap(_.refOut)

def sourceFileOut: Iterator[nodes.File] = traversal.flatMap(_.sourceFileOut)
}

