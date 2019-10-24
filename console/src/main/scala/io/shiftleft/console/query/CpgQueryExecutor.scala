package io.shiftleft.console.query

import cats.data.OptionT
import cats.effect.IO

import io.shiftleft.codepropertygraph.Cpg

import java.util.UUID

/**
  * This trait provides an abstraction over the execution of queries on a CPG.
  *
  * @tparam ResultT The type of result returned by the query executor.
  */
trait CpgQueryExecutor[ResultT] {

  /**
    * Asynchronously performs the provided CPG `query` against the specified `cpg`.
    *
    * @param cpg The CPG to perform the query against.
    * @param query A String containing a CPG query.
    * @return A UUID referencing the eventual result of the query.
    */
  def executeQuery(cpg: Cpg, query: String): IO[UUID]

  /**
    * Synchronously performs the provided CPG `query` against the specified `cpg`.
    *
    * @param cpg The CPG to perform the query against.
    * @param query A String containing a CPG query.
    * @return A CpgOperationResult containing the result of the query.
    */
  def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[Object]]

  /**
    * Returns a query result identified by `uuid` iff the query has finished running.
    *
    * @param queryId A UUID associated with a CPG query.
    * @return An OptionT, containing a CPG query result if it exists.
    */
  def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[ResultT]]
}
