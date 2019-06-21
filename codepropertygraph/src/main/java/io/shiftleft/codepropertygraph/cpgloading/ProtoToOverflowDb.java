package io.shiftleft.codepropertygraph.cpgloading;

import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import io.shiftleft.proto.cpg.Cpg;
import org.apache.tinkerpop.gremlin.tinkergraph.storage.OndiskOverflow;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class ProtoToOverflowDb extends ProtoToX<File> {
  private final OndiskOverflow overflowDb;
  private final Map<Long, Map<String, TLongSet>> inEdgesByNodeId = new HashMap<>(); //NodeId -> EdgeLabel -> EdgeIds
  private final Map<Long, Map<String, TLongSet>> outEdgesByNodeId = new HashMap<>(); //NodeId -> EdgeLabel -> EdgeIds
  private final ProtoNodeSerializer nodeSerializer = new ProtoNodeSerializer(inEdgesByNodeId, outEdgesByNodeId);
  private final ProtoEdgeSerializer edgeSerializer = new ProtoEdgeSerializer();
  private Collection<Cpg.CpgStruct.Node> nodes;

  public ProtoToOverflowDb() throws IOException {
    this(File.createTempFile("overflowdb", "bin"));
  }

  public ProtoToOverflowDb(File writeTo) {
    this.overflowDb = OndiskOverflow.createWithSpecificLocation(writeTo);
  }

  @Override
  public void addNodes(Collection<Cpg.CpgStruct.Node> nodes) {
    /** proto nodes don't have their adjacent edges, but that's required for the OverflowDb serializer,
     * so we can only bring it together in the final `build` step */
    this.nodes = nodes;
  }

  @Override
  public void addEdges(Collection<Cpg.CpgStruct.Edge> edges) {
    final int characteristics = Spliterator.IMMUTABLE | Spliterator.CONCURRENT | Spliterator.NONNULL;
    final boolean parallel = true;
    final Spliterator<Cpg.CpgStruct.Edge> spliterator = Spliterators.spliterator(edges, characteristics);
    StreamSupport.stream(spliterator, parallel).forEach(edge -> {
      final ProtoEdgeWithId edgeWithId = new ProtoEdgeWithId(edge);
      final String label = edge.getType().name();

      // populate maps that are shared with `nodeSerializer`
      inEdgesByNodeId
          .computeIfAbsent(edgeWithId.edge.getSrc(), z -> new HashMap<>())
          .computeIfAbsent(label, z -> new TLongHashSet())
          .add(edgeWithId.id);
      outEdgesByNodeId
          .computeIfAbsent(edgeWithId.edge.getDst(), z -> new HashMap<>())
          .computeIfAbsent(label, z -> new TLongHashSet())
          .add(edgeWithId.id);

      try {
        final byte[] bytes = edgeSerializer.serialize(edgeWithId);
        overflowDb.getEdgeMVMap().put(edgeWithId.id, bytes);
      } catch (IOException e) {
        logger.error("error while serializing " + edge, e);
      }
    });
  }

  @Override
  public File build() {
    // now that we have the edges, we can finally serialize the nodes
    assert nodes != null;
    final int characteristics = Spliterator.IMMUTABLE | Spliterator.CONCURRENT | Spliterator.NONNULL;
    final boolean parallel = true;
    final Spliterator<Cpg.CpgStruct.Node> spliterator = Spliterators.spliterator(nodes, characteristics);
    StreamSupport.stream(spliterator, parallel).forEach(node -> {
      try {
        final byte[] bytes = nodeSerializer.serialize(node);
        overflowDb.getVertexMVMap().put(node.getKey(), bytes);
      } catch (IOException e) {
        logger.error("error while serializing " + node, e);
      }
    });

    overflowDb.close();
    return overflowDb.getStorageFile();
  }

}