#!/usr/bin/env python3

import json, os

# equivalent of `sourceManaged.in(Compile).value` in build.sbt
OUTPUT_DIR = '../../../../target/src_managed/main/java'

def generateJava(cpgDescr):

    try:
        os.makedirs(OUTPUT_DIR)
    except FileExistsError:
        pass

    # transform keys into a dict for lookups
    nodeKeysByName = {}
    for key in cpgDescr['nodeKeys']:
        nodeKeysByName[key["name"]] = key

    template = open('../resources/templates/javaTemplate.java').read()
    writeJavaFile('NodeKeyNames', cpgDescr['nodeKeys'], template, 'String')
    writeJavaFile('EdgeKeyNames', cpgDescr['edgeKeys'], template, 'String')
    writeJavaFile('NodeTypes', cpgDescr['nodeTypes'], template, 'String')
    writeJavaFile('EdgeTypes', cpgDescr['edgeTypes'], template, 'String')
    writeNodeKeys('NodeKeys', cpgDescr['nodeTypes'], cpgDescr['nodeKeys'], template)
    writeJavaFile('EdgeKeys', cpgDescr['edgeKeys'], template, 'Key')
    writeJavaFile('EvaluationStrategies', cpgDescr['evaluationStrategies'], template, 'String')
    writeJavaFile('DispatchTypes', cpgDescr['dispatchTypes'], template, 'String')
    writeJavaFile('Languages', cpgDescr['languages'], template, 'String')
    writeJavaFile('ModifierTypes', cpgDescr['modifierTypes'], template, 'String')
    writeJavaFile('Frameworks', cpgDescr['frameworks'], template, 'String')
    writeJavaFile('Operators', cpgDescr['operatorNames'], template, 'String', 'operator', 'name')
    writeNodeKeyTypesFile('NodeKeyTypes', cpgDescr['nodeKeys'], template)

def writeJavaFile(className, entryList, template, constantType, identifierField='name', valueField='name'):
    lineTemplatePerConstantType = {
        'Key': 'Key<%s> %s = new Key<>(\"%s\");',
        'String': 'String %s = \"%s\";'
    }
    lineTemplate = "/** %s */\npublic static final " + lineTemplatePerConstantType.get(constantType) + "\n"
    contents = template.replace('$ClassName', className) \
        .replace('$Strings', entryListToJavaStrings(
        entryList, lineTemplate, constantType, identifierField, valueField
    ))

    open(OUTPUT_DIR + '/' + className + '.java', 'w').write(contents)

def entryListToJavaStrings(entryList, lineTemplate, constantType, identifierField, valueField):
    if constantType == 'Key':
        lines = [lineTemplate % (x['comment'] if 'comment' in x else '', toJavaType(x['valueType'], x['cardinality']), x[identifierField], x[valueField]) for x in entryList]
    elif constantType == 'String':
        lines = [lineTemplate % (x['comment'] if 'comment' in x else '', x[identifierField], x[valueField]) for x in entryList]
    return "\n".join(lines)

def toJavaType(cpgType, cardinality):
    baseType = {
        'string': 'String',
        'int': 'Integer',
        'boolean' : 'Boolean'
    }.get(cpgType, 'UNKNOWN')
    if cardinality.lower() == 'list':
        return 'java.util.List<%s>' % baseType
    else:
        return baseType

def writeNodeKeyTypesFile(className, nodeKeys, template):
    strings = ''
    for key in nodeKeys:
        strings += "public static final String %s = \"%s\";\n" % (key['name'], key['valueType'])

    contents = template.replace('$ClassName', className) \
                       .replace('$Strings', strings)
    open(OUTPUT_DIR + '/' + className + '.java', 'w').write(contents)

def writeNodeKeys(className, types, nodeKeys, template):
    nodeKeysByName = {}
    for key in cpgDescr['nodeKeys']:
        nodeKeysByName[key['name']] = "/** %s */\npublic static final Key<%s> %s = new Key<>(\"%s\");\n\n" \
                                      % (key['comment'], toJavaType(key['valueType'], key['cardinality']), key['name'], key['name'])

    contents = ''.join(sorted(nodeKeysByName.values()))
    for tpe in types:
        typeContent = "/** " + tpe["comment"] + " */\n" + "public static class " + tpe["name"] + " { \n$Defs}\n\n"
        lines = ""
        for typeKeyName in tpe["keys"]:
            lines += nodeKeysByName.get(typeKeyName)
        contents += typeContent.replace("$Defs", lines)
    contents = template.replace("$ClassName", className).replace("$Strings", contents)
    open(OUTPUT_DIR + '/' + className + '.java', 'w').write(contents)

if __name__ == '__main__':

    # Change working directory to script directory

    abspath = os.path.abspath(__file__)
    dname = os.path.dirname(abspath)
    os.chdir(dname)

    cpgDescr = json.loads(open('../../../../src/main/resources/cpg.json').read())

    generateJava(cpgDescr)
