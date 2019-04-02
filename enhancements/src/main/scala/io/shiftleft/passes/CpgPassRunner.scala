package io.shiftleft.passes

import io.shiftleft.SerializedCpg

/**
  * A generic CPG pass runner
  * */
class CpgPassRunner(serializedCpg: SerializedCpg) {

  def createStoreAndApplyOverlay(pass: CpgPass, counter: Int = 0) = {
    val overlays = pass.executeAndCreateOverlay()
    overlays.zipWithIndex.foreach {
      case (x, index) =>
        serializedCpg.addOverlay(x, pass.getClass.getSimpleName + counter.toString + "_" + index)
    }
  }

}
