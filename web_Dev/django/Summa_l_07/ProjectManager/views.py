from django.shortcuts import render
from django.shortcuts import redirect


from .models import Project
from .forms import ProjectForm

def index(request):

    projects = Project.objects.all()
    psw_names = []
    pw_names = []

    for project in projects:
        pws = project.project_workspaces.all()
        for pw in pws:
            pw_names += [pw.workspace_name]
        psw_names += [pw_names]
        pw_names = []

    #print(pw_names)
    #print(psw_names)

    psm_names = []
    pm_names = []
    for project in projects:
        pms = project.project_members.all()
        for pm in pms:
            pm_names += [pm.member_name]
        psm_names += [pm_names]
        pm_names = []

    import json
    import os

    # Build paths inside the project like this: os.path.join(BASE_DIR, ...)
    BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
    path = os.path.join(BASE_DIR, 'ProjectManager/templates/ProjectManagerDir/miserables.json')
    task_graph_json = open(path)
    #task_graph_json = json.load(task_graph_json) # deserialises it
    task_graph_json = json.dumps(json.load(task_graph_json)) # json formatted string


    ctx = {
        'projects': projects,
        'projects_workspace_names': psw_names,
        'projects_member_names': psm_names,
        'task_graph_json': task_graph_json,
        'filepath': path,
    }

    return render(request, 'ProjectManagerDir/index.html', ctx)


def create_project(request):
    if request.method == "GET":
        form = ProjectForm()
    elif request.method == "POST":
        form = ProjectForm(request.POST)

    if form.is_valid():
        project = form.save(commit=False)
        post.save()
        return redirect('projectmanager:create_project', pk=project.pk)
    ctx = {
        'form': form,
    }
    return render(request, 'ProjectManagerDir/edit_project.html', ctx)

def list_project(request):
    #project = Project.objects.get(pk=pk)
    #TODO: get projects only belong to a certain workspace or controller

    projects = Project.objects.all()
    project_workspace_names = []
    project_member_names = []
    for project in projects:
        workspaces = project.project_workspaces.all()
        for workspace in workspaces:
            project_workspace_names += [workspace.workspace_name]

    for project in projects:
        for member in project.project_members.all():
            project_member_names += [member.member_name]
    # print(projects)
    # print(projects[0].project_category.category_name)
    # print(project_workspace_names)
    # print(project_member_names)
    ctx['projects'] = projects
    ctx['project_workspace_names'] = project_workspace_names
    ctx['project_member_names'] = project_member_names
