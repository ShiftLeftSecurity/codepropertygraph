import os
from setuptools import setup, find_packages

here = os.path.abspath(os.path.dirname(__file__))
os.chdir(here)


with open("README.md", "r") as fh:
    long_description = fh.read()

setup(
    name="cpgqls-client",
    version="0.0.1",
    author="ShiftLeft Inc.",
    author_email="support@shiftleft.io",
    description="A client library for CPGQL servers",
    long_description=long_description,
    long_description_content_type="text/markdown",

    url="https://github.com/ShiftLeftSecurity/codepropertygraph",
    install_requires=[
        "requests>=2.0.0",
        "websockets>=8.1",
    ],
    packages=find_packages(exclude=["tests", "tests.*"]),
    classifiers=[
        "Programming Language :: Python :: 3.7",
        "Programming Language :: Python :: 3.8",
        "License :: OSI Approved :: Apache Software License",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.7',
)
