package io.shiftleft.cpgserver.cpg

import java.util.UUID
import cats.data.OptionT
import cats.effect.IO

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.console.query.CpgOperationResult

trait CpgProvider {

  /**
    * Asynchronously generates a CPG from the input set of files.
    *
    * Depending on the implementation, this operation may fail if any of the
    * specified files do not exist.
    *
    * @param fileNames a set of file names to construct the CPG from.
    * @return A UUID identifying the eventual CPG.
    */
  def createCpg(fileNames: Set[String]): IO[UUID]

  /**
    * Returns a CPG result identified by `uuid` iff the CPG has been created.
    *
    * @param cpgId A UUID associated with a CPG.
    * @return A OptionT, containing a CPG result if it exists.
    */
  def retrieveCpg(cpgId: UUID): OptionT[IO, CpgOperationResult[Cpg]]
}
