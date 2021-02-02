package io.shiftleft.console.scripting

import scala.reflect.runtime.currentMirror
import com.google.protobuf.DescriptorProtos.FileDescriptorProto

/** a workaround for a scala reflect bug, where the first invocation of scala reflect fails
  * idea: invoke this at the very start to trigger the bug. future invocations should be fine
  * i asked to move the retry logic into scala reflect as a better way to handle this
  * https://github.com/scala/bug/issues/12038#issuecomment-760629585
  *
  * to reproduce the issue, comment out this workaround, publish cpg locally and run the following in the codescience repo:
  * `cpg2sp/testOnly *PolicyAmmoniteExecutorTest`
  * if the above is green without this workaround, we don't need it any more
  */
object ScalaReflectWorkaround {
  var applied = false

  def fromJava(t: FileDescriptorProto): Unit = {
    println(t)
    // this is just here to suppress a warnings - it is never invoked by anything afaik,
    // but for whatever reason essential to trigger the scala reflection bug...
  }

  def workaroundScalaReflectBugByTriggeringReflection() = {
    if (!applied) {
      try {
        currentMirror
          .reflectModule(currentMirror.staticModule("io.shiftleft.console.scripting.ScalaReflectWorkaround$"))
          .instance
      } catch {
        case _: Throwable => // that's what we want to trigger - it happens the first time, then works - all good
      }
      applied = true
    }
  }
}
