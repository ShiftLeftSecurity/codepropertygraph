import setuptools
import subprocess
import os, os.path

with open("README.md", "r") as fh:
    long_description = fh.read()
    
    setuptools.setup(
        name='cpgclientlib',
        version='0.1',
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
