import requests
import time
import pprint
import json

DEFAULT_PORT = 8080

class CpgClient:
    """
    The CPG client provides a Python interface to the CPG server, allowing
    you, hopefully, to ignore the fact that there is client/server
    communication happening in the background. Instead, you can simply
    issue queries and work with the responses.
    """

    def __init__(self, server, port):
        self.server = server
        self.port = port
        self.handlerAndUrl = "http://{}:{}".format(server, port)


    # TODO: it would be nice if the server responded with an id
    # that can be used to refer to the CPG in the future.

    def createCpg(self, filename):
        """
            Create a code property graph for the given filename
            and optionally block until it is ready.
        """
        postBody = {"filenames" : [filename]}
        response = self._request("create", postBody)
        self._waitUntilCpgIsCreated()

    def _request(self, operation, body, isGet = False):
        url = "{}/{}".format(self.handlerAndUrl, operation)
        body = json.dumps(body)
        response = requests.post(url, data = body)
        if not response.status_code in [200, 202]:
            raise Exception("Invalid request: " + str(response))
        return response

    # TODO: if we do allow multiple CPGs to be loaded, this method
    # will need to receive an optional Id.
    def isCpgLoaded(self):
        """
        Check if the CPG is ready for querying
        """
        response = requests.get("{}/status".format(self.handlerAndUrl))
        jsonBody = response.json()
        try:
            loaded = jsonBody["isCpgLoaded"]
        except KeyError:
            return False

        return loaded

    def _waitUntilCpgIsCreated(self):
        while not self.isCpgLoaded():
            time.sleep(.001)

    def query(self, q):
        """
        Run query `q` on the CPG and return result
        """
        postBody = {"query" : q}
        response = self._request("query", postBody)
        return self._pollForQueryResult()

    def _pollForQueryResult(self):
        while True:
            response = requests.get("{}/queryresult".format(self.handlerAndUrl))
            jsonBody = response.json()
            if jsonBody["isQueryCompleted"]:
                return jsonBody["response"]
            time.sleep(.001)
