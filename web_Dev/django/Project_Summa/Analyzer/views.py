import json

from django.shortcuts import render
from django.shortcuts import redirect
from django.shortcuts import HttpResponse

from .models import Video
from .models import Image

from .forms import ControllerForm
from .forms import image_upload_model_form
from .forms import video_upload_form
from .forms import FileFieldForm

from django.forms import Form

from django.views.generic.edit import FormView

from SummaMLEngine.task_test import plot_ols


def index(request):
    ctx={
    }
    return render(request, 'AnalyzerDir/index.html', ctx)


def ml_core(request):
    if request.method == 'POST':
        form = Form(request.POST, request.FILES)
        print(form.is_valid())
        if form.is_valid():
            print('post method is called')
            x = int(request.POST.get('x', False))
            y = int(request.POST.get('y', False))
            print('in form : ', x+y)
            if request.is_ajax():
                print('ajax call!')
                print(x, y)
                result = plot_ols.delay(x, y)

                return HttpResponse(json.dumps({
                        "consoles": result.get()[0],
                        "plots": result.get()[1]
                })
                )
    ctx={}
    return render(request, 'AnalyzerDir/ml-core.html', ctx)


def kdsfddp(request):
    ctx = {}
    return render(request, 'AnalyzerDir/kaggle-demo-state-farm-distracted-driver-problem.html'
                  , ctx)


#kaggle-demo-santander-product-recommendation-problem
def kdsprp(request):
    controllerform = ControllerForm()
    if request.method == 'post':
        controllerform = ControllerForm(request.POST, request.FILES)
        if controllerform.is_valid():
            controller = controllerform.save(commit=False)
            controller.save()
            return redirect('analyzer:kaggle-demo-santander-product-recommendation-problem')
    ctx={
        'controllerform': controllerform,
         }
    return render(request, 'AnalyzerDir/kaggle-demo-santander-product-recommendation-problem.html'
                  ,ctx)

#kaggle-the-nature-conservancy-fisheries-monitoring-problem
def ktncfmp(request):
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-the-nature-conservancy-fisheries-monitoring-problem.html'
                  ,ctx)


#kaggle-two-sigma-financial-modeling-challenge-problem
def ktsfmcp(request):
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-two-sigma-financial-modeling-challenge-problem.html')


def image_analysis(request):

    if request.method == "GET":
        form = image_upload_model_form()

    elif request.method == "POST":
        form = image_upload_model_form(request.POST, request.FILES)

        if form.is_valid():
            image = form.save()
            image.save()
        return redirect('multimedia:image-analysis')

    form = image_upload_model_form()
    images = Image.objects.all()
    ctx={
        'form': form,
        'images': images,
    }

    return render(request, 'MultiMediaDir/image_analysis_test.html', ctx)


def image_preprocess(request):
    if request.method == "GET":
        form = image_upload_model_form()

    elif request.method == "POST":
        form = image_upload_model_form(request.POST, request.FILES)

        if form.is_valid():
            image = form.save()
            image.save()
            image_for_preprocess = Image.objects.last()

            ctx={
                'imagefile': image_for_preprocess,
            }
        return render(request, 'MultiMediaDir/image_preprocess.html',ctx)

    form = image_upload_model_form()

    ctx={
        'form': form,
    }

    return redirect('multimedia:image-analysis')


def video_analysis(request):
    videos = Video.objects.all()
    ctx = {
        'videos': videos,
    }

    if request.POST :
        form = video_upload_form()

        if form.is_valid():
            return redirect('home:video-anaylsis')

    return render(request, 'MultiMediaDir/video_analysis.html', ctx)


class FileFieldView(FormView):
    form_class = FileFieldForm
    template_name = 'MultiMediaDir/image_analysis_test.html'
    success_url = 'MultiMediaDir/image_analysis_test.html'

    def post(self, request, *args, **kwargs):
        form_class = self.get_form_class()
        form = self.get_form(form_class)
        files = request.FILES.getlist('file_field')
        if form.is_valid():
            for f in files:
                print(f)
            return self.form_valid(form)
        else:
            return self.form_invalid(form)