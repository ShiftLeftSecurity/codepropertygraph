package io.shiftleft.cpgsplitter

import io.shiftleft.codepropertygraph.cpgloading.CpgLoaderConfig

import scala.collection.mutable

class CpgSplitter {

  /**
    * Analyze the CPG at `cpgFilename` to determine its independent
    * sub graphs.
    * */
  def split(cpgFilename: String, outDirname: String): Unit = {
    val config = CpgLoaderConfig.default
    config.patterns = List(".*methods.*")
    val loader = new ProtoCpgStreamLoader()

    val callees = new ParallelIteratorExecutor(loader.loadStreamOfNodeCpgs(cpgFilename, config)).map{
       cpg =>
         val ret = cpg.method.head.fullName -> cpg.call.name().l
         cpg.close()
        ret
    }.toMap

    val allMethods = callees.keys.toList
    val cache : mutable.HashMap[String, List[String]] = mutable.HashMap()

    def cpgFor(m : String, visited : List[String] = List()) : List[String] = {

      if (visited.contains(m)) {
        return List()
      }

      if (cache.contains(m)) {
        cache(m)
      } else {
        cache(m) = m :: callees.getOrElse(m, List()).flatMap(cpgFor(_, m :: visited))
        cache(m)
      }
    }

    val cpgs = allMethods.map(cpgFor(_).distinct)

    println(allMethods.size)
    println(cpgs.size)
//    cpgs.foreach{ cpg =>
//      println(cpg.size)
//    }

  }

}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelIteratorExecutor[T](iterator: Iterator[T]) {
  def map[D](func: T => D): Iterator[D] = {
    val futures = Future.traverse(iterator) { element =>
      Future {
        func(element)
      }
    }
    Await.result(futures, Duration.Inf)
  }
}
