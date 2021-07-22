package io.shiftleft.passes

abstract class ParallelCpgPass[T](keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase[T](keyPools) {

}
