from cpgqls_client.client import CPGQLSClient

#### possible configuration
# 0 retries
# set the timeout for the HTTP thing
# set the timeout for the websocket thing

# ImportCodeQuery
# OpenProjectQuery
# RunProfileQuery

# the client connects on the first call of `execute`
server_endpoint = "localhost:8080"
client = CPGQLSClient(server_endpoint)

query = "val a = 1"
result = client.execute(query)
print(result)

