import requests
import time
import json

DEFAULT_SERVER = '127.0.0.1'
DEFAULT_PORT = 8080


class CpgClient:
    """
    The CPG client provides a Python interface to the CPG server, allowing
    you, hopefully, to ignore the fact that there is client/server
    communication happening in the background. Instead, you can simply
    issue queries and work with the responses.
    """

    # TODO: Support for multiple CPGs & queries
    currentCpgId = None
    currentQueryId = None

    def __init__(self, server=DEFAULT_SERVER, port=DEFAULT_PORT):
        self.server = server
        self.port = port
        self.handlerAndUrl = "http://{}:{}".format(server, port)

    def create_cpg(self, filename):
        """
            Create a code property graph for the given filename
            and optionally block until it is ready.
        """
        post_body = {"files": [filename]}
        response = self._request("v1/create", post_body)
        self.currentCpgId = response.json()["uuid"]
        self._wait_until_cpg_is_created()

    def _request(self, operation, body):
        url = "{}/{}".format(self.handlerAndUrl, operation)
        body = json.dumps(body)
        response = requests.post(url, data=body)
        if response.status_code not in [200, 201, 202]:
            raise Exception("Invalid request: " + str(response))
        return response

    # TODO: if we do allow multiple CPGs to be loaded, this method
    # will need to receive an optional Id.
    def is_cpg_loaded(self):
        """
        Check if the CPG is ready for querying
        """
        if self.currentCpgId is None:
            return False
        response = requests.get("{}/v1/cpg/{}".format(self.handlerAndUrl, self.currentCpgId))
        json_body = response.json()
        try:
            loaded = json_body["ready"]
        except KeyError:
            return False

        return loaded

    def _wait_until_cpg_is_created(self):
        while not self.is_cpg_loaded():
            time.sleep(.001)

    def query(self, q):
        """
        Run query `q` on the CPG and return result
        """
        if self.currentCpgId is None:
            return None
        post_body = {"query": q}
        response = self._request("v1/cpg/{}/query".format(self.currentCpgId), post_body)
        self.currentQueryId = response.json()["uuid"]
        return self._poll_for_query_result()

    def _poll_for_query_result(self):
        while True:
            response = requests.get("{}/v1/query/{}".format(self.handlerAndUrl, self.currentQueryId))
            json_body = response.json()
            if json_body["ready"]:
                return json_body["result"] or json_body["error"]
            time.sleep(.001)
