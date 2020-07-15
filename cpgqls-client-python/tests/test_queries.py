from cpgqls_client import import_code_query
from cpgqls_client import workspace_query

import pytest


def test_workspace_query():
    out = workspace_query()
    assert len(out) > 0


def test_import_code_query():
    with pytest.raises(Exception):
        import_code_query(None)

    out = import_code_query("/unix/path")
    assert len(out) > 0

    out = import_code_query("C:\\windows\\path")
    assert len(out) > 0

    out = import_code_query("/unix/path", "my-project")
    assert len(out) > 0

    out = import_code_query("/unix/path", "my-project", "c")
    assert len(out) > 0

