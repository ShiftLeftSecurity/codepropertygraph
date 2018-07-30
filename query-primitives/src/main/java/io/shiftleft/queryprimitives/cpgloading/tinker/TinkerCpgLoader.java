package io.shiftleft.queryprimitives.cpgloading.tinker;

import io.shiftleft.queryprimitives.steps.starters.Cpg;
import java.io.IOException;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class TinkerCpgLoader {

  /**
   * Load a Code Property Graph from the given Tinkerpop serialization format.
   * */
  public static Cpg loadFromTinkerFormat(String filename, TinkerFormat format) {
    Cpg codePropertyGraph;
    TinkerGraph graph;
    try {
      graph = loadCpgFromTinkerpop(filename, format);
      graph.createIndex("FULL_NAME", Vertex.class);
      codePropertyGraph = new Cpg(graph);
    } catch (IOException exception) {
      exception.printStackTrace();
      throw new RuntimeException("Cannot load CPG from: " + filename, exception);
    }
    return codePropertyGraph;
  }

  private static TinkerGraph loadCpgFromTinkerpop(String filename, TinkerFormat format) throws IOException {
    final Configuration longIdManagerConfig = new BaseConfiguration();
    longIdManagerConfig.addProperty(TinkerGraph.GREMLIN_TINKERGRAPH_EDGE_ID_MANAGER, TinkerGraph.DefaultIdManager.LONG.name());
    longIdManagerConfig.addProperty(TinkerGraph.GREMLIN_TINKERGRAPH_VERTEX_ID_MANAGER, TinkerGraph.DefaultIdManager.LONG.name());
    longIdManagerConfig.addProperty(TinkerGraph.GREMLIN_TINKERGRAPH_VERTEX_PROPERTY_ID_MANAGER, TinkerGraph.DefaultIdManager.LONG.name());
    TinkerGraph graph = TinkerGraph.open(
      longIdManagerConfig//,
      // io.shiftleft.codepropertygraph.generated.nodes.Factories$.MODULE$.AllAsJava(),
      // io.shiftleft.codepropertygraph.generated.edges.Factories$.MODULE$.AllAsJava()
    ); 
    if (format.equals(TinkerFormat.GRYO)) {
      graph.io(IoCore.gryo()).readGraph(filename);
    } else {
      graph.io(IoCore.graphml()).readGraph(filename);
    }
    return graph;
  }

}
