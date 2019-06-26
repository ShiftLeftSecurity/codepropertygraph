package io.shiftleft.codepropertygraph.cpgloading;

import io.shiftleft.proto.cpg.Cpg;

import java.util.concurrent.atomic.AtomicLong;

public class ProtoEdgeWithId {
  private static AtomicLong edgeIdManager = new AtomicLong(0);

  public final Cpg.CpgStruct.Edge edge;
  public final long id = edgeIdManager.getAndIncrement();

  public ProtoEdgeWithId(Cpg.CpgStruct.Edge edge) {
    this.edge = edge;
  }
}
