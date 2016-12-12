from django.shortcuts import render


def index(request):
    return render(request, 'ProjectManagerDir/index.html')


def create_project(request):
    if request.method == "GET":
        form = ProjectForm()
    elif request.method == "POST":
        form = ProjectForm(request.POST)

    if form.is_valid():
        project = form.save(commit=False)
        post.save()
        return redirect('projectmanager:view', pk=project.pk)
    ctx = {
        'form': form,
    }
    return render(request, 'ProjectManagerDir/edit_project.html', ctx)

def list_project(request, pk):
    project = Project.objects.get(pk=pk)
    if request.method == 'GET':
        form =