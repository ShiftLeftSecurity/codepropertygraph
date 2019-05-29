#!/bin/bash

# publish codepropertygraph-custom 0.0.0-SNAPSHOT to your maven2 local, using public codepropertygraph (master)
# if the script is called with the `--csharp` flag, it'll also create a cpg-proto-bindings.nupkg locally in the repo

CODEPROPERTYGRAPH_ROOT=../../

# stop on errors
set -e
set -o pipefail

BASE_DIR=`pwd`
CSHARP_FLAG=$1

# checkout the latest released (tagged) version of codepropertygraph (public)
rm -rf build
mkdir build
rsync -av --exclude 'samples' $CODEPROPERTYGRAPH_ROOT build/codepropertygraph
cd build/codepropertygraph

# add our schema extensions
CPG_SCHEMA_DIR=codepropertygraph/src/main/resources/schemas
for file in `ls ../../schemas`; do
  if [ -f ${CPG_SCHEMA_DIR}/$file ]; then
    echo "file $file already exists! exiting."
    exit 1
  fi
  cp -v ../../schemas/$file $CPG_SCHEMA_DIR
done

# change name to codepropertygraph-custom
# can't use `sed -i` because of macOS :(
sed 's/name := "\(.*\)"/name := "\1-custom"/' codepropertygraph/build.sbt > codepropertygraph/build.sbt.tmp
mv codepropertygraph/build.sbt.tmp codepropertygraph/build.sbt
sed 's/name := "\(.*\)"/name := "\1-custom"/' proto-bindings/build.sbt > proto-bindings/build.sbt.tmp
mv proto-bindings/build.sbt.tmp proto-bindings/build.sbt

# publish to m2 local repo
sbt 'set ThisBuild/version := "999.0.0-SNAPSHOT"' clean generateGoBindings publishM2

if [ "$CSHARP_FLAG" = '--csharp' ]; then
  echo "building csharp proto bindings"
  sbt -Ddotnet-version=999.0.0 generateCsharpBindings
fi

cd $BASE_DIR
echo "all done. get back to work!"
