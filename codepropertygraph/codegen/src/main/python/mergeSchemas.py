#!/usr/bin/env python3
import os.path
import os
import json
from collections import defaultdict

class SchemaMerger:

    nonUniqueKeysFound = False
    categories = ["nodeKeys", "edgeKeys", "nodeTypes", "edgeTypes"]

    def createMergedSchema(self, schemasDirname):

        jsonFiles = [os.path.join(schemasDirname, fname)
                     for fname in os.listdir(schemasDirname)
                     if fname.endswith(".json")]
        jsonFiles.sort()

        result = dict()
        for jsonFile in jsonFiles:
            lines = open(jsonFile).readlines()

            jsonContent = ""
            for line in lines:
                if line.lstrip().startswith("//"):
                    jsonContent += "\n"
                else:
                    jsonContent += line

            try:
                cur = json.loads(jsonContent)
            except json.decoder.JSONDecodeError as exception:
                print("Error parsing json file: %s" % jsonFile)
                raise exception
            result = self._mergeLists(result, cur)

        result = self._addMissingContainsEdges(result)
        self._assertNoDuplicateIds(result)
        return result

    def _mergeLists(self, existing, cur):
        result = existing.copy()
        result.update(cur)
        for category in self.categories:
          result[category] = self._mergeListsForKey(category, existing, cur)
        return result

    def _mergeListsForKey(self, key, d1, d2):
        s1 = d1[key] if key in d1 else []
        s2 = d2[key] if key in d2 else []

        nameToRow1 = { row["name"] : row for row in s1 }
        nameToRow2 = { row["name"] : row for row in s2 }

        s1Names = set(nameToRow1.keys())
        s2Names = set(nameToRow2.keys())

        namesToMerge = s1Names & s2Names
        namesOnlyInS1 = (s1Names - namesToMerge)
        namesOnlyInS2 = (s2Names - namesToMerge)

        rows1 = [nameToRow1[name] for name in sorted(namesOnlyInS1)]
        rows2 = [nameToRow2[name] for name in sorted(namesOnlyInS2)]
        mergedRows = [self._merge(nameToRow1[name], nameToRow2[name]) for name in sorted(namesToMerge)]

        return rows1 + rows2 + mergedRows

    def _merge(self, obj1, obj2):
        self._assertNoKeyIntersection(obj1, obj2)

        result = obj1.copy()
        result.update(obj2)

        mergedLists = {key : obj1[key] + obj2[key] for key in obj1 if type(obj1[key]) == list and key in obj2}
        mergedLists = self._fixOutEdges(mergedLists)
        result.update(mergedLists)
        return result

    def _fixOutEdges(self, mergedLists):
        if "outEdges" not in mergedLists: return mergedLists

        outEdges = mergedLists["outEdges"]
        edgeNames = [x["edgeName"] for x in outEdges]
        if len(set(edgeNames)) == len(edgeNames): return mergedLists

        # This node has an `outEdges` entry and duplicate `edgeNames`.
        # We need to update this entry to ensure elements in `outEdges` with
        # the same name are merged, that is, `inNodes` lists must be merge

        edgeNameInNodesPair = [(x["edgeName"], x["inNodes"]) for x in outEdges]
        edgeNameToInNodes = defaultdict(list)
        for (edgeName, inNodes) in edgeNameInNodesPair:
            edgeNameToInNodes[edgeName].extend(inNodes)

        mergedLists["outEdges"] = []
        for (edgeName, inNodes) in edgeNameToInNodes.items():
            mergedLists["outEdges"].append({"edgeName" : edgeName, "inNodes" : inNodes})

        return mergedLists

    # for any node that has `containedNode` entries, automatically add the corresponding `outEdges`
    def _addMissingContainsEdges(self, result):
        for nodeType in result["nodeTypes"]:
            if "containedNodes" in nodeType:
                requiredInNodesForContains = [containedNode["nodeType"] for containedNode in nodeType["containedNodes"]]
                # replace entry with `edge["edgeName"] == "CONTAINS_NODE"` if it exists, or add one if it doesn't
                # to do that, convert outEdges to Map<EdgeName, OutEdge> and back at the end
                inNodesByOutEdgeName = { outEdge["edgeName"] : outEdge["inNodes"] for outEdge in nodeType["outEdges"] }
                containsInNodes = inNodesByOutEdgeName.get("CONTAINS_NODE", [])
                containsInNodes = list(set(containsInNodes + requiredInNodesForContains))
                inNodesByOutEdgeName.update({"CONTAINS_NODE": containsInNodes})
                nodeType["outEdges"] = [{"edgeName": edgeName, "inNodes": inNodes} for edgeName, inNodes in inNodesByOutEdgeName.items()]
        return result

    def _assertNoKeyIntersection(self, s1, s2):
        # 'name' is allowed in intersection since it's used as a key/foreign key
        # lists will be merged, so that's fine too
        s1RelevantKeys = {k for k, v in s1.items() if type(v) != list and v != "name"}
        s2RelevantKeys = {k for k, v in s2.items() if type(v) != list and v != "name"}
        intersection = s1RelevantKeys & s2RelevantKeys
        intersection.remove("name")
        if len(intersection) > 0:
            self.nonUniqueKeysFound = True
            print("error: %s contains duplicate keys: `%s`" % (s1["name"], ', '.join(intersection)))

    def _assertNoDuplicateIds(self, result):
        for category in self.categories:
            ids = [ entry["id"] for entry in result[category] ]
            duplicates = set([x for x in ids if ids.count(x) > 1])
            if len(duplicates) > 0:
              raise ValueError("duplicate ids found in schema definition for category `%s`: %s" % (category, duplicates))


if __name__ == '__main__':

    # Change working directory to script directory

    abspath = os.path.abspath(__file__)
    dname = os.path.dirname(abspath)
    os.chdir(dname)

    schemasDirname = '../../../../src/main/resources/schemas/'
    merger = SchemaMerger()
    mergedSchema = merger.createMergedSchema(schemasDirname)

    if merger.nonUniqueKeysFound:
        raise ValueError("non-unique keys found in schema definition (see errors above).")

    outFilename = os.path.join(schemasDirname, "..", "cpg.json")
    f = open(outFilename, "w")
    f.write(json.dumps(mergedSchema, indent=0))
    f.close()
