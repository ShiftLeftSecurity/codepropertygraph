package io.shiftleft.passes

import io.shiftleft.SerializedCpg

/**
  * A generic CPG pass runner
  * */
class CpgPassRunner(serializedCpg: SerializedCpg) {

  def createStoreAndApplyOverlay(pass: CpgPass, counter: Int = 0) = {
    val overlay = pass.executeAndCreateOverlay()
    serializedCpg.addOverlay(overlay, pass.getClass.getSimpleName + counter.toString)
  }

}
