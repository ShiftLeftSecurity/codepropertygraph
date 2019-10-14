#!/usr/bin/env sh

SCRIPT_ABS_PATH=$(readlink -f "$0")
SCRIPT_ABS_DIR=$(dirname $SCRIPT_ABS_PATH)
CPG_FILE=$(readlink -f $@)

cd $SCRIPT_ABS_DIR
$SCRIPT_ABS_DIR/cpgvalidator/target/universal/stage/bin/cpgvalidator $CPG_FILE

