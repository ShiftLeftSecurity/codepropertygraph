import setuptools
import os, os.path

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))

def getCurrentVersion():
    output = open(os.path.join(SCRIPT_DIR,"version")).readline()
    return output.replace("'", "").replace("\\n", "").replace("v", "").replace("b", "")

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
