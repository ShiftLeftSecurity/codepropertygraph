package io.shiftleft.codepropertygraph.cpgloading;

import io.shiftleft.proto.cpg.Cpg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ProtoToX<A> {
  protected Logger logger = LogManager.getLogger(getClass());
  protected NodeFilter nodeFilter = new NodeFilter();

  public abstract void addNodes(Iterable<Cpg.CpgStruct.Node> nodes);

  public abstract void addEdges(Iterable<Cpg.CpgStruct.Edge> protoEdges);

  public abstract A build();
}
