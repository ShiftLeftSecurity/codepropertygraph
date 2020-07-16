## cpgqls-client

`cpgqls-client` is a simple Python library for communicating with an instace of
a Code Property Graph server.

### Requirements

Python 3.7+

### Installation

```
pip install clpgqls-client
```

### Example usage

Assuming that an instance of a Code Property Graph server is running locally
(e.g. with `joern --server`), the following snipped shows a basic use of the
library:

```python
from cpgqls_client import CPGQLSClient, import_code_query, workspace_query

# the client connects on the first call of `execute`
server_endpoint = "localhost:8080"
client = CPGQLSClient(server_endpoint)

# execute a simple CPGQuery
query = "val a = 1"
result = client.execute(query)
print(result)

# execute a `workspace` CPGQuery
query = workspace_query()
result = client.execute(query)
print(result['out'])

# execute an `importCode` CPGQuery
query = import_code_query("/home/user/code/x42/c", "my-c-project")
result = client.execute(query)
print(result['out'])

query = import_code_query("/home/user/code/x42/java/X42.jar", "my-java-project")
result = client.execute(query)
print(result['out'])
```

## References

* Code Property Graph specification and tools
  https://github.com/ShiftLeftSecurity/codepropertygraph/
* The open source code analyzer joern: https://joern.io
* ShiftLeft Ocular: https://ocular.shiftleft.io

