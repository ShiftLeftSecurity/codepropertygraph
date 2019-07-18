package io.shiftleft.passes

/**
  * A generic CPG pass runner
  * */
class CpgPassRunner(serializedCpg: SerializedCpg) {

  def createStoreAndApplyOverlay(pass: CpgPass, counter: Int = 0) = {
    val overlays = pass.executeAndCreateOverlay()
    overlays.zipWithIndex.foreach {
      case (overlay, index) => {
        if (overlay.getSerializedSize > 0) {
          serializedCpg.addOverlay(overlay, pass.getClass.getSimpleName + counter.toString + "_" + index)
        }
      }
    }
  }

}
