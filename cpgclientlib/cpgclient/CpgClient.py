import requests
from pprint import pprint

DEFAULT_PORT = 8080

class CpgClient:

    def __init__(self, server, port):
        self.server = server
        self.port = port
        self.handlerAndUrl = "http://{}:{}".format(server, port)

    def createCpg(self, filename):
        postBody = {"filenames" : [filename]}
        response = requests.post("{}/create".format(self.handlerAndUrl), postBody)
        if response.status_code == 400:
            raise Exception("Invalid request: {}".format(response._content))
        pprint(vars(response))

def query(q):
    print(q)

