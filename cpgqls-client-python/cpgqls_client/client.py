import asyncio
import requests
import websockets

class CPGQLSTransport(object):

    def __init__(self):
        self._ws_conn = None

    def connect(self, endpoint):
        self._ws_conn = websockets.connect(endpoint)
        return self._ws_conn

    async def recv(self):
        await self._ws_conn.recv()

    def post(self, uri, **kwargs):
        return requests.post(uri, **kwargs)

    def get(self, uri):
        return requests.get(uri)



class CPGQLSClient(object):
    HTTP_ENDPOINT_PREFIX = "http://"
    WS_ENDPOINT_PREFIX = "ws://"

    CPGQLS_MSG_CONNECTED = "connected"

    def __init__(self, server_endpoint, event_loop = None, transport = None):
        if server_endpoint is None:
            raise ValueError("The server endpoint cannot be None")
        if not isinstance(server_endpoint, str):
            raise ValueError("The server_endpoint parameter has to be a string")

        self._loop = asyncio.get_event_loop() if event_loop is None else event_loop
        self._transport = CPGQLSTransport() if transport is None else transport

        self._server_endpoint = server_endpoint

        self._http_endpoint = self.HTTP_ENDPOINT_PREFIX + server_endpoint
        self._ws_connect_endpoint = self.WS_ENDPOINT_PREFIX + server_endpoint + "/connect"
        self._http_post_query_endpoint = self.HTTP_ENDPOINT_PREFIX + server_endpoint + "/query"
        self._http_get_result_endpoint = self.HTTP_ENDPOINT_PREFIX + server_endpoint + "/result"

    def execute(self, query):
        return self._loop.run_until_complete(self._send_query(query))

    async def _send_query(self, query):
        async with self._transport.connect(self._ws_connect_endpoint) as ws_conn:
            connected_msg = await ws_conn.recv()
            if connected_msg != self.CPGQLS_MSG_CONNECTED:
                raise Exception("Received unexpected first message on websocket endpoint")
            post_query_res = self._transport.post(self._http_post_query_endpoint, json = {"query": query})
            if post_query_res.status_code != 200:
                raise Exception("Could not post query to the HTTP endpoint of the cpgserver")
            last_msg = await ws_conn.recv()
            get_query_res = self._transport.get(self._http_get_result_endpoint + "/" + post_query_res.json()["uuid"])
            if get_query_res.status_code != 200:
                raise Exception("Could not retrieve query result via the HTTP endpoint of the cpgserver")
            return get_query_res.json()

