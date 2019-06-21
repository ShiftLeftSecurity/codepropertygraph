package io.shiftleft.codepropertygraph.cpgloading;

import io.shiftleft.proto.cpg.Cpg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public abstract class ProtoToX<A> {
  protected Logger logger = LogManager.getLogger(getClass());
  protected NodeFilter nodeFilter = new NodeFilter();

  public abstract void addNodes(Collection<Cpg.CpgStruct.Node> nodes);

  public abstract void addEdges(Collection<Cpg.CpgStruct.Edge> protoEdges);

  public abstract A build();
}
