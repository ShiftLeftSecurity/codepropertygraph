import setuptools
import os
import os.path

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))


def get_version():
    print(SCRIPT_DIR)
    version_file = os.path.join(SCRIPT_DIR, "cpg-version")
    print(version_file)
    if not os.path.exists(version_file):
        set_version_filename = str(os.path.join(SCRIPT_DIR, "setVersion"))
        print(set_version_filename)
        os.system(set_version_filename)
    output = open(version_file).readline()
    version = output.replace("'", "").replace("\\n", "").replace("v", "").replace("b", "").replace("\n", "")
    print(version)
    return version


dependencies=["requests"]

with open("README.md", "r") as fh:
    long_description = fh.read()

    setuptools.setup(
        name='cpgclientlib',
        version=get_version(),
        scripts=['cpg-version', 'cpg-create', 'cpg-query'],
        author="Fabian Yamaguchi",
        author_email="fabs@shiftleft.io",
        description="A client library for CPG servers",
        long_description=long_description,
        long_description_content_type="text/markdown",

        url="https://github.com/ShiftLeftSecurity/codepropertygraph",
        packages=setuptools.find_packages(),
        install_requires=dependencies,
        classifiers=[
            "Programming Language :: Python :: 3",
            "License :: OSI Approved :: Apache Software License",
            "Operating System :: OS Independent",
        ],
    )
