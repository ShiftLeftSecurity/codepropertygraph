package io.shiftleft.console

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.console.scripting.{AmmoniteExecutor, ScriptManager}
import io.shiftleft.semanticcpg.layers.LayerCreator

abstract class Console(executor: AmmoniteExecutor) extends ScriptManager(executor) {

  def _runAnalyzer(overlayCreators: LayerCreator*): Cpg

}
