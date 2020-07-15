
def import_code_query(path, project_name = None, language = None):
    if path is None:
        raise Exception('An importCode query requires a project path')
    if project_name and language:
        return "importCode(inputPath=\"%s\", projectName=\"%s\", language=\"%s\")" % (path, project_name, language)
    if project_name and (language is None):
        return "importCode(inputPath=\"%s\", projectName=\"%s\")" % (path, project_name)
    if project_name is None and language is None:
        return u"importCode(\"%s\")" % (path)


def workspace_query():
    return "workspace"


