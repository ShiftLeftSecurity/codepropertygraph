package io.shiftleft.console.testing

import java.nio.file.Path
import java.util.concurrent.LinkedBlockingQueue

import better.files.Dsl.mkdir
import better.files.File
import io.shiftleft.console.cpgcreation.{CpgGenerator, LanguageFrontend}
import io.shiftleft.console.{Console, ConsoleConfig, DefaultAmmoniteExecutor, InstallConfig}
import io.shiftleft.console.workspacehandling.{Project, ProjectFile, WorkspaceLoader}
import io.shiftleft.fuzzyc2cpg.FuzzyC2Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct

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
    install = new InstallConfig(Map("SHIFTLEFT_CONSOLE_INSTALL_DIR" -> workspaceDir))
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
      val queue = new LinkedBlockingQueue[CpgStruct.Builder]()
      val factory =
        new io.shiftleft.fuzzyc2cpg.output.overflowdb.OutputModuleFactory(outputPath, queue)
      val fuzzyc = new FuzzyC2Cpg(factory)
      File(inputPath).list.foreach(println(_))
      fuzzyc.runAndOutput(Set(inputPath), Set(".c"))
      Some(outputPath)
    }

    def isAvailable: Boolean = true

  }

}
