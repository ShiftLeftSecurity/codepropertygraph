package io.shiftleft.codepropertygraph.cpgloading;

public class Defaults {

  /**
   * Percentage of heap used for caching elements in tinkergraph.
   *
   * Everything that doesn't fit into the cache will be serialized to disk
   * there needs to be sufficient space for everything else.
   * Currently, this value is a good sweet spot
   */
  public static final float DEFAULT_CACHE_HEAP_PERCENTAGE = 30f;

}
