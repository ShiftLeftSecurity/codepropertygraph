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
    CPGQLS_MSG_CONNECTED = "connected"

    def __init__(self, server_endpoint, event_loop=None, transport=None):
        if server_endpoint is None:
            raise ValueError("server_endpoint cannot be None")
        if not isinstance(server_endpoint, str):
            raise ValueError("server_endpoint parameter has to be a string")

        self._loop = asyncio.get_event_loop() if not event_loop else event_loop
        self._transport = CPGQLSTransport() if not transport else transport
        self._endpoint = server_endpoint.rstrip("/")

    def execute(self, query):
        return self._loop.run_until_complete(self._send_query(query))

    async def _send_query(self, query):
        endpoint = self._connect_endpoint()
        async with self._transport.connect(endpoint) as ws_conn:
            connected_msg = await ws_conn.recv()
            if connected_msg != self.CPGQLS_MSG_CONNECTED:
                exception_msg = """Received unexpected first message
                on websocket endpoint"""
                raise Exception(exception_msg)
            endpoint = self._post_query_endpoint()
            post_res = self._transport.post(endpoint, json={"query": query})
            if post_res.status_code != 200:
                exception_msg = """Coould not post query to the HTTP
                endpoint of the server"""
                raise Exception(exception_msg)
            last_msg = await ws_conn.recv()
            endpoint = self._get_result_endpoint(post_res.json()["uuid"])
            get_res = self._transport.get(endpoint)
            if get_res.status_code != 200:
                exception_msg = """Could not retrieve query result via the HTTP endpoint
                of the server"""
                raise Exception(exception_msg)
            return get_res.json()

    def _connect_endpoint(self):
        return "ws://" + self._endpoint + "/connect"

    def _post_query_endpoint(self):
        return "http://" + self._endpoint + "/query"

    def _get_result_endpoint(self, uuid):
        return "http://" + self._endpoint + "/result/" + uuid
