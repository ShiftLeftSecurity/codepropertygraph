package io.shiftleft.queries

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}

class CopyOperations extends WordSpec with Matchers {

  val code =
    """
      int array_indexing_in_loop (char *dst, char *src, int offset) {
        for(i = 0; i < strlen(src); i++) {
          dst[i + + j*8 + offset] = src[i];
        }
      }
    """

  CodeToCpgFixture(code) { cpg =>
    "find indexed buffer assigment targets" in {
      indexBufferAssigns(cpg).l.map(_._2) shouldBe List(Set("i", "j", "offset"))
    }

    /**
      * Determine assignments where target (argument(1)) contains a computed member
      * access. For that access, determine the destination buffer and all indices
      * */
    def indexBufferAssigns(cpg: Cpg) =
      cpg.assign.target
        .ast
        .isCall
        .name(".*op.*computedMemberAccess.*")
        .map { call =>
          val indices = call.argument(2).ast.isIdentifier.code.toSet
          val buf = call.argument(1)
          (buf, indices)
        }

    "find indexed buffer assignment targets in loops where index is incremented" in {

      /**
        * For (buf, indices) pairs, determine those inside control structures (for, while, if ...)
        * where any of the calls made outside of the body (block) are Inc operations. Determine
        * the first argument of that Inc operation and check if they are used as indices for
        * the write operation into the buffer.
        * */
      indexBufferAssigns(cpg)
        .where {
          case (buf, indices) =>
            val incIdentifiers = buf.start.inAst.isControlStructure.astChildren
              .filterNot(_.isBlock)
              .ast
              .isCall
              .name(".*Inc.*")
              .argument(1)
              .code
              .toSet
            (incIdentifiers & indices).nonEmpty
        }
        .map(_._1)
        .code
        .l shouldBe List("dst")

    }

  }

}
