package io.shiftleft.cpgserver.cpg

import java.util.UUID
import java.util.concurrent.{ConcurrentHashMap, Executors}

import scala.jdk.CollectionConverters._
import scala.collection.concurrent.Map
import scala.concurrent.ExecutionContext
import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, IO}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewMethod
import io.shiftleft.cpgserver.query.{CpgOperationFailure, CpgOperationResult, CpgOperationSuccess}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

/**
  * This provider creates a dummy CPG with a single method node.
  *
  * Any file names passed to the `createCpg` method will be ignored.
  */
class DummyCpgProvider(implicit cs: ContextShift[IO]) extends CpgProvider {

  private val blocker: Blocker =
    Blocker.liftExecutionContext(ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2)))

  private val cpgMap: Map[UUID, CpgOperationResult[Cpg]] =
    new ConcurrentHashMap[UUID, CpgOperationResult[Cpg]].asScala

  private val uuidProvider = IO(UUID.randomUUID)

  private class MyPass(cpg: Cpg) extends CpgPass(cpg) {
    override def run(): Iterator[DiffGraph] = {
      implicit val diffGraph: DiffGraph.Builder = DiffGraph.newBuilder
      NewMethod(name = "main", isExternal = false).start.store
      Iterator(diffGraph.build())
    }
  }

  override def createCpg(filenames: Set[String]): IO[UUID] = {
    val cpg = new Cpg

    for {
      cpgId <- uuidProvider
      _ <- blocker
        .blockOn(IO(new MyPass(cpg).createAndApply()))
        .runAsync {
          case Right(_) => IO(cpgMap.put(cpgId, CpgOperationSuccess(cpg))).map(_ => ())
          case Left(ex) => IO(cpgMap.put(cpgId, CpgOperationFailure(ex))).map(_ => ())
        }
        .toIO
    } yield cpgId
  }

  override def retrieveCpg(uuid: UUID): OptionT[IO, CpgOperationResult[Cpg]] = {
    OptionT.fromOption(cpgMap.get(uuid))
  }
}
