package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Languages {


public static final String JAVA = "JAVA";


public static final String JAVASCRIPT = "JAVASCRIPT";


public static final String GOLANG = "GOLANG";


public static final String CSHARP = "CSHARP";


public static final String C = "C";


public static final String PYTHON = "PYTHON";


public static final String LLVM = "LLVM";


public static final String PHP = "PHP";


public static final String FUZZY_TEST_LANG = "FUZZY_TEST_LANG";

/** generic reverse engineering framework */
public static final String GHIDRA = "GHIDRA";


public static final String KOTLIN = "KOTLIN";

/** Eclipse CDT based parser for C/C++ */
public static final String NEWC = "NEWC";

/** Source-based front-end for Java */
public static final String JAVASRC = "JAVASRC";

/** Source-based front-end for Python */
public static final String PYTHONSRC = "PYTHONSRC";

/** Source-based JS frontend based on Babel */
public static final String JSSRC = "JSSRC";

/** Solidity language frontend */
public static final String SOLIDITY = "SOLIDITY";

/** Source-based frontend for Ruby */
public static final String RUBYSRC = "RUBYSRC";


public static Set<String> ALL = new HashSet<String>() {{
add(JAVA);
add(JAVASCRIPT);
add(GOLANG);
add(CSHARP);
add(C);
add(PYTHON);
add(LLVM);
add(PHP);
add(FUZZY_TEST_LANG);
add(GHIDRA);
add(KOTLIN);
add(NEWC);
add(JAVASRC);
add(PYTHONSRC);
add(JSSRC);
add(SOLIDITY);
add(RUBYSRC);
}};

}