object GitLFSUtils {
  def isGitLFSEnabled(): Boolean = {
    import scala.sys.process._
    val exitCode = "git lfs ls-files" ! ProcessLogger(s => ())
    exitCode == 0
  }
}
