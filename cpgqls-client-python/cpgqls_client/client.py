import requests
import websockets
import asyncio

### TODO
#### possible configuration
# 0 retries
# set the timeout for the HTTP thing
# set the timeout for the websocket thing

class CPGQLSClient(object):
    HTTP_ENDPOINT_PREFIX = "http://"
    WS_ENDPOINT_PREFIX = "ws://"

    WS_MSG_CONNECTED = "connected"

    def __init__(self, server_endpoint):
        if server_endpoint is None:
            raise ValueError("The server endpoint cannot be None")
        if not isinstance(server_endpoint, str):
            raise ValueError("The server_endpoint parameter has to be a string")
        self._server_endpoint = server_endpoint

        self._http_endpoint = self.HTTP_ENDPOINT_PREFIX + server_endpoint
        self._ws_connect_endpoint = self.WS_ENDPOINT_PREFIX + server_endpoint + "/connect"
        self._http_post_query_endpoint = self.HTTP_ENDPOINT_PREFIX + server_endpoint + "/query"
        self._http_get_result_endpoint = self.HTTP_ENDPOINT_PREFIX + server_endpoint + "/result"

    def execute(self, query):
        return asyncio.get_event_loop().run_until_complete(self._send_query(query))

    async def _send_query(self, query):
        async with websockets.connect(self._ws_connect_endpoint) as ws_conn:
            connected_msg = await ws_conn.recv()
            if connected_msg != self.WS_MSG_CONNECTED:
                raise Exception("Received unexpected first message on websocket endpoint")
            post_query_res = requests.post(self._http_post_query_endpoint, json = {"query": query})
            if post_query_res.status_code != 200:
                raise Exception("Could not post query to the HTTP endpoint of the cpgserver")
            last_msg = await ws_conn.recv()
            get_query_res = requests.get(self._http_get_result_endpoint + "/" + post_query_res.json()["uuid"])
            if get_query_res.status_code != 200:
                raise Exception("Could not retrieve query result via the HTTP endpoint of the cpgserver")
            return get_query_res.json()

