// TODO uncomment once moved to separate library

/*

package jsonmerge

import org.scalatest._
import ujson._

class SchemaMergerTest extends WordSpec with Matchers {

  "adds independent elements" in {
    val jsonA = """{"nodeKeys": [{"name":"foo", "field1":"value1"}]}"""
    val jsonB = """{"edgeKeys": [{"name":"bar", "field2":"value2"}]}"""

    val result = merge(jsonA, jsonB)
    result shouldBe read(
      """{
      "nodeKeys": [{"name":"foo", "field1":"value1"}],
      "edgeKeys": [{"name":"bar", "field2":"value2"}]
      }""")
  }

  "joins separate elements from same collection" in {
    val jsonA = """{"nodeKeys": [{"name":"foo", "field1":"value1"}]}"""
    val jsonB = """{"nodeKeys": [{"name":"bar", "field2":"value2"}]}"""

    val result = merge(jsonA, jsonB)
    result shouldBe read(
      """{
      "nodeKeys": [{"name":"bar", "field2":"value2"}, {"name":"foo", "field1":"value1"}]
      }""")
  }

  "combines elements with same `name`" in {
    val jsonA = """{"nodeKeys": [{"name":"foo", "field1": "value1"}]}"""
    val jsonB = """{"nodeKeys": [{"name":"foo", "field2": "value2"}]}"""

    val result = merge(jsonA, jsonB)
    result shouldBe read(
      """{
      "nodeKeys": [{"name":"foo", "field1": "value1", "field2": "value2"}]
      }""")
  }

  "errors if same element has property defined multiple times with different values" in {
    val jsonA = """{"nodeKeys": [{"name":"foo", "field1": "value1"}]}"""
    val jsonB = """{"nodeKeys": [{"name":"foo", "field1": "value2"}]}"""

    intercept[AssertionError] {
      merge(jsonA, jsonB)
    }
  }

  "errors if collection contains element with duplicate IDs" in {
    val jsonA = """{"nodeKeys": [{"name":"foo", "id":1}]}"""
    val jsonB = """{"nodeKeys": [{"name":"bar", "id":1}]}"""

    intercept[AssertionError] {
      merge(jsonA, jsonB)
    }
  }

  "for any node, automatically add a generic `CONTAINS_NODE` outEdge" in {
    val json =
      """{ "nodeTypes": [
           {
             "name":"CALL_SITE",
             "outEdges": [
               { "edgeName": "SOME_EDGE", "inNodes": [ "SOME_OTHER_NODE" ] }
             ]
           }
         ]}"""

    val result = SchemaMerger.addMissingContainsEdges(read(json).obj)
    result shouldBe read(
      """{ "nodeTypes": [
           {
             "name":"CALL_SITE",
             "outEdges": [
               { "edgeName": "SOME_EDGE", "inNodes": [ "SOME_OTHER_NODE" ] },
               { "edgeName": "CONTAINS_NODE", "inNodes": [ "NODE" ] }
             ]
           }
         ]}""")
  }


  "for any node that has `containedNode` entries, automatically add the corresponding `outEdges`" in {
    val json =
      """{ "nodeTypes": [
           {
             "name":"CALL_SITE",
             "containedNodes": [{ "nodeType": "METHOD" }, { "nodeType": "CALL" } ],
             "outEdges": [
               { "edgeName": "SOME_EDGE", "inNodes": [ "SOME_OTHER_NODE" ] }
             ]
           }
         ]}"""

    SchemaMerger.addMissingContainsEdges(read(json).obj) shouldBe read(
      """{ "nodeTypes": [
           {
             "name":"CALL_SITE",
             "containedNodes": [{ "nodeType": "METHOD" }, { "nodeType": "CALL" } ],
             "outEdges": [
               { "edgeName": "SOME_EDGE", "inNodes": [ "SOME_OTHER_NODE" ] },
               { "edgeName": "CONTAINS_NODE", "inNodes": [ "NODE", "METHOD", "CALL" ] }
             ]
           }
         ]}""")
  }

  def merge(jsonA: String, jsonB: String) =
    SchemaMerger.mergeCollections(
      Seq(jsonA, jsonB).map(json => Obj(read(json).obj))
    )
}
 */
