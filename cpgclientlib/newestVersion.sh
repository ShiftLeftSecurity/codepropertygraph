#!/usr/bin/env bash

REPO=https://github.com/ShiftLeftSecurity/codepropertygraph
HIGHEST_TAG=`git ls-remote --tags $REPO | awk -F"/" '{print $3}' | grep '^v[0-9]*\.[0-9]*\.[0-9]*' | grep -v {} | sort --version-sort | tail -n1`
echo $HIGHEST_TAG
