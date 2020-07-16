from cpgqls_client import CPGQLSClient

import asyncio
import pytest
from unittest.mock import Mock


class MockCPGQLTransportConnection(object):
    def __init__(self, first_recv_msg, second_recv_msg):
        self._num_recv_msgs = 0
        self._first_recv_msg = first_recv_msg
        self._second_recv_msg = second_recv_msg

    def __await__(self):
        return self
        yield None # pylint: disable=unreachable

    async def recv(self):
        await asyncio.sleep(0)

        lock = asyncio.Lock()
        async with lock:
            msg = None
            if self._num_recv_msgs == 0:
                msg = self._first_recv_msg
            elif self._num_recv_msgs == 1:
                msg = self._second_recv_msg
            self._num_recv_msgs += 1
        return msg

    async def __aenter__(self):
        return await self

    async def __aexit__(self, exc_type, exc, tb):
        pass


class MockCPGQLSTransport(object):
    def __init__(self, conn, get_response, post_response):
        self._conn = conn
        self._get_response = get_response
        self._post_response = post_response

    def connect(self, *args, **kwargs):
        return self._conn

    def post(self, *args, **kwargs):
        return self._post_response

    def get(self, *args, **kwargs):
        return self._get_response


def test_basic_execution():
    event_loop = asyncio.new_event_loop()
    conn = MockCPGQLTransportConnection("connected", "received")
    get_args = {'json.return_value':  {'uuid': 'one'}}
    get_response_mock = Mock(status_code=200, **get_args)
    post_args = {'json.return_value':  {'uuid': 'one'}}
    post_response_mock = Mock(status_code=200, **post_args)
    mt = MockCPGQLSTransport(conn, get_response_mock, post_response_mock)
    endpoint = "localhost:8080"
    client = CPGQLSClient(endpoint, event_loop=event_loop, transport=mt)
    res = client.execute("val a = 1")
    assert res == post_response_mock.json()


def test_get_response_not_200():
    event_loop = asyncio.new_event_loop()
    conn = MockCPGQLTransportConnection("connected", "received")
    get_args = {'json.return_value':  {'uuid': 'one'}}
    get_response_mock = Mock(status_code=400, **get_args)
    post_args = {'json.return_value':  {'uuid': 'one'}}
    post_response_mock = Mock(status_code=200, **post_args)
    mt = MockCPGQLSTransport(conn, get_response_mock, post_response_mock)
    endpoint = "localhost:8080"
    client = CPGQLSClient(endpoint, event_loop=event_loop, transport=mt)
    with pytest.raises(Exception):
        client.execute("val a = 1")
