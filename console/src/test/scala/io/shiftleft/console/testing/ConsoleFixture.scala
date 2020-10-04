package io.shiftleft.console.testing

import java.nio.file.Path

import better.files.Dsl.mkdir
import better.files.File
import io.shiftleft.console.cpgcreation.{CpgGenerator, LanguageFrontend}
import io.shiftleft.console.{Console, ConsoleConfig, DefaultAmmoniteExecutor, InstallConfig}
import io.shiftleft.console.workspacehandling.{Project, ProjectFile, WorkspaceLoader}

import scala.sys.process.Process

object ConsoleFixture {
  def apply[T <: Console[Project]](constructor: String => T = { x =>
    new TestConsole(x)
  })(fun: (T, File) => Unit): Unit = {
    File.usingTemporaryDirectory("console") { workspaceDir =>
      File.usingTemporaryDirectory("console") { codeDir =>
        mkdir(codeDir / "dir1")
        mkdir(codeDir / "dir2")
        (codeDir / "dir1" / "foo.c")
          .write("int main(int argc, char **argv) { char *ptr = 0x1 + argv; return argc; }")
        (codeDir / "dir2" / "bar.c").write("int bar(int x) { return x; }")
        val console = constructor(workspaceDir.toString)
        fun(console, codeDir)
      }
    }
  }

}

object TestWorkspaceLoader extends WorkspaceLoader[Project] {
  override def createProject(projectFile: ProjectFile, path: Path): Project = Project(projectFile, path)
}

class TestConsole(workspaceDir: String) extends Console[Project](DefaultAmmoniteExecutor, TestWorkspaceLoader) {
  override def config = new ConsoleConfig(
    install = new InstallConfig(Map("SHIFTLEFT_OCULAR_INSTALL_DIR" -> workspaceDir))
  )

  override val cpgGenerator = new TestCpgGenerator(config)

}

class TestCpgGenerator(config: ConsoleConfig) extends CpgGenerator(config) {
  override def createFrontendByPath(
      inputPath: String,
  ): Option[LanguageFrontend] = {
    Some(new FuzzyCTestingFrontend)
  }

  override def createFrontendByLanguage(language: String): Option[LanguageFrontend] = {
    Some(new FuzzyCTestingFrontend)
  }

  private class FuzzyCTestingFrontend extends LanguageFrontend {

    override def generate(inputPath: String, outputPath: String, namespaces: List[String]): Option[String] = {
      val p = Process(
        List(
          "./fuzzyc2cpg.sh",
          inputPath,
          "--output",
          outputPath
        )
      ).run()
      assert(p.exitValue() == 0, s"fuzzyc exited with code ${p.exitValue}")

      Some(outputPath)
    }

    def isAvailable: Boolean = true

  }

}
