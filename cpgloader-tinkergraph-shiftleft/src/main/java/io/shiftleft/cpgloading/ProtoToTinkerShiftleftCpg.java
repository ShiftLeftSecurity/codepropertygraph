package io.shiftleft.cpgloading;

import io.shiftleft.proto.cpg.Cpg;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class ProtoToTinkerShiftleftCpg extends ProtoToCpg {

  public ProtoToTinkerShiftleftCpg() {
    super(TinkerGraph.open(
      // TODO generate factories for case classes
      // io.shiftleft.codepropertygraph.generated.nodes.Factories$.MODULE$.AllAsJava(),
      // io.shiftleft.codepropertygraph.generated.edges.Factories$.MODULE$.AllAsJava()
    ));
  }

  @Override
  public void addNodes(Cpg.CpgStruct protoCpg) {
    for (Cpg.CpgStruct.Node protoNode : protoCpg.getNodeList()) {
      // if (elementImportCounter % 1000 == 0) {
      //   long millisSinceLastBatch = System.currentTimeMillis() - lastStart;
      //   lastStart = System.currentTimeMillis();
      //   System.out.println("importing node " + elementImportCounter + "; millis since last batch: " + millisSinceLastBatch);
      // }
      elementImportCounter++;
      Vertex node;
      try {
        node = graph.addVertex(T.id, protoNode.getKey(),
          T.label, protoNode.getType().name());
        keyToVertexId.put(protoNode.getKey(), node.id());
      } catch (IllegalArgumentException exception) {
        logger.warn("Failed to insert a vertex", exception);
        continue;
      }
      for (Cpg.CpgStruct.Node.Property property : protoNode.getPropertyList()) {
        addPropertyToElement(node, property.getName().name(), property.getValue());
      }
    }
  }
}
