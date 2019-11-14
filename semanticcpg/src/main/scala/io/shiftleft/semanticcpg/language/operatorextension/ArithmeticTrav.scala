package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.semanticcpg.language.Steps

class ArithmeticTrav(raw: GremlinScala[nodes.Arithmetic]) extends Steps[nodes.Arithmetic](raw) {}
