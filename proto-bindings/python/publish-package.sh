#!/usr/bin/env bash

# This script takes a path to a file containing Python Protocol Buffer bindings
# for the Code Property Graph data structure, generates a Python source distribution
# package and uploads it to a PyPi repository.
#
# Example usage:
# $ ./publish-package.sh pypi-local ../../codepropertygraph/target/cpg_pb2.py
#

set -o errexit
set -o pipefail
set -o nounset

# Script starts here

if ! command -v python3 &> /dev/null; then
  echo 'python3 is required to execute this script. Exitting.'
  exit 1
fi

if [ "$#" -ne 2 ]; then
  echo 'Incorrect number of arguments provided.'
  echo 'Usage: ./publish-package.sh PYPI_REPOSITORY_NAME PATH_TO_BINDINGS'
  echo 'Exitting.'
  exit 1
fi

readonly PYPI_REPOSITORY_NAME=$1
readonly PATH_TO_BINDINGS=$2
readonly VENV_NAME=".cpgprotobuf-venv"
readonly PATH_TO_REQUIREMENTS="requirements.txt"
readonly PATH_TO_PACKAGE="./cpgprotobuf/"
readonly PATH_TO_SETUP_PY="./setup.py"

if [ ! -f "${PATH_TO_BINDINGS}" ]; then
  echo "Could not read Python bindings file at ${PATH_TO_BINDINGS}."
  exit 1
fi

cp "${PATH_TO_BINDINGS}" "${PATH_TO_PACKAGE}"
echo 'Bindings file copied to package.'

if [ -d "${VENV_NAME}" ]; then rm -Rf "${VENV_NAME}"; fi
python3 -m venv "${VENV_NAME}"
echo 'Virtual environment created.'

# shellcheck source=/dev/null
source "${VENV_NAME}/bin/activate"
echo 'Virtual environment activated.'

pip install -r "${PATH_TO_REQUIREMENTS}"
echo 'Dependencies installed.'

python "${PATH_TO_SETUP_PY}" sdist upload -r "${PYPI_REPOSITORY_NAME}"
echo 'Source distribution created.'

deactivate
echo 'Virtual environment deactivated.'

rm -Rf "${VENV_NAME}"
echo 'Virtual environment deleted.'

echo 'Done.'

