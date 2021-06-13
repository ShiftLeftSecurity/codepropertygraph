package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{Local, NewLocation}
import io.shiftleft.semanticcpg.NodeExtension
import io.shiftleft.semanticcpg.language.{HasLocation, LocationCreator, _}

class LocalMethods(val local: Local) extends NodeExtension with HasLocation {
  override def location: NewLocation = {
    LocationCreator(
      local,
      local.name,
      local.label,
      local.lineNumber,
      local.method.head
    )
  }
}
