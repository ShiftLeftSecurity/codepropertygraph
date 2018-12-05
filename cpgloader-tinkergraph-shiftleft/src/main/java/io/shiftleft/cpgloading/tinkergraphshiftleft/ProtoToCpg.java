package io.shiftleft.cpgloading.tinkergraphshiftleft;

import io.shiftleft.codepropertygraph.generated.NodeKeys;
import io.shiftleft.cpgloading.ProtoToCpgBase;
import io.shiftleft.proto.cpg.Cpg;
import java.util.List;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedElementFactory;

public class ProtoToCpg extends ProtoToCpgBase {

  public ProtoToCpg() {
    super(TinkerGraph.open(
      (List<SpecializedElementFactory.ForVertex<?,?>>)
        io.shiftleft.codepropertygraph.generated.nodes.Factories$.MODULE$.AllAsJava(),
      (List<SpecializedElementFactory.ForEdge<?,?>>)
        io.shiftleft.codepropertygraph.generated.edges.Factories$.MODULE$.AllAsJava()
    ));
    ((TinkerGraph) graph).createIndex(NodeKeys.FULL_NAME.name(), Vertex.class);
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
