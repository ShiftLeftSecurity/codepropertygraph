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

    val callers : mutable.Map[String, List[String]] = mutable.HashMap()
    callees.foreach { case (method : String, children : List[String]) =>
      children.foreach{ child =>
        if (!callers.contains(child)) {
          callers(child) = List(method)
        } else {
          callers(child) ++= List(method)
        }
      }
    }


    val allMethods = callees.keys.toList
    val forwardCache : mutable.HashMap[String, List[String]] = mutable.HashMap()
    val backwardCache : mutable.HashMap[String, List[String]] = mutable.HashMap()

    def cpgForForward(m : String, visited : List[String] = List()) : List[String] =
      cpgForShared(m, visited, callees, forwardCache)

    def cpgForBackward(m : String, visited : List[String] =  List()) : List[String] =
      cpgForShared(m, visited, callers.toMap, backwardCache)

    def cpgForShared(m : String, visited : List[String],
                     adjMap: Map[String, List[String]],
                     cache : mutable.HashMap[String, List[String]]) : List[String] = {
      if (visited.contains(m)) {
        return List()
      }
      if (cache.contains(m)) {
        cache(m)
      } else {
        cache(m) = m :: adjMap.getOrElse(m, List()).flatMap(cpgForForward(_, m :: visited))
        cache(m)
      }
    }

    def cpgFor(m : String) : List[String] = cpgForForward(m) ++ cpgForBackward(m)

    val epsilon = 2
    val cpgs = allMethods.map(cpgFor(_).distinct).map(_.toSet).distinct
    val cpgsWithoutSubSets = cpgs.filter(c => !cpgs.exists{ c1 => c != c1 && (c diff c1).size <= epsilon })

    println(allMethods.size)
    println(cpgsWithoutSubSets.size)
    // cpgs.foreach(println(_))

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
