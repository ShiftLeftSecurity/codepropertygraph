#!/usr/bin/env sh

# As explained in schema-extender/build.sbt, schema-extender is a separate sbt build inside this
# repository. Both are usually published from travis, but if you want to publish locally and use
# it in joern or ocular, you must also locally publish both. This script does exactly that.

sbt publishM2
cd schema-extender
sbt publishM2
cd ..

