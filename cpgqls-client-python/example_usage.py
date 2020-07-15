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
query = import_code_query("/home/user/code/x42/c", "my-project")
result = client.execute(query)
print(query)


