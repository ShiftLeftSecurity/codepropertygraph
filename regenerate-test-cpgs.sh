#!/usr/bin/env sh

# stop on errors
set -e
set -o pipefail

CPG_HOME=`pwd`
TESTCODE_ROOT="$CPG_HOME/resources/testcode"
SRC_ROOT="$TESTCODE_ROOT/sources"
JARS_ROOT="$TESTCODE_ROOT/jars"
PREGENERATED_JARS_ROOT="$TESTCODE_ROOT/jars-pregenerated"
CPGS_ROOT="$TESTCODE_ROOT/cpgs"

DEFAULT_JAVA2CPG_HOME=`readlink -f ../java2cpg`
if [ -z "$JAVA2CPG_HOME" ]; then
  JAVA2CPG_HOME=$DEFAULT_JAVA2CPG_HOME
fi
echo "java2cpg home directory: $JAVA2CPG_HOME"

# start fresh
find "$SRC_ROOT" -name '*.class' -exec rm -rf {} \;
rm -rf "$JARS_ROOT"
cp -rp "$PREGENERATED_JARS_ROOT" "$JARS_ROOT"
rm -rf "$CPGS_ROOT"
mkdir -p "$CPGS_ROOT"

# create jars 
cd "$SRC_ROOT"
for TESTCASE in `ls`; do
  cd "$SRC_ROOT/$TESTCASE"
  echo "creating jar for `pwd`"
  javac -g *
  jar -cvf "$JARS_ROOT/$TESTCASE.jar" .
done;

cd $JAVA2CPG_HOME
sbt stage

for jar in `find "$JARS_ROOT" -name '*.jar'`; do
  TESTCASE=`basename "$jar" | sed s/\.jar//`
  mkdir -p "$CPGS_ROOT/$TESTCASE"
  ./java2cpg.sh "$jar" -o "$CPGS_ROOT/$TESTCASE/cpg.bin.zip"
done;

# cleanup
find "$SRC_ROOT" -name '*.class' -exec rm -rf {} \;
rm -rf "$JARS_ROOT"
