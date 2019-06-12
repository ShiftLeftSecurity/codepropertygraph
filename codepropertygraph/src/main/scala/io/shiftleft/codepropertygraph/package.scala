package io.shiftleft

package object codepropertygraph {

  /**
    * A convenience wrapper around Cpgs providing auto-closeable resource
    * @param cpg the underlying Cpg to be pimped
    */
  implicit class CpgOps(val cpg: Cpg) extends AnyVal {
    def autoClose[A](fun: cpg.type => A): A = {
      try {
        fun(cpg)
      } finally {
        cpg.close()
      }
    }
  }

}
