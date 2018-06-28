#!/usr/bin/env python3

import json, os

OUTPUT_DIR = '../../../../target/resource_managed/main/'

def generateProtobuf(cpgDescr):
    protoStr = open('../resources/templates/cpg.proto.tpl').read()
    protoStr = protoStr.replace('$nodeKeys', entryListToProtobufEnumVal(cpgDescr['nodeKeys']))
    protoStr = protoStr.replace('$edgeKeys', entryListToProtobufEnumVal(cpgDescr['edgeKeys']))
    protoStr = protoStr.replace('$nodeTypes', entryListToProtobufEnumVal(cpgDescr['nodeTypes']))
    protoStr = protoStr.replace('$edgeTypes', entryListToProtobufEnumVal(cpgDescr['edgeTypes']))
    protoStr = protoStr.replace('$evaluationStrategies', entryListToProtobufEnumVal(cpgDescr['evaluationStrategies']))
    protoStr = protoStr.replace('$dispatchTypes', entryListToProtobufEnumVal(cpgDescr['dispatchTypes']))
    protoStr = protoStr.replace('$languages', entryListToProtobufEnumVal(cpgDescr['languages']))
    protoStr = protoStr.replace('$frameworks', entryListToProtobufEnumVal(cpgDescr['frameworks']))

    try:
        os.makedirs(OUTPUT_DIR)
    except FileExistsError:
        pass

    writeProto(OUTPUT_DIR + '/cpg.proto', protoStr)

def writeProto(filename, protoStr):
    f = open(filename, 'w')
    f.write(protoStr)
    f.close()


def entryListToProtobufEnumVal(entryList):
    return '\n'.join(['// %s\n%s = %d;' % (x['comment'], x['name'], x['id']) for x in entryList])

if __name__ == '__main__':
    # Change working directory to script directory
    abspath = os.path.abspath(__file__)
    dname = os.path.dirname(abspath)
    os.chdir(dname)

    cpgDescr = json.loads(open('../../../../src/main/resources/cpg.json').read())
    generateProtobuf(cpgDescr)
