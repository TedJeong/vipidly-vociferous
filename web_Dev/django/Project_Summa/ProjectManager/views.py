from django.shortcuts import render
from django.shortcuts import redirect
from django.shortcuts import HttpResponse
from django.shortcuts import render_to_response

from django.contrib.auth.decorators import login_required

from django.template import RequestContext

from django.http import HttpResponseRedirect

from .models import Project
from .models import Task
from .models import Member
from .forms import ProjectForm
from .forms import TaskForm

from django.core import serializers
from rest_framework import serializers as drf_serializers
from rest_framework import viewsets

def index(request):

    # model form
    taskform = TaskForm()

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


    # if

    #print(tasks)
    #ctx['tasks'] = tasks




    members = Member.objects.all()

    if request.method == 'POST':
        print("post!")
        taskform = TaskForm(request.POST)
        print(taskform.is_valid())
        if taskform.is_valid():

            task = taskform.save(commit=False)
            task.save()

        if request.is_ajax():
            print('index call')
            # print(request.POST['task_pks'])
            delete_pks = request.POST.getlist('task_pks[]')
            for ids in delete_pks:
                print(ids, " deleted.")
                task = Task.objects.get(pk=ids)
                task.delete()
                # data = serializers.serialize('json', request.POST["task_pks"])
            #ctxes = index_ctx_call()
            # return render(request, 'ProjectManagerDir/index.html', ctxes)
            #taskform=TaskForm()
            #render_to_response('ProjectManagerDir/index.html',{'taskform':taskform})
        return redirect('projectmanager:index')



    tasks = Task.objects.all().order_by('-task_priority')

    print('ctz')


    is_authenticated = request.user.is_authenticated()
    username = request.user.username

    ctx = {
        'is_authenticated': is_authenticated,
        'username': username,
        'projects': projects,
        'projects_workspace_names': psw_names,
        'projects_member_names': psm_names,
        'task_graph_json': task_graph_json,
        'filepath': path,
        'tasks': tasks,
        'members': members,
        'taskform': taskform,
    }


    return render(request, 'ProjectManagerDir/index.html', ctx)


@login_required
def user_profile(request):
    current_user = request.user
    profile = current_user.userprofile
    ctx = {
        'is_authenticated': True,
        "username": current_user.username,
        "user": profile,
    }
    return render(request, 'ProjectManagerDir/user-profile.html', ctx)
