syntax = "proto3";

package cpg;

option go_package = "github.com/ShiftLeftSecurity/proto/cpg";
option java_package = "io.shiftleft.proto.cpg";
option java_outer_classname = "Cpg";
option csharp_namespace = "io.shiftleft.proto.cpg";

enum NodePropertyName {
  UNKNOWN_NODE_PROPERTY = 0;
  $nodeKeys
}

enum EdgePropertyName {
  UNKNOWN_EDGE_PROPERT = 0;
  $edgeKeys
}

enum EvaluationStrategies {
  UNKNOWN_EVALUATION_STRATEGY = 0;
  $evaluationStrategies 
}

enum DispatchTypes {
  UNKNOWN_DISPATCH_TYPE = 0;
  $dispatchTypes
}

enum LANGUAGES {
  UNKNOWN_LANGUAGE = 0;
  $languages
}

enum FRAMEWORKS {
  UNKNOWN_FRAMEWORK = 0;
  $frameworks
}

message PropertyValue {
  oneof value {
    string string_value = 1;
    bool bool_value = 2;
    int32 int_value = 3;
    int64 long_value = 4;
    float float_value = 5;
    double double_value = 6;
  }
}

message CpgStruct {
  message Node {
    int64 key = 1;

    // Logical node type.
    enum NodeType {
      UNKNOWN_NODE_TYPE = 0;
      $nodeTypes
    }
    NodeType type = 2;

    // Node properties.
    message Property {
      NodePropertyName name = 1;
      PropertyValue value = 2;
    }
  repeated Property property = 3;
  }
  repeated Node node = 1;

  message Edge {
    reserved 5;
    reserved "key";
    // Source node.
    int64 src = 1;
    // Destination node.
    int64 dst = 2;

    // Edge type.
    enum EdgeType {
      UNKNOWN_EDGE_TYPE = 0;
      $edgeTypes
    }
    EdgeType type = 3;

    // Edge properties.
    message Property {
      EdgePropertyName name = 1;
      PropertyValue value = 2;
    }
  repeated Property property = 4;
  }
  repeated Edge edge = 2;
}


message AdditionalNodeProperty {
  int64 node_id = 1;
  CpgStruct.Node.Property property = 2;
}

message AdditionalEdgeProperty {
  int64 edge_id = 1;
  CpgStruct.Edge.Property property = 2;
}

message CpgOverlay {
  repeated CpgStruct.Node node = 1;
  repeated CpgStruct.Edge edge = 2;
  repeated AdditionalNodeProperty node_property = 3;
  repeated AdditionalEdgeProperty edge_property = 4;
}
