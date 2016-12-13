from django.shortcuts import render
from django.shortcuts import redirect


from .models import Project
from .forms import ProjectForm

def index(request):

    projects = Project.objects.all()
    projects_workspace_names = []
    project_workspace_names = []
    project_member_names = []
    for project in projects:
        project_workspaces = project.project_workspaces.all()
        for project_workspace in project_workspaces:
            project_workspace_names += [project_workspace.workspace_name]
        projects_workspace_names += project_workspace_names

    for project in projects:
        for member in project.project_members.all():
            project_member_names += [member.member_name]

    # print(projects)
    # print(projects[0].project_category.category_name)
    # print(project_workspace_names)
    # print(project_member_names)
    ctx = {
        'projects': projects,
        'projects_workspace_names': projects_workspace_names,
        'project_member_names': project_member_names,
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
