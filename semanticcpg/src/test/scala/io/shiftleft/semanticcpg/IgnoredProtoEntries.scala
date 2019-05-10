package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.cpgloading.IgnoredProtoEntries

object IgnoredCpgEntities {
  /* some non-public frontends (e.g. java2cpg) use cpg proto entries that are `UNRECOGNIZED` by cpg-public.
   * we'll ignore those during the import */
  val forJava2Cpg =
    Some(IgnoredProtoEntries(
      nodeTypes = Set(5, 6, 7, 49),
      nodeKeys = Set(14, 16)
    ))
}
