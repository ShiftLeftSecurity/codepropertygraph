package io.shiftleft.cpgloading;

import io.shiftleft.codepropertygraph.generated.NodeTypes;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.Property;
import io.shiftleft.proto.cpg.Cpg.PropertyValue;
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase;
import io.shiftleft.queryprimitives.steps.starters.Cpg;

import org.apache.commons.configuration.Configuration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.util.*;

public class ProtoToCpg {

  TinkerGraph tinkerGraph;
  private Logger logger = LogManager.getLogger(getClass());
  private NodeFilter nodeFilter = new NodeFilter();
  public final Optional<IgnoredProtoEntries> ignoredProtoEntries;

  public ProtoToCpg(Optional<IgnoredProtoEntries> ignoredProtoEntries) {
    this(Optional.empty(), ignoredProtoEntries);
  }

  public ProtoToCpg(
    Optional<OnDiskOverflowConfig> onDiskOverflowConfig,
    Optional<IgnoredProtoEntries> ignoredProtoEntries) {
    Configuration configuration = TinkerGraph.EMPTY_CONFIGURATION();
    onDiskOverflowConfig.ifPresent(config -> {
      configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, true);
      configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_CACHE_MAX_HEAP_PERCENTAGE, config.cacheHeapPercentage());
      if (config.alternativeParentDirectory().isDefined()) {
        configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_ROOT_DIR, config.alternativeParentDirectory().get());
      }
    });
    this.ignoredProtoEntries = ignoredProtoEntries;

    this.tinkerGraph = TinkerGraph.open(
      configuration,
      io.shiftleft.codepropertygraph.generated.nodes.Factories$.MODULE$.AllAsJava(),
      io.shiftleft.codepropertygraph.generated.edges.Factories$.MODULE$.AllAsJava()); 
  }

  public void addNodes(List<Node> nodes) {
    for (Node node : nodes) {
      try {
        if (nodeFilter.filterNode(node)) {
          List<Property> properties = node.getPropertyList();
          final ArrayList<Object> keyValues = new ArrayList<>(4 + (2 * properties.size()));
          keyValues.add(T.id);
          keyValues.add(node.getKey());
          keyValues.add(T.label);
          if (ignoredProtoEntries.isPresent() && ignoredProtoEntries.get().nodeTypes().contains(node.getTypeValue())) {
            // only defined for cpg-internal schema, insert an UNKOWN node without properties instead
            keyValues.add(NodeTypes.UNKNOWN);
          } else {
            keyValues.add(node.getType().name());
            for (Property property: properties) {
              if (!ignoredProtoEntries.isPresent() || !ignoredProtoEntries.get().nodeKeys().contains(property.getNameValue())) {
                addProperties(keyValues, property.getName().name(), property.getValue());
              }
            }
          }

          tinkerGraph.addVertex(keyValues.toArray());
        }
      } catch (Exception exception) {
        logger.warn("Failed to insert a vertex. proto:\n" + node, exception);
      }
    }
  }

  public void addEdges(List<Edge> protoEdges) {
    for (Edge edge : protoEdges) {
      long srcNodeId = edge.getSrc();
      long dstNodeId = edge.getDst();
      Vertex srcVertex = tinkerGraph.vertices(srcNodeId).next();
      Vertex dstVertex = tinkerGraph.vertices(dstNodeId).next();

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

  public static void addProperties(ArrayList<Object> tinkerKeyValues, String name, PropertyValue propertyValue) {
    ValueCase valueCase = propertyValue.getValueCase();
    switch(valueCase) {
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
        throw new RuntimeException("Error: unsupported property case: " + valueCase.name());
    }
  }

  public Cpg build() {
    return new Cpg(tinkerGraph);
  }
}
