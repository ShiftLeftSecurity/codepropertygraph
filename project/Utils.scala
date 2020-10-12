object Versions {
  val scalatest  = "3.1.1"
  val antlr = "4.7.2"
}

object GitLFSUtils {
  def isGitLFSEnabled(): Boolean = {
    import scala.sys.process._
    val exitCode = "git lfs ls-files" ! ProcessLogger(s => ())
    exitCode == 0
  }
}
