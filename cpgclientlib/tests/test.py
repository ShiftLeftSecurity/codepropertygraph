import sys
import os.path

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))
sys.path.append(SCRIPT_DIR + "/../")

import unittest
from cpgclient import CpgClient
import json
import subprocess
import time
import requests
import signal
import threading

SERVER = "127.0.0.1"
PORT = 8080

stopEvent = threading.Event()

def doStartServer():
    cmd = str(testServerPath())

    FNULL = open(os.devnull, 'w')
    subprocess.Popen(cmd, stdout = FNULL, stderr=FNULL)
    while True:
        try:
            response = requests.get("http://{}:{}/".format(SERVER, PORT))
        except requests.exceptions.ConnectionError:
            if stopEvent.is_set():
                break
            time.sleep(1)
            continue
        break

def testServerPath():
    scriptDir = os.path.dirname(os.path.realpath(__file__))
    relpath = os.path.join(scriptDir, "..", "..","testserver.sh")
    if not os.path.isfile(relpath):
        raise Exception("testserver not found")
    return os.path.abspath(relpath)

class TestStringMethods(unittest.TestCase):

    @classmethod
    def setUpClass(cls):

        startServerThread = threading.Thread(target=doStartServer)
        startServerThread.start()
        startServerThread.join(timeout=10)
        stopEvent.set()
        cls.client = CpgClient.CpgClient(SERVER, PORT)

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
    
