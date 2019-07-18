//package io.shiftleft.codepropertygraph.cpgloading;
//
//import gnu.trove.map.hash.THashMap;
//import gnu.trove.set.TLongSet;
//import io.shiftleft.proto.cpg.Cpg;
//import org.apache.commons.lang.NotImplementedException;
//import org.apache.tinkerpop.gremlin.structure.Direction;
//import org.apache.tinkerpop.gremlin.tinkergraph.storage.Serializer;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//public class ProtoNodeSerializer extends Serializer<Cpg.CpgStruct.Node> {
//
//  //NodeId -> EdgeLabel -> EdgeId
//  private final Map<Long, Map<String, TLongSet>> inEdgesByNodeId;
//  private final Map<Long, Map<String, TLongSet>> outEdgesByNodeId;
//
//  public ProtoNodeSerializer(Map<Long, Map<String, TLongSet>> inEdgesByNodeId, Map<Long, Map<String, TLongSet>> outEdgesByNodeId) {
//    this.inEdgesByNodeId = inEdgesByNodeId;
//    this.outEdgesByNodeId = outEdgesByNodeId;
//  }
//
//  @Override
//  protected long getId(Cpg.CpgStruct.Node node) {
//    return node.getKey();
//  }
//
//  @Override
//  protected String getLabel(Cpg.CpgStruct.Node node) {
//    return node.getType().name();
//  }
//
//  @Override
//  protected Map<String, Object> getProperties(Cpg.CpgStruct.Node node) {
//    final Map<String, Object> propertyMap = new THashMap<>(node.getPropertyCount());
//    for (Cpg.CpgStruct.Node.Property property : node.getPropertyList()) {
//      final String key = property.getName().name();
//      final Cpg.PropertyValue propertyValue = property.getValue();
//      switch (propertyValue.getValueCase()) {
//        case INT_VALUE:
//          propertyMap.put(key, propertyValue.getIntValue());
//          break;
//        case STRING_VALUE:
//          propertyMap.put(key, propertyValue.getStringValue());
//          break;
//        case BOOL_VALUE:
//          propertyMap.put(key, propertyValue.getBoolValue());
//          break;
//        case STRING_LIST:
//          propertyMap.put(key, propertyValue.getStringList().getValuesList());
//          break;
//        case VALUE_NOT_SET:
//          break;
//        default:
//          throw new RuntimeException("Error: unsupported property case: " + propertyValue.getValueCase().name());
//      }
//    }
//    return propertyMap;
//  }
//
//  @Override
//  protected Map<String, TLongSet> getEdgeIds(Cpg.CpgStruct.Node node, Direction direction) {
//    final Set<Cpg.CpgStruct.Edge> edges;
//    switch (direction) {
//      case IN:
//        return inEdgesByNodeId.getOrDefault(node.getKey(), new HashMap(0));
//      case OUT:
//        return outEdgesByNodeId.getOrDefault(node.getKey(), new HashMap(0));
//      default:
//        throw new NotImplementedException();
//    }
//  }
//}
