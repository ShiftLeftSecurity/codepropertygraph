package io.shiftleft.cpgloading.neo4j;

import io.shiftleft.cpgloading.ProtoToCpgBase;
import io.shiftleft.proto.cpg.Cpg;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.neo4j.structure.Neo4jGraph;

public class ProtoToCpg extends ProtoToCpgBase {

  public ProtoToCpg(String dbPath) {
    super(Neo4jGraph.open(dbPath));
  }

  @Override
  public void addNodes(Cpg.CpgStruct protoCpg) {
    for (Cpg.CpgStruct.Node protoNode : protoCpg.getNodeList()) {
      if (elementImportCounter % 1000 == 0) {
        long millisSinceLastBatch = System.currentTimeMillis() - lastStart;
        lastStart = System.currentTimeMillis();
        System.out.println("importing node " + elementImportCounter + "; millis since last batch: " + millisSinceLastBatch);
      }
      if (elementImportCounter % 10000 == 0) {
        commit();
      }
      elementImportCounter++;
      Vertex node;
      try {
        node = graph.addVertex(T.label, protoNode.getType().name());
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
