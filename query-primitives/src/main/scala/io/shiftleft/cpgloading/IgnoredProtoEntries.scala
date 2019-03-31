package io.shiftleft.cpgloading

/* some non-public frontends (e.g. java2cpg) use cpg proto entries that are `UNRECOGNIZED` by cpg-public.
 we'll ignore those during the import */
case class IgnoredProtoEntries(nodeTypes: Set[Int], nodeKeys: Set[Int])
