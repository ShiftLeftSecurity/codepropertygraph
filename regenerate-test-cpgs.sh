#!/usr/bin/env sh

export CPG_HOME=`pwd`

DEFAULT_JAVA2CPG_HOME=`readlink -f ../java2cpg`
if [ -z "$JAVA2CPG_HOME" ]; then
  JAVA2CPG_HOME=$DEFAULT_JAVA2CPG_HOME
fi

echo "java2cpg home directory: $JAVA2CPG_HOME"

cd $JAVA2CPG_HOME
sbt stage

for jar in `find $CPG_HOME/resources/cpgs -name '*.jar'`; do
  OUT_DIR=`dirname $jar`
  ./java2cpg.sh $jar -o $OUT_DIR/cpg.bin.zip
done;
