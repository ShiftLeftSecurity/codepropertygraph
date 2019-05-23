#!/bin/bash

SCRIPT_ABS_PATH=$(readlink -f "$0")
SCRIPT_ABS_DIR=$(dirname $SCRIPT_ABS_PATH)
cd $SCRIPT_ABS_DIR
./setVersion
python3 setup.py sdist
# python3 -m twine upload -u fabsx00 -p $PYPI_PASSWORD dist/*
