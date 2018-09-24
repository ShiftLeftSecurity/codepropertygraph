// package io.shiftleft.cpgloading;

// import io.shiftleft.proto.cpg.Cpg;
// import org.apache.tinkerpop.gremlin.structure.T;
// import org.apache.tinkerpop.gremlin.structure.Vertex;
// import org.janusgraph.core.JanusGraphFactory;

// public class ProtoToJanusCpg extends ProtoToCpg {

//   public ProtoToJanusCpg() {
//     super(
//       JanusGraphFactory.build()
//         .set("cache.db-cache", false)
//         .set("cache.tx-cache-size", 4*1000*1000)
//         .set("storage.backend", "inmemory")
//         // .set("storage.backend", "berkeleyje").set("storage.directory", "janusgraph")
//         // .set("storage.berkeleyje.cache-percentage", 1)
//         // .set("storage.berkeleyje.cache-percentage", 50)
//         // .set("storage.transactions", false)
//         // .set("storage.batch-loading", true).set("schema.default", "none")
//         .open());
//   }

//   @Override
//   public void addNodes(Cpg.CpgStruct protoCpg) {
//     for (Cpg.CpgStruct.Node protoNode : protoCpg.getNodeList()) {
//       if (elementImportCounter % 1000 == 0) {
//         long millisSinceLastBatch = System.currentTimeMillis() - lastStart;
//         lastStart = System.currentTimeMillis();
//         System.out.println("importing node " + elementImportCounter + "; millis since last batch: " + millisSinceLastBatch);
//       }
//       if (elementImportCounter % 10000 == 0) {
//         commit();
//       }
//       elementImportCounter++;
//       Vertex node;
//       try {
//         node = graph.addVertex(T.label, protoNode.getType().name());
//         keyToVertexId.put(protoNode.getKey(), node.id());
//       } catch (IllegalArgumentException exception) {
//         logger.warn("Failed to insert a vertex", exception);
//         continue;
//       }
//       for (Cpg.CpgStruct.Node.Property property : protoNode.getPropertyList()) {
//         addPropertyToElement(node, property.getName().name(), property.getValue());
//       }
//     }
//   }
// }
