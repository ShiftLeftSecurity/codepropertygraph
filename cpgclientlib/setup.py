import setuptools
import subprocess
import os, os.path

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))
NEWEST_VERSION_SCRIPT = os.path.join(SCRIPT_DIR, "newestVersion.sh")

def getCurrentVersion():
    output = str(subprocess.check_output(['./newestVersion.sh']))
    return output.replace("'", "").replace("\\n", "").replace("v", "").replace("b", "")

with open("README.md", "r") as fh:
    long_description = fh.read()
    
    setuptools.setup(
        name='cpgclientlib',
        version=getCurrentVersion(),
        scripts=[] ,
        author="Fabian Yamaguchi",
        author_email="fabs@shiftleft.io",
        description="A client library for CPG servers",
        long_description=long_description,
        long_description_content_type="text/markdown",

        url="https://github.com/ShiftLeftSecurity/codepropertygraph",
        packages=setuptools.find_packages(),

        classifiers=[
            "Programming Language :: Python :: 3",
            "License :: OSI Approved :: Apache 2 License",
            "Operating System :: OS Independent",
        ],
)
