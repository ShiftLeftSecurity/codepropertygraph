package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala

import io.shiftleft.codepropertygraph.generated.nodes.Call
import io.shiftleft.semanticcpg.language.Steps

class ArithmeticTrav(raw: GremlinScala[Call]) extends Steps[Call](raw) {}
