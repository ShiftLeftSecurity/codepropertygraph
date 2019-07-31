#!/usr/bin/env sh

export CPG_ROOT_DIR=`pwd`

# https://stackoverflow.com/a/28085062
: ${DEFAULT_JAVA2CPG_ROOT:=`readlink -f ../java2cpg`}
# : ${DEFAULT_JAVA2CPG_ROOT:=${readlink -f "../java2cpg"}}
echo "java2cpg root directory [$DEFAULT_JAVA2CPG_ROOT]:"
read INSTALL_DIR
if [ -z "$JAVA2CPG_ROOT" ]; then
  JAVA2CPG_ROOT=$DEFAULT_JAVA2CPG_ROOT
fi

cd $JAVA2CPG_ROOT
sbt stage

for jar in `find $CPG_ROOT_DIR/resources/cpgs -name '*.jar'`; do
  OUT_DIR=`dirname $jar`
  ./java2cpg.sh $jar -o $OUT_DIR/cpg.bin.zip
done;
