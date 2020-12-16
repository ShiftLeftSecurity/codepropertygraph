package io.shiftleft.console.cpgcreation

import java.nio.file.Path

import io.shiftleft.console.JavaFrontendConfig

/**
  * Language frontend for Java archives (JAR files). Translates Java archives into code property graphs.
  * */
case class JavaCpgGenerator(config: JavaFrontendConfig, rootPath: Path) extends CpgGenerator {

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin.zip",
                        namespaces: List[String] = List()): Option[String] = {
    if (inputPath.endsWith(".apk")) {
      println("found .apk ending - will first transform it to a jar using dex2jar.sh")
      val dex2jar = rootPath.resolve("dex2jar.sh").toString
      runShellCommand(dex2jar, Seq(inputPath)).flatMap { _ =>
        val jarPath = s"${inputPath}.jar"
        generate(jarPath, outputPath, namespaces)
      }
    } else {
      var command = rootPath.resolve("java2cpg.sh").toString
      var arguments = Seq(inputPath, "-o", outputPath) ++ jvmLanguages ++ namespaceArgs(namespaces) ++ config.cmdLineParams
      if (System.getProperty("os.name").startsWith("Windows")) {
        command = "powershell"
        arguments = Seq(rootPath.resolve("java2cpg.ps1").toString) ++ arguments
      }
      runShellCommand(command, arguments).map(_ => outputPath)
    }
  }

  private def jvmLanguages: List[String] = {
    if (JavaCpgGenerator.experimentalLanguages.nonEmpty) {
      List("--experimental-langs", JavaCpgGenerator.experimentalLanguages.mkString(","))
    } else Nil
  }

  private def namespaceArgs(namespaces: List[String]): List[String] = {
    val csvString = namespaces.mkString(",")
    // if no namespaces are specified, use smart unpacking
    if (csvString.isEmpty) {
      List("-su")
    } else {
      List("-nb", "-w", csvString)
    }
  }

  override def isAvailable: Boolean = rootPath.resolve("java2cpg.sh").toFile.exists()
}

object JavaCpgGenerator {
  private final val experimentalLanguages: List[String] = List("scala")
}
