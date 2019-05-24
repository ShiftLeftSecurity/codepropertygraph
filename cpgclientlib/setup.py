import setuptools
import os, os.path

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))

def getCurrentVersion():
    print(SCRIPT_DIR)
    versionFile = os.path.join(SCRIPT_DIR,"version")
    print(versionFile)
    if not os.path.exists(versionFile):
        setVersionFilename = str(os.path.join(SCRIPT_DIR, "setVersion"))
        print(setVersionFilename)
        os.system(setVersionFilename)
    output = open(versionFile).readline()
    version = output.replace("'", "").replace("\\n", "").replace("v", "").replace("b", "").replace("\n","")
    print(version)
    return version

with open("README.md", "r") as fh:
    long_description = fh.read()
    
    setuptools.setup(
        name='cpgclientlib',
        version=getCurrentVersion(),
        scripts=['version'] ,
        author="Fabian Yamaguchi",
        author_email="fabs@shiftleft.io",
        description="A client library for CPG servers",
        long_description=long_description,
        long_description_content_type="text/markdown",

        url="https://github.com/ShiftLeftSecurity/codepropertygraph",
        packages=setuptools.find_packages(),

        classifiers=[
            "Programming Language :: Python :: 3",
            "License :: OSI Approved :: Apache Software License",
            "Operating System :: OS Independent",
        ],
)
