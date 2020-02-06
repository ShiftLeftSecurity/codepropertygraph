#!/usr/bin/env sh

cpg2overflowdb/target/universal/stage/bin/cpg2overflowdb -Dlog4j.configurationFile=codepropertygraph/src/main/resources/log4j2.xml -J-XX:+HeapDumpOnOutOfMemoryError -J-XX:+UseG1GC -J-Xmx2700M -J-XX:AutoBoxCacheMax=8048 $@

