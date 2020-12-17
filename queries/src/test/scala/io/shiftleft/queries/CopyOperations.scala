package io.shiftleft.queries

import io.shiftleft.semanticcpg.language._
import io.shiftleft.fuzzyc2cpg.testfixtures.CodeToCpgSuite
import overflowdb.traversal._

class CopyOperations extends CodeToCpgSuite {

  override val code =
    """
      int index_into_dst_array (char *dst, char *src, int offset) {
        for(i = 0; i < strlen(src); i++) {
          dst[i + + j*8 + offset] = src[i];
        }
      }

      // We do not want to detect this one because the
      // index only specifies where to read from
      int index_into_src_array() {
        for(i = 0; i < strlen(src); i++) {
          dst[k] = src[i];
        }
      }

    """

  "find indexed buffer assigment targets" in {
    cpg.assignment.target.isArrayAccess.dedup.map { arrAccess =>
      arrAccess.subscripts.code.toSet
    }.toSet shouldBe Set(Set("k"), Set("i", "j", "offset"))
  }

  "find indexed buffer assignment targets in loops where index is incremented" in {

    /**
      * For (buf, indices) pairs, determine those inside control structures (for, while, if ...)
      * where any of the calls made outside of the body (block) are Inc operations. Determine
      * the first argument of that Inc operation and check if they are used as indices for
      * the write operation into the buffer.
      * */
    cpg.assignment.target.isArrayAccess
      .map { access =>
        (access.array, access.subscripts.code.toSet)
      }
      .filter {
        case (buf, subscripts) =>
          val incIdentifiers = buf.inAst.isControlStructure.astChildren
            .filterNot(_.isBlock)
            .assignments
            .target
            .code
            .toSet
          (incIdentifiers & subscripts).nonEmpty
      }
      .map(_._1)
      .method
      .name
      .l shouldBe List("index_into_dst_array")

  }

}
