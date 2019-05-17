import requests
import time
import pprint

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
        response = requests.post("{}/create".format(self.handlerAndUrl), json = postBody)
        if response.status_code == 202:
            self._waitUntilCpgIsCreated()
        else:
            raise Exception("Invalid request: {}".format(response._content))

    # TODO: if we do allow multiple CPGs to be loaded, this method
    # will need to receive an optional Id.
    def isCpgLoaded(self):
        """
        Check if the CPG is ready for querying
        """
        response = requests.get("{}/status".format(self.handlerAndUrl))
        jsonBody = response.json()
        try:
            isCpgLoaded = jsonBody["isCpgLoaded"]
        except KeyError:
            return False

        return isCpgLoaded

    def _waitUntilCpgIsCreated(self):
        while not self.isCpgLoaded():
            time.sleep(100)
