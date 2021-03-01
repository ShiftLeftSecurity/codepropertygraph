package io.shiftleft.console

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

package object scan {

  implicit class ScannerStarters(val cpg: Cpg) extends AnyVal {
    def finding: Traversal[nodes.Finding] =
      cpg.graph.nodes(NodeTypes.FINDING).cast[nodes.Finding]
  }

  implicit class QueryWrapper(q: Query) {

    /**
      * Obtain list of findings by running query on CPG
      * */
    def apply(cpg: Cpg): List[nodes.NewFinding] = {
      q.traversal(cpg)
        .map(
          evidence =>
            finding(evidence = evidence,
                    name = q.name,
                    author = q.author,
                    title = q.title,
                    description = q.description,
                    score = q.score))
        .l
    }
  }

  private object FindingKeys {
    val name = "name"
    val author = "author"
    val title = "title"
    val description = "description"
    val score = "score"
  }

  implicit class ScannerFindingStep(val traversal: Traversal[nodes.Finding]) extends AnyRef {

    def name: Traversal[String] = traversal.map(_.name)

    def author: Traversal[String] = traversal.map(_.author)

    def title: Traversal[String] = traversal.map(_.title)

    def description: Traversal[String] = traversal.map(_.description)

    def score: Traversal[Double] = traversal.map(_.score)

  }

  implicit class ScannerFindingExtension(val node: nodes.Finding) extends AnyRef {

    def name: String = getValue(FindingKeys.name)

    def author: String = getValue(FindingKeys.author)

    def title: String = getValue(FindingKeys.title)

    def description: String = getValue(FindingKeys.description)

    def score: Double = getValue(FindingKeys.score).toDouble

    protected def getValue(key: String, default: String = ""): String =
      node.keyValuePairs.find(_.key == key).map(_.value).getOrElse(default)

  }

  private def finding(evidence: nodes.StoredNode,
                      name: String,
                      author: String,
                      title: String,
                      description: String,
                      score: Double): nodes.NewFinding = {
    nodes
      .NewFinding()
      .evidence(List(evidence))
      .keyValuePairs(List(
        nodes.NewKeyValuePair().key(FindingKeys.name).value(name),
        nodes.NewKeyValuePair().key(FindingKeys.author).value(author),
        nodes.NewKeyValuePair().key(FindingKeys.title).value(title),
        nodes.NewKeyValuePair().key(FindingKeys.description).value(description),
        nodes.NewKeyValuePair().key(FindingKeys.score).value(score.toString)
      ))
  }

  /**
    * Print human readable list of findings to standard out.
    * */
  def outputFindings(cpg: Cpg): Unit = {
    cpg.finding.sortBy(_.score.toInt).foreach { finding =>
      val evidence = finding.evidence.headOption
        .map { e =>
          s"${e.location.filename}:${e.location.lineNumber.getOrElse(0)}:${e.location.methodFullName}"
        }
        .getOrElse("")
      println(s"Result: ${finding.score} : ${finding.title}: $evidence")
    }
  }

}
