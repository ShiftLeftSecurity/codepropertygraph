## proto-bindings.python

This directory contains all the necessary files for building and distributing a Python package that contains the Python Protocol Buffer bindings for the Code Property Graph data structure.

### Requirements

1. Python `3.5+`
2. On Linux, the `python3-venv` package:

```console
$ sudo apt-get install python3-venv
```

### How to upload a distribution to a private PyPi repository


1. Generate the Python Protocol Buffer bindings for the Code Property Graph following instructions in the root folder README of this repository.

2. Create a PyPi configuration file which points to your package repository:

```console
$ touch ~/.pypirc
$ cat <<EOT >> ~/.pypirc
[distutils]
index-servers =
  local
  pypi

[local]
  repository: https://private-pypi-repo.placehold.er/
  username: user
  password: password
EOT
```

2. Bump the version in `setup.py`.

3. Run the `publish-package.sh` script:

```
./publish-package.sh pypi-local ../../codepropertygraph/target/cpg_pb2.py
```

4. Done.
