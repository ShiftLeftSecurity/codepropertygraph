import unittest
import os.path
from cpgclient import CpgClient
import pprint
import json
import subprocess
import time
import requests

SERVER = "127.0.0.1"
PORT = 8080

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))

class TestStringMethods(unittest.TestCase):

    @classmethod
    def setUpClass(self):
        cmd = str(self._testServerPath())
        subprocess.Popen(cmd, stdout = subprocess.DEVNULL, stderr=subprocess.DEVNULL)

        # wait until server is available
        while True:
            try:
                requests.get("http://{}:{}/".format(SERVER, PORT))
            except requests.exceptions.ConnectionError:
                time.sleep(0.1)
                continue
            break

        self.client = CpgClient.CpgClient(SERVER, PORT)

    def _testServerPath():
        scriptDir = os.path.dirname(os.path.realpath(__file__))
        relpath = os.path.join(scriptDir, "..", "..","testserver.sh")
        return os.path.abspath(relpath)

    def testShouldRaiseForCreateCpgOnNonExistingFile(self):
        """
        Ensure that `createCpg` raises an exception
        if the filename is not that of an existing file
        """
        filename = "filethatdoesnotexist"
        self.assertRaises(Exception, self.client.createCpg, filename)

    def testShouldSucceedForCreateCpgOnKnownFile(self):
        """
        Ensure that `createCpg` returns without exception
        if filename is that of a known file.
        """
        filename = os.path.join(SCRIPT_DIR, "testcode")
        self.client.createCpg(filename)
        self.assertEqual(True, self.client.isCpgLoaded())

    def testSimpleQuery(self):
        filename = os.path.join(SCRIPT_DIR, "testcode")
        self.client.createCpg(filename)
        response = self.client.query("cpg.method.toJson")
        jsonResponse = json.loads(response)
        self.assertEqual("main", jsonResponse[0]["NAME"])

    @classmethod
    def tearDownClass(self):
        os.system("pkill -f cpg-server")

if __name__ == '__main__':
    unittest.main()
