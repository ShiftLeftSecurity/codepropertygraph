import unittest
from cpgclient import CpgClient

SERVER = "127.0.0.1"
PORT = 8080

class TestStringMethods(unittest.TestCase):

    def setUp(self):
        pass

    def testShouldFailForCreateCpgUnUnknownFile(self):
        """
        Ensure that we can create a CPG
        via a call to the server
        """
        filename = "filethatdoesnotexist"
        client = CpgClient.CpgClient(SERVER, PORT)
        self.assertRaises(Exception, client.createCpg, filename)

    def tearDown(self):
        pass

if __name__ == '__main__':
    unittest.main()
