import os
from setuptools import setup, find_packages

here = os.path.abspath(os.path.dirname(__file__))
os.chdir(here)


with open("PACKAGE-README.md", "r") as fh:
    long_description = fh.read()

setup(
    name="cpgprotobuf",
    version="0.0.1",
    author="ShiftLeft Inc.",
    author_email="support@shiftleft.io",
    description="Protocol Buffer v2 bindings for the Code Property Graph data structure",
    long_description=long_description,
    long_description_content_type="text/markdown",

    url="https://github.com/ShiftLeftSecurity/codepropertygraph",
    install_requires=[
        "protobuf>=3.13.0",
    ],
    packages=find_packages(where='.'),
    classifiers=[
        "Programming Language :: Python :: 3.5",
        "Programming Language :: Python :: 3.6",
        "Programming Language :: Python :: 3.7",
        "Programming Language :: Python :: 3.8",
        "License :: OSI Approved :: Apache Software License",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.5',
)
