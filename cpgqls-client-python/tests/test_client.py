from cpgqls_client import CPGQLSClient

import pytest

def test_initializer():
    with pytest.raises(ValueError):
        CPGQLSClient(None)

    with pytest.raises(ValueError):
        CPGQLSClient(1)

    out = CPGQLSClient("localhost:8000")
    assert out is not None

