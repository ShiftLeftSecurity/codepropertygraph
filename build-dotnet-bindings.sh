#!/bin/bash
set -e
set -o pipefail

usage() { echo "Usage: $0 --cpg-version <string> [--publish-to-repo <string>] [--publish-key <string>]" 1>&2; exit 1; }

ARGS=`getopt -o v:r:k: --long cpg-version:,publish-to-repo:,publish-key: -- "$@"`
eval set -- "$ARGS"
while true ; do
    case "$1" in
        -v|--cpg-version)
            CPG_VERSION=$2 ; shift 2 ;;
        -r|--publish-to-repo)
            PUBLISH_TO_REPO=$2 ; shift 2 ;;
        -k|--publish-key)
            PUBLISH_KEY=$2 ; shift 2 ;;
        --) shift ; break ;;
        *) echo "Internal error!" ; usage ;;
    esac
done

if [ -z "$CPG_VERSION" ]; then
    echo "error: cpg version not defined!"
    usage
fi

echo "generating csharp protoc bindings for cpg.proto"
rm -rf target/csharp
mkdir -p target/csharp/io/shiftleft/proto
cp codepropertygraph/target/cpg.proto target/csharp
cp proto-bindings/cpg-proto-bindings.csproj target/csharp
cd target/csharp
protoc --csharp_out=io/shiftleft/proto cpg.proto

echo "packaging as v$CPG_VERSION"
dotnet pack cpg-proto-bindings.csproj -o . /p:Version=$CPG_VERSION
PKG_FILE=cpg-proto-bindings.${CPG_VERSION}.nupkg
echo "generated file: $PKG_FILE"

if [ -n "$PUBLISH_TO_REPO" ] && [ -n "$PUBLISH_KEY" ]; then
    echo "publishing nuget package to $PUBLISH_TO_REPO with provided api key..."
    dotnet nuget push cpg-proto-bindings.${CPG_VERSION}.nupkg --source $PUBLISH_TO_REPO --api-key $PUBLISH_KEY
fi

echo "all done"
