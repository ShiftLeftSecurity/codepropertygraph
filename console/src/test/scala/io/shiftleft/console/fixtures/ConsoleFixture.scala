package io.shiftleft.console.fixtures

import java.nio.file.Path

import better.files.Dsl.mkdir
import better.files.File
import io.shiftleft.console.workspacehandling.{Project, ProjectFile, WorkspaceLoader}
import io.shiftleft.console.{Console, ConsoleConfig, DefaultAmmoniteExecutor, InstallConfig}

object ConsoleFixture {
  def apply()(fun: (TestConsole, File) => Unit): Unit = {
    File.usingTemporaryDirectory("console") { workspaceDir =>
      File.usingTemporaryDirectory("console") { codeDir =>
        mkdir(codeDir / "dir1")
        mkdir(codeDir / "dir2")
        (codeDir / "dir1" / "foo.c")
          .write("int main(int argc, char **argv) { char *ptr = 0x1 + argv; return argc; }")
        (codeDir / "dir2" / "bar.c").write("int bar(int x) { return x; }")
        val console = new TestConsole(workspaceDir.toString)
        fun(console, codeDir)
      }
    }
  }
}

object TestWorkspaceLoader extends WorkspaceLoader[Project] {
  override def createProject(projectFile: ProjectFile, path: Path): Project = Project(projectFile, path)
}

private class TestConsole(workspaceDir: String) extends Console[Project](DefaultAmmoniteExecutor, TestWorkspaceLoader) {
  override def config = new ConsoleConfig(
    install = new InstallConfig(Map("SHIFTLEFT_CONSOLE_INSTALL_DIR" -> workspaceDir))
  )

  override val cpgGenerator = new TestCpgGenerator(config)

}
