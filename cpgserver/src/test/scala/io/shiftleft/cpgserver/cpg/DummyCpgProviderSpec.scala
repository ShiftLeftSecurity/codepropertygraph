package io.shiftleft.cpgserver.cpg

import java.util.UUID

import scala.concurrent.ExecutionContext
import cats.data.OptionT
import cats.effect.{ContextShift, IO}
import org.scalatest.concurrent.Eventually

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgserver.BaseSpec
import io.shiftleft.cpgserver.query.CpgOperationResult

import scala.concurrent.duration._
import scala.language.postfixOps

class DummyCpgProviderSpec extends BaseSpec with Eventually {

  private implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  private def withNewCpgProvider[T](f: DummyCpgProvider => T): T = {
    f(new DummyCpgProvider)
  }

  "Creating a CPG" should {
    "return a UUID referencing the eventual CPG" in withNewCpgProvider { cpgProvider =>
      noException should be thrownBy cpgProvider.createCpg(Set.empty).unsafeRunSync()
    }
  }

  "Retrieving a CPG" should {
    "return a success if the CPG was created successfully" in withNewCpgProvider { cpgProvider =>
      val cpgId = cpgProvider.createCpg(Set.empty).unsafeRunSync()

      eventually(timeout(10 seconds), interval(1 seconds)) {
        cpgProvider.retrieveCpg(cpgId).value.unsafeRunSync() shouldBe defined
      }
    }

    "return an empty OptionT if the CPG does not exist" in withNewCpgProvider { cpgProvider =>
      cpgProvider.retrieveCpg(UUID.randomUUID) shouldBe OptionT.none[IO, CpgOperationResult[Cpg]]
    }
  }
}
