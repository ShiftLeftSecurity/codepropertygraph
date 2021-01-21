package io.shiftleft.console

import io.shiftleft.semanticcpg.language._
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec
import io.shiftleft.macros.QueryMacros.queryInit
import io.shiftleft.codepropertygraph.Cpg

object TestBundle extends QueryBundle {
  @q def foo(n: Int = 4): Query = Query(
    name = "a-name",
    author = "an-author",
    title = "a-title",
    description = s"a-description $n",
    score = 2.0,
    cpg => cpg.method
  )
}

class QueryDatabaseTests extends AnyWordSpec with should.Matchers {
  "QueryDatabase" should {
    "contain Metrics bundle" in {
      new QueryDatabase(namespace = "io.shiftleft.console").allBundles.count { bundle =>
        bundle.getName.endsWith("TestBundle$")
      } shouldBe 1
    }

    "contain `foo` query" in {
      // val qdb = new QueryDatabase(namespace = "io.shiftleft.console")
      // val testBundles = qdb.allBundles.filter { bundle =>
      //   bundle.getName.endsWith("TestBundle$")
      // }
      // testBundles.size shouldBe 1
      // val testBundle = testBundles.head
      // val queries = qdb.queriesInBundle(testBundle)
      // queries.count(_.title == "a-title") shouldBe 1
      // printExact {
      //   val x = "abc"
      //   x + 1
      // }

      // intended usage
      queryInit("a-name", "an-author", "a-title", "a-description", 2.0, {cpg: Cpg => cpg.method.l} )
    }
  }
}
