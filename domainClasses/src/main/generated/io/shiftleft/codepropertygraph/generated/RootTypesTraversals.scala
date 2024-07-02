package io.shiftleft.codepropertygraph.generated.nodes

extension (iterator: Iterator[StoredNode]) {

  final def _aliasOfOut: Iterator[StoredNode] = iterator.flatMap(_._aliasOfOut)
  final def _aliasOfIn: Iterator[StoredNode]  = iterator.flatMap(_._aliasOfIn)

  final def _argumentOut: Iterator[StoredNode] = iterator.flatMap(_._argumentOut)
  final def _argumentIn: Iterator[StoredNode]  = iterator.flatMap(_._argumentIn)

  final def _astOut: Iterator[StoredNode] = iterator.flatMap(_._astOut)
  final def _astIn: Iterator[StoredNode]  = iterator.flatMap(_._astIn)

  final def _bindsOut: Iterator[StoredNode] = iterator.flatMap(_._bindsOut)
  final def _bindsIn: Iterator[StoredNode]  = iterator.flatMap(_._bindsIn)

  final def _bindsToOut: Iterator[StoredNode] = iterator.flatMap(_._bindsToOut)
  final def _bindsToIn: Iterator[StoredNode]  = iterator.flatMap(_._bindsToIn)

  final def _callOut: Iterator[StoredNode] = iterator.flatMap(_._callOut)
  final def _callIn: Iterator[StoredNode]  = iterator.flatMap(_._callIn)

  final def _captureOut: Iterator[StoredNode] = iterator.flatMap(_._captureOut)
  final def _captureIn: Iterator[StoredNode]  = iterator.flatMap(_._captureIn)

  final def _capturedByOut: Iterator[StoredNode] = iterator.flatMap(_._capturedByOut)
  final def _capturedByIn: Iterator[StoredNode]  = iterator.flatMap(_._capturedByIn)

  final def _cdgOut: Iterator[StoredNode] = iterator.flatMap(_._cdgOut)
  final def _cdgIn: Iterator[StoredNode]  = iterator.flatMap(_._cdgIn)

  final def _cfgOut: Iterator[StoredNode] = iterator.flatMap(_._cfgOut)
  final def _cfgIn: Iterator[StoredNode]  = iterator.flatMap(_._cfgIn)

  final def _conditionOut: Iterator[StoredNode] = iterator.flatMap(_._conditionOut)
  final def _conditionIn: Iterator[StoredNode]  = iterator.flatMap(_._conditionIn)

  final def _containsOut: Iterator[StoredNode] = iterator.flatMap(_._containsOut)
  final def _containsIn: Iterator[StoredNode]  = iterator.flatMap(_._containsIn)

  final def _dominateOut: Iterator[StoredNode] = iterator.flatMap(_._dominateOut)
  final def _dominateIn: Iterator[StoredNode]  = iterator.flatMap(_._dominateIn)

  final def _evalTypeOut: Iterator[StoredNode] = iterator.flatMap(_._evalTypeOut)
  final def _evalTypeIn: Iterator[StoredNode]  = iterator.flatMap(_._evalTypeIn)

  final def _importsOut: Iterator[StoredNode] = iterator.flatMap(_._importsOut)
  final def _importsIn: Iterator[StoredNode]  = iterator.flatMap(_._importsIn)

  final def _inheritsFromOut: Iterator[StoredNode] = iterator.flatMap(_._inheritsFromOut)
  final def _inheritsFromIn: Iterator[StoredNode]  = iterator.flatMap(_._inheritsFromIn)

  final def _isCallForImportOut: Iterator[StoredNode] = iterator.flatMap(_._isCallForImportOut)
  final def _isCallForImportIn: Iterator[StoredNode]  = iterator.flatMap(_._isCallForImportIn)

  final def _parameterLinkOut: Iterator[StoredNode] = iterator.flatMap(_._parameterLinkOut)
  final def _parameterLinkIn: Iterator[StoredNode]  = iterator.flatMap(_._parameterLinkIn)

  final def _postDominateOut: Iterator[StoredNode] = iterator.flatMap(_._postDominateOut)
  final def _postDominateIn: Iterator[StoredNode]  = iterator.flatMap(_._postDominateIn)

  final def _reachingDefOut: Iterator[StoredNode] = iterator.flatMap(_._reachingDefOut)
  final def _reachingDefIn: Iterator[StoredNode]  = iterator.flatMap(_._reachingDefIn)

  final def _receiverOut: Iterator[StoredNode] = iterator.flatMap(_._receiverOut)
  final def _receiverIn: Iterator[StoredNode]  = iterator.flatMap(_._receiverIn)

  final def _refOut: Iterator[StoredNode] = iterator.flatMap(_._refOut)
  final def _refIn: Iterator[StoredNode]  = iterator.flatMap(_._refIn)

  final def _sourceFileOut: Iterator[StoredNode] = iterator.flatMap(_._sourceFileOut)
  final def _sourceFileIn: Iterator[StoredNode]  = iterator.flatMap(_._sourceFileIn)

  final def _taggedByOut: Iterator[StoredNode] = iterator.flatMap(_._taggedByOut)
  final def _taggedByIn: Iterator[StoredNode]  = iterator.flatMap(_._taggedByIn)

}
