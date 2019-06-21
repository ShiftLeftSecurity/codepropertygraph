package io.shiftleft.codepropertygraph.cpgloading;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import io.shiftleft.proto.cpg.Cpg;
import org.apache.commons.lang.NotImplementedException;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.tinkergraph.storage.Serializer;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ProtoEdgeSerializer extends Serializer<ProtoEdgeWithId> {

  @Override
  protected long getId(ProtoEdgeWithId edgeWithId) {
    return edgeWithId.id;
  }

  @Override
  protected String getLabel(ProtoEdgeWithId edgeWithId) {
    return edgeWithId.edge.getType().name();
  }

  @Override
  protected Map<String, Object> getProperties(ProtoEdgeWithId edgeWithId) {
    final Map<String, Object> propertyMap = new THashMap<>(edgeWithId.edge.getPropertyCount());
    for (Cpg.CpgStruct.Edge.Property property : edgeWithId.edge.getPropertyList()) {
      final String key = property.getName().name();
      final Cpg.PropertyValue propertyValue = property.getValue();
      switch(propertyValue.getValueCase()) {
        case INT_VALUE:
          propertyMap.put(key, propertyValue.getIntValue());
          break;
        case STRING_VALUE:
          propertyMap.put(key, propertyValue.getStringValue());
          break;
        case BOOL_VALUE:
          propertyMap.put(key, propertyValue.getBoolValue());
          break;
        case STRING_LIST:
          propertyMap.put(key, propertyValue.getStringList().getValuesList());
          break;
        case VALUE_NOT_SET:
          break;
        default:
          throw new RuntimeException("Error: unsupported property case: " + propertyValue.getValueCase().name());
      }
    }
    return propertyMap;
  }

  @Override
  protected Map<String, TLongSet> getEdgeIds(ProtoEdgeWithId edgeWithId, Direction direction) {
    final TLongSet nodeIds = new TLongHashSet(1);

    switch (direction) {
      case IN:
        nodeIds.add(edgeWithId.edge.getSrc());
        break;
      case OUT:
        nodeIds.add(edgeWithId.edge.getDst());
        break;
      default:
        throw new NotImplementedException();
    }
    final Map<String, TLongSet> adjacentNodeIds = new THashMap<>(1);
    adjacentNodeIds.put(direction.name(), nodeIds);
    return adjacentNodeIds;
  }
}
