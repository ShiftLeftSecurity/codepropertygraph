#!/usr/bin/env sh

export CPG_HOME=`pwd`

# https://stackoverflow.com/a/28085062
: ${DEFAULT_JAVA2CPG_HOME:=`readlink -f ../java2cpg`}
echo "java2cpg root directory [$DEFAULT_JAVA2CPG_HOME]:"
read INSTALL_DIR
if [ -z "$JAVA2CPG_HOME" ]; then
  JAVA2CPG_HOME=$DEFAULT_JAVA2CPG_HOME
fi

cd $JAVA2CPG_HOME
sbt stage

for jar in `find $CPG_HOME/resources/cpgs -name '*.jar'`; do
  OUT_DIR=`dirname $jar`
  ./java2cpg.sh $jar -o $OUT_DIR/cpg.bin.zip
done;
