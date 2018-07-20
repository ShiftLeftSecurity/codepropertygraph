#!/bin/bash
set -e
set -o pipefail

usage() { echo "Usage: $0 --cpg-version <string> [--publish-with-key <string>]" 1>&2; exit 1; }

ARGS=`getopt -o v:k: --long cpg-version:,publish-with-key: -- "$@"`
eval set -- "$ARGS"
while true ; do
    case "$1" in
        -v|--cpg-version)
            CPG_VERSION=$2 ; shift 2 ;;
        -k|--publish-with-key)
            PUBLISH_WITH_KEY=$2 ; shift 2 ;;
        --) shift ; break ;;
        *) echo "Internal error!" ; usage ;;
    esac
done

echo "generating csharp protoc bindings for cpg.proto"
rm -rf target/csharp
mkdir -p target/csharp/io/shiftleft/proto
cp ../codepropertygraph/target/classes/cpg.proto target/csharp
cp cpg-proto-bindings.csproj target/csharp
cd target/csharp
protoc --csharp_out=io/shiftleft/proto cpg.proto

echo "packaging as v$CPG_VERSION"
dotnet pack cpg-proto-bindings.csproj -o . /p:Version=$CPG_VERSION
PKG_FILE=cpg-proto-bindings.${CPG_VERSION}.nupkg
echo "generated file: $PKG_FILE"

if [ -n "$PUBLISH_WITH_KEY" ]; then
    echo "publishing nuget package to jfrog with provided api key"
    # $PUBLISH_WITH_KEY
fi

# echo "all done"
