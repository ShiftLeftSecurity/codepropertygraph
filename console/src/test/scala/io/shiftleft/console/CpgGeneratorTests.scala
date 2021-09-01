package io.shiftleft.console

import io.shiftleft.console.cpgcreation.{CCpgGenerator, CSharpCpgGenerator, FuzzyCCpgGenerator, GhidraCpgGenerator, JsCpgGenerator}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.file.{Path, Paths}

class CpgGeneratorTests extends AnyWordSpec with Matchers with BeforeAndAfterEach {
  private val rootPath = "/proj/root"
  private val frontendConfig = FrontendConfig()
  private var installConfig = InstallConfig().withRootPath(rootPath)

  override def beforeEach(): Unit = {
    super.beforeEach()
    installConfig = InstallConfig().withRootPath(rootPath)
  }

  "XCpgGenerator" should {
    "use the project root as the binary dir if none is specified" in {

      CCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/c2cpg.sh"
      FuzzyCCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/fuzzyc2cpg.sh"
      GhidraCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/ghidra2cpg"
      JsCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/js2cpg.sh"
    }

    "correctly insert the cpg generators directory into the binary path" in {
      val genPath = "cpg/generators"
      installConfig.cpgGeneratorsDir = genPath
      CCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/$genPath/c2cpg.sh"
      FuzzyCCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/$genPath/fuzzyc2cpg.sh"
      GhidraCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/$genPath/ghidra2cpg"
      JsCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe s"$rootPath/$genPath/js2cpg.sh"

      installConfig.cpgGeneratorsDir = ""
      CCpgGenerator(frontendConfig, installConfig).binaryPath.toString shouldBe "/proj/root/c2cpg.sh"
    }
  }

}