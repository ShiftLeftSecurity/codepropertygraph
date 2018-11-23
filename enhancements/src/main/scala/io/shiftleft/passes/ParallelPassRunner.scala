package io.shiftleft.passes

import io.shiftleft.SerializedCpg
import scalaz.concurrent.Task

class ParallelPassRunner(serializedCpg: SerializedCpg, prefix: String) {

  protected def runInParallel(passes: List[CpgPass], timeoutSeconds: Long): Unit = {
    val tasks = passes.zipWithIndex.map {
      case (pass, i) =>
        Task {
          val overlay = pass.executeAndCreateOverlay()
          serializedCpg.addOverlay(overlay, prefix + "-" + i.toString)
          pass
        }
    }
    Task.gatherUnordered(tasks).unsafePerformSync.foreach { pass =>
      pass.applyDiff()
    }
  }

}
