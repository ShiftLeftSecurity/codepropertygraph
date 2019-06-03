# A Python client library for Joern Server

A library to script code analyzers based on code property graphs
(e.g., Joern/Ocular) via Python.


## Requirements

* Python 3 and Pip(we do _not_ support Python 2.7)
  see (https://packaging.python.org/tutorials/installing-packages/)

## Installation

To download and install the latest version, issue the following command:

```
pip install cpgclientlib
```

## Usage

Assuming that `joernd` is running locally, the following short scripts creates a code property graph and runs a query to retrieve all methods in JSON format.

```python
#!/usr/bin/env python3

from cpgclient.CpgClient import CpgClient

server = '127.0.0.1'
port = 8080
client = CpgClient(server, port)
client.create_cpg('/path/to/cpg')
methods = client.query('cpg.method.toJson')
print(methods)
```

## References

* Code Property Graph specification and tools
  https://github.com/ShiftLeftSecurity/codepropertygraph/
* The open source code analyze joern: https://joern.io
* ShiftLeft Ocular: https://ocular.shiftleft.io
