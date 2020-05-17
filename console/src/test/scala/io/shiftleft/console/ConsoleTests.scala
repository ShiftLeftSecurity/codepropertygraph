package io.shiftleft.console

import java.io.FileOutputStream
import java.util.zip.ZipOutputStream

import better.files.Dsl.cp
import better.files._
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.console.testing._
import io.shiftleft.semanticcpg.layers.Scpg

class ConsoleTests extends WordSpec with Matchers {

  "importCode" should {
    "provide overview of available language modules" in ConsoleFixture() { (console, _) =>
      console.importCode.toString.contains("| C") shouldBe true
    }

    "allow importing code with specific module" in ConsoleFixture() { (console, codeDir) =>
      console.importCode.c(codeDir.toString)
      console.workspace.numberOfProjects shouldBe 1
    }

    "allow importing code" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      console.workspace.numberOfProjects shouldBe 1
      Set("main", "bar").subsetOf(console.cpg.method.name.toSet)
      console.project.appliedOverlays shouldBe List("semanticcpg")
      console.project.availableOverlays shouldBe List("semanticcpg")
    }

    "allow importing code and setting project name" in ConsoleFixture() { (console, codeDir) =>
      val projectName = "test"
      console.importCode(codeDir.toString, projectName)
      console.workspace.numberOfProjects shouldBe 1
      console.workspace.project(projectName) match {
        case Some(p) =>
          p.name shouldBe projectName
          p.cpg should not be empty
        case None => fail()
      }
      console.project.appliedOverlays shouldBe List("semanticcpg")
    }

    "allow importing multiple code bases" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString, "foo")
      console.importCode(codeDir.toString, "bar")
      console.workspace.numberOfProjects shouldBe 2
      console.project.name shouldBe "bar"
      console.project.appliedOverlays shouldBe List("semanticcpg")
      console.workspace.project("foo").get.appliedOverlays shouldBe List("semanticcpg")
    }

    "set project to active" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString, "foo")
      Set("main", "bar").subsetOf(console.cpg.method.name.toSet)
    }

  }

  "importCpg" should {

    "gracefully handle request to import non-existing CPG" in ConsoleFixture() { (console, _) =>
      console.importCpg("idontexist")
      console.workspace.numberOfProjects shouldBe 0
    }

    "convert legacy CPGs on import" in ConsoleFixture() { (console, _) =>
      File.usingTemporaryFile("console") { file =>
        new ZipOutputStream(new FileOutputStream(file.toString)).close()
        val projectName = "myproject"
        val cpg = console.importCpg(file.toString, projectName)
        console.workspace.numberOfProjects shouldBe 1
        console.workspace.projectByCpg(cpg.get).map(_.name) shouldBe Some(projectName)
        console.project.appliedOverlays shouldBe List()
      }
    }

    "handle broken legacy CPG gracefully" in ConsoleFixture() { (console, _) =>
      File.usingTemporaryFile("console") { file =>
        file.write("PK")
        console.importCpg(file.toString)
      }
    }

    "gracefully handle broken CPGs" in ConsoleFixture() { (console, _) =>
      File.usingTemporaryFile("console") { file =>
        file.writeBytes(List[Byte]('F', 'O').iterator)
        console.importCpg(file.toString)
        console.workspace.numberOfProjects shouldBe 0
      }
    }

    "allow importing an existing CPG" in ConsoleFixture() { (console, codeDir) =>
      val tmpCpg = createStandaloneCpg(console, codeDir)
      try {
        console.importCpg(tmpCpg.toString)
        console.workspace.numberOfProjects shouldBe 1
        Set("main", "bar").subsetOf(console.cpg.method.name.toSet)
        console.project.appliedOverlays shouldBe List("semanticcpg")
      } finally {
        Some(tmpCpg).find(_.exists).foreach(_.delete())
      }
    }

    "allow importing an existing CPG with custom project name" in ConsoleFixture() { (console, codeDir) =>
      val tmpCpg = createStandaloneCpg(console, codeDir)
      try {
        console.importCpg(tmpCpg.toString, "foobar")
        console.workspace.numberOfProjects shouldBe 1
        console.workspace.project("foobar") should not be empty
        Set("main", "bar").subsetOf(console.cpg.method.name.toSet)
      } finally {
        Some(tmpCpg).find(_.exists).foreach(_.delete())
      }
    }

    "allow importing two CPGs with the same filename but different paths" in ConsoleFixture() { (console, codeDir) =>
      val cpgFile = createStandaloneCpg(console, codeDir)
      File.usingTemporaryDirectory("console") { dir1 =>
        File.usingTemporaryDirectory("console") { dir2 =>
          File.usingTemporaryDirectory("console") { dir3 =>
            val cpg1Path = dir1.path.resolve("cpg.bin")
            val cpg2Path = dir2.path.resolve("cpg.bin")
            val cpg3Path = dir3.path.resolve("cpg.bin")
            cp(cpgFile, cpg1Path)
            cp(cpgFile, cpg2Path)
            cp(cpgFile, cpg3Path)
            console.importCpg(cpg1Path.toString)
            console.importCpg(cpg2Path.toString)
            console.importCpg(cpg3Path.toString)
            console.workspace.numberOfProjects shouldBe 3
            console.workspace.project(cpg1Path.toFile.getName) should not be empty
            console.workspace.project(cpg1Path.toFile.getName + "1") should not be empty
            console.workspace.project(cpg1Path.toFile.getName + "2") should not be empty
            console.workspace.project(cpg1Path.toFile.getName + "12") shouldBe empty
          }
        }
      }
    }

    "overwrite project if a project for the inputPath exists" in ConsoleFixture() { (console, codeDir) =>
      val tmpCpg = createStandaloneCpg(console, codeDir)
      try {
        console.importCpg(tmpCpg.toString)
        console.importCpg(tmpCpg.toString)
        console.workspace.numberOfProjects shouldBe 1
        Set("main", "bar").subsetOf(console.cpg.method.name.toSet)
        console.project.appliedOverlays shouldBe List("semanticcpg")
      } finally {
        Some(tmpCpg).find(_.exists).foreach(_.delete())
      }
    }
  }

  "open/close" should {
    "allow opening an already open project to make it active" in ConsoleFixture() { (console, codeDir) =>
      val projectName = "myproject"
      console.importCode(codeDir.toString, projectName)
      console.importCpg(codeDir.path.resolve("cpg.bin").toString, "foo")
      val project = console.open(projectName)
      project match {
        case Some(p) =>
          p.name shouldBe projectName
          console.workspace.projectByCpg(p.cpg.get).map(_.name) shouldBe Some(projectName)
        case None => fail
      }
    }

    "allow closing and then opening a project again" in ConsoleFixture() { (console, codeDir) =>
      val projectName = "myproject"
      console.importCode(codeDir.toString, projectName)
      console.close(projectName).get.cpg shouldBe empty
      console.open(projectName).get.cpg should not be empty
    }

    "allow closing currently active project" in ConsoleFixture() { (console, codeDir) =>
      val projectName = "myproject"
      console.importCode(codeDir.toString, projectName)
      val project = console.close
      project.get.name shouldBe projectName
      project.get.cpg shouldBe empty
    }

    "should keep project active on close and allow setting other as active" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString, "foo")
      console.importCode(codeDir.toString, "bar")
      console.close
      a[RuntimeException] should be thrownBy (console.cpg)
      console.open("foo")
      Set("main", "bar").subsetOf(console.cpg.method.name.toSet)
    }
  }

  "delete" should {

    "remove a project from disk" in ConsoleFixture() { (console, codeDir) =>
      val cpg = console.importCode(codeDir.toString, "foo")
      val projectDir = console.workspace.projectByCpg(cpg.get).get.path.toFile
      console.delete("foo")
      projectDir.exists shouldBe false
    }

    "handle request to delete non-existing project gracefully" in ConsoleFixture() { (console, _) =>
      val project = console.delete("foo")
      project shouldBe None
    }

  }

  "runAnalyzer" should {

    "prohibit adding semanticcpg again" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      console.project.appliedOverlays shouldBe List("semanticcpg")
      val numOverlayFilesBefore = console.project.path.resolve("overlays").toFile.list().size
      numOverlayFilesBefore shouldBe 1
      console._runAnalyzer(new Scpg)
      console.project.appliedOverlays shouldBe List("semanticcpg")
      console.project.path.resolve("overlays").toFile.list().size shouldBe numOverlayFilesBefore
    }

    "store directory for each overlay in project" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      val overlayDir = console.project.path.resolve("overlays")
      overlayDir.toFile.list.toSet shouldBe Set("semanticcpg")

      val overlayFiles = overlayDir.toFile.listFiles()
      overlayFiles.foreach { file =>
        File(file.getPath).isDirectory shouldBe true
      }
    }
  }

  "undo" should {
    "remove layer from meta information" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      console.project.appliedOverlays shouldBe List("semanticcpg")
      console.undo
      console.project.appliedOverlays shouldBe List()
    }

    "remove overlay file from project" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      val overlayDir = console.project.path.resolve("overlays")
      val overlayFilesBefore = overlayDir.toFile.list.toSet
      overlayFilesBefore shouldBe Set("semanticcpg")
      console.undo
      val overlayFilesAfter = overlayDir.toFile.list.toSet
      overlayFilesAfter shouldBe Set()
    }

    "actually remove some nodes" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      console.cpg.parameter.asOutput.l.size should be > 0
      console.project.appliedOverlays shouldBe List("semanticcpg")
      console.undo
      console.project.appliedOverlays shouldBe List()
      console.cpg.parameter.asOutput.l.size shouldBe 0
      console._runAnalyzer(new Scpg)
      console.cpg.parameter.asOutput.l.size should be > 0
    }
  }

  "save" should {
    "close and reopen projects" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString, "project1")
      console.importCode(codeDir.toString, "project2")
      console.workspace.project("project1").exists(_.cpg.isDefined) shouldBe true
      console.workspace.project("project2").exists(_.cpg.isDefined) shouldBe true
      console.save
      console.workspace.project("project1").exists(_.cpg.isDefined) shouldBe true
      console.workspace.project("project2").exists(_.cpg.isDefined) shouldBe true
    }

    "copy working copy to persistent copy" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString, "project1")
      val projectPath = console.project.path
      console.save
      val persistentCpgSize = projectPath.resolve("cpg.bin").toFile.length()
      val workingCpgSize = projectPath.resolve("cpg.bin.tmp").toFile.length()
      persistentCpgSize shouldBe workingCpgSize
    }

  }

  "cpg" should {
    "provide .help command" in ConsoleFixture() { (console, codeDir) =>
      console.importCode(codeDir.toString)
      console.cpg.help.contains(".all") shouldBe true
    }
  }

}
