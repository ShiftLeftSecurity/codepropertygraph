package io.shiftleft.queryprimitives;

import io.shiftleft.queryprimitives.cpgloading.proto.ProtoCpgLoader;
import io.shiftleft.queryprimitives.cpgloading.tinker.TinkerCpgLoader;
import io.shiftleft.queryprimitives.cpgloading.tinker.TinkerFormat;
import io.shiftleft.queryprimitives.steps.starters.Cpg;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class CpgLoader {

  /**
   * Load a Code Property Graph
   * @param filename name of file that stores the code property graph
   * @param format input format of the CPG
   */
  public static Cpg loadCodePropertyGraph(String filename, CpgInputFormat format) {
    Cpg cpg;

    switch (format) {
      case PROTOZIP:
        cpg = ProtoCpgLoader.getInstance().loadFromProtoZip(filename);
        break;
      case GRYO:
        cpg = TinkerCpgLoader.loadFromTinkerFormat(filename, TinkerFormat.GRYO);
        break;
      case TINKERPOP:
        cpg = TinkerCpgLoader.loadFromTinkerFormat(filename, TinkerFormat.GRAPHML);
        break;
      case GRAPHML:
        cpg = TinkerCpgLoader.loadFromTinkerFormat(filename, TinkerFormat.GRAPHML);
        break;
      default:
        // Match is exhaustive, but javac still complains
        cpg = null;
        break;
    }

    return cpg;
  }

}
