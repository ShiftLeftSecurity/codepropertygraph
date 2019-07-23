package io.shiftleft.codepropertygraph.cpgloading;

import io.shiftleft.codepropertygraph.Cpg;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.Property;
import org.apache.commons.configuration.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProtoToCpg {
  private Logger logger = LogManager.getLogger(getClass());
  private NodeFilter nodeFilter = new NodeFilter();
  private TinkerGraph tinkerGraph;

  public ProtoToCpg() {
    this(OnDiskOverflowConfig.defaultForJava());
  }

  public ProtoToCpg(
    OnDiskOverflowConfig onDiskOverflowConfig) {
    Configuration configuration = TinkerGraph.EMPTY_CONFIGURATION();
    if (onDiskOverflowConfig.enabled()) {

      configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, true);
      configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_OVERFLOW_HEAP_PERCENTAGE_THRESHOLD,
              onDiskOverflowConfig.heapPercentageThreshold());
      onDiskOverflowConfig.graphLocationAsJava().ifPresent(path ->
        configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_LOCATION, path));
    } else {
      configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, false);
    }

    this.tinkerGraph = TinkerGraph.open(
      configuration,
      io.shiftleft.codepropertygraph.generated.nodes.Factories$.MODULE$.AllAsJava(),
      io.shiftleft.codepropertygraph.generated.edges.Factories$.MODULE$.AllAsJava());
  }

  public void addNodes(Collection<Node> nodes) {
    for (Node node : nodes) {
      try {
        if (nodeFilter.filterNode(node)) {
          List<Property> properties = node.getPropertyList();
          final ArrayList<Object> keyValues = new ArrayList<>(4 + (2 * properties.size()));
          keyValues.add(T.id);
          keyValues.add(node.getKey());
          keyValues.add(T.label);
          keyValues.add(node.getType().name());
          for (Property property: properties) {
            addProperties(keyValues, property.getName().name(), property.getValue());
          }

          tinkerGraph.addVertex(keyValues.toArray());
        }
      } catch (Exception exception) {
        logger.warn("Failed to insert a vertex. proto:\n" + node, exception);
      }
    }
  }

  public void addEdges(Collection<Edge> protoEdges) {
    for (Edge edge : protoEdges) {
      long srcNodeId = edge.getSrc();
      long dstNodeId = edge.getDst();
      if (srcNodeId == -1 || dstNodeId == -1) {
        throw new IllegalArgumentException("edge " + edge + " has illegal src|dst node. something seems wrong with the cpg");
      }
      Iterator<Vertex> srcVertices = tinkerGraph.vertices(srcNodeId);
      if (!srcVertices.hasNext()) {
        throw new NoSuchElementException("Couldn't find source node " + srcNodeId + " for edge to " + dstNodeId + " of type " + edge.getType().name());
      }
      Vertex srcVertex = srcVertices.next();
      Iterator<Vertex> dstVertices = tinkerGraph.vertices(dstNodeId);
      if (!dstVertices.hasNext()) {
        throw new NoSuchElementException("Couldn't find destination node " + dstNodeId + " for edge from " + srcNodeId + " of type " + edge.getType().name());
      }
      Vertex dstVertex = dstVertices.next();

      List<Edge.Property> properties = edge.getPropertyList();
      final ArrayList<Object> keyValues = new ArrayList<>(2 * properties.size());
      for (Edge.Property property: properties) {
        addProperties(keyValues, property.getName().name(), property.getValue());
      }

      try {
        srcVertex.addEdge(edge.getType().name(), dstVertex, keyValues.toArray());
      } catch (IllegalArgumentException exception) {
        String context = "label=" + edge.getType().name() +
          ", srcNodeId=" + srcNodeId +
          ", dstNodeId=" + dstNodeId +
          ", srcVertex=" + srcVertex +
          ", dstVertex=" + dstVertex;
        logger.warn("Failed to insert an edge. context: " + context, exception);
        continue;
      }
    }
  }

  public static void addProperties(ArrayList<Object> tinkerKeyValues, String name, io.shiftleft.proto.cpg.Cpg.PropertyValue propertyValue) {
    switch(propertyValue.getValueCase()) {
      case INT_VALUE:
        tinkerKeyValues.add(name);
        tinkerKeyValues.add(propertyValue.getIntValue());
        break;
      case STRING_VALUE:
        tinkerKeyValues.add(name);
        tinkerKeyValues.add(propertyValue.getStringValue());
        break;
      case BOOL_VALUE:
        tinkerKeyValues.add(name);
        tinkerKeyValues.add(propertyValue.getBoolValue());
        break;
      case STRING_LIST:
        propertyValue.getStringList().getValuesList().forEach(value -> {
          tinkerKeyValues.add(name);
          tinkerKeyValues.add(value);
        });
        break;
      case VALUE_NOT_SET:
        break;
      default:
        throw new RuntimeException("Error: unsupported property case: " + propertyValue.getValueCase().name());
    }
  }

  public Cpg build() {
    return new Cpg(tinkerGraph);
  }
}
