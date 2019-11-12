package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.{NodeSteps => OriginalNodeSteps}

class Tags(override val raw: GremlinScala[nodes.Tags]) extends OriginalNodeSteps[nodes.Tags](raw) {}
