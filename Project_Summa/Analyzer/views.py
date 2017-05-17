import json
import os

from django.contrib.auth.decorators import login_required
from django.shortcuts import render
from django.shortcuts import redirect
from django.shortcuts import HttpResponse

from django.contrib.auth.models import User

# from .models import Video
# from .models import Image
from .models import Job
from UserProfile.models import UserMessage

from .forms import ControllerForm
from .forms import JobForm
from .forms import image_upload_model_form
from .forms import datafile_upload_model_form
from .forms import video_upload_form
from .forms import FileFieldForm

from django.forms import Form

from django.views.generic.edit import FormView

from SummaMLEngine.task_test import plot_ols
from SummaMLEngine.task_test import plot_raw_test
from SummaMLEngine.task_test import plot_feature_all_test
from SummaMLEngine.task_test import plot_model_all_test
from SummaMLEngine.task_test import plot_report_all_test

from SummaMLEngine.task_test import styler_function


def index(request):
    ctx={}
    is_authenticated = request.user.is_authenticated()
    username = request.user.username
    print(username)
    print('index(request) : ',is_authenticated)
    if is_authenticated == True:
        messages = UserMessage.objects.filter(touser__user__username__exact=request.user.username)
        ctx['messages'] = messages
    ctx['is_authenticated'] = is_authenticated
    ctx['username'] = username

    return render(request, 'AnalyzerDir/index.html', ctx)


def ml_core(request):
    jobs = Job.objects.all()
    datafile_form = image_upload_model_form()
    is_authenticated = request.user.is_authenticated()
    username = request.user.username

    if request.method == 'POST':
        form = Form(request.POST, request.FILES)
        datafile_form = image_upload_model_form(request.POST, request.FILES)
        print(form.is_valid(), datafile_form.is_valid())

        if datafile_form.is_valid():
            datafile = form.save()
            datafile.save()

        if form.is_valid():
            print('post method is called')
            x = int(request.POST.get('x', False))
            y = int(request.POST.get('y', False))
            status = request.POST.get('ajax-')
            print('in form : ', x+y)
            if request.is_ajax():
                print('ajax call!')
                print(x, y)
                #result = plot_ols.delay(x, y)
                #print(type(result)) # Asyc.celery.result
                #result = plot_raw_test(x, y)
                result = plot_feature_all_test(x, y)
                model_result = plot_model_all_test()
                report_result = plot_report_all_test()
                print(result[2])
                if isinstance(result, list):
                    return HttpResponse(json.dumps({
                        "consoles": result[0],
                        "plots": result[1],
                        "_3dplots": result[2],
                        "xlabel": result[3],
                        "ylabel": result[4],
                        "zlabel": result[5],
                        "model_consoles": model_result[0],
                        "model_plots": model_result[1],
                        "report_stack": report_result[0],
                    }))
                else:
                    #TODO: currently not using
                    return HttpResponse(json.dumps({
                            "consoles": result.get()[0],
                            "plots": result.get()[1],
                            "_3dplots": result.get()[2],
                            "xlabel": result[3],
                            "ylabel": result[4],
                            "zlabel": result[5],
                            "model_consoles": model_result[0],
                            "model_plots": model_result[1],
                    })
                    )
    ctx={
        "jobs" : jobs,
        "datafile_form": datafile_form,
        'is_authenticated': is_authenticated,
        'username': username,
    }

    if is_authenticated == True:
        messages = UserMessage.objects.filter(touser__user__username__exact=request.user.username)
        ctx['messages'] = messages
    ctx['is_authenticated'] = is_authenticated
    ctx['username'] = username

    return render(request, 'AnalyzerDir/ml-core.html', ctx)


#------------------------------------------------------------------------------------------------
# ml-core
#kaggle-santander-product-recommendation-problem
def ksprp(request):
    controllerform = ControllerForm()
    if request.method == 'post':
        controllerform = ControllerForm(request.POST, request.FILES)
        if controllerform.is_valid():
            controller = controllerform.save(commit=False)
            controller.save()
            return redirect('analyzer:kaggle-santander-product-recommendation-problem')
    ctx={
        'controllerform': controllerform,
         }
    return render(request, 'AnalyzerDir/kaggle-santander-product-recommendation-problem.html'
                  ,ctx)


#kaggle-outbrain-click-prediction-problem
def kocpp(request):
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-quora-question-pairs-problem.html')


#------------------------------------------------------------------------------------------------
# image-core



def image_ml_core(request):
    ctx={}
    jobs = Job.objects.all()
    #datafile_form = image_upload_model_form()
    job_form = JobForm()
    datafile_form = datafile_upload_model_form()
    #print(datafile_form)
    is_authenticated = request.user.is_authenticated()

    ## TODO: NOT WORKING?
    username = request.user.username

    print('cored!')
    #print(request.method)
    if request.method == "POST":
        print('post method is called')
        job_form = JobForm(request.POST)
        datafile_form = datafile_upload_model_form(request.POST, request.FILES)
        print(datafile_form.errors)
        #print(datafile_form.is_valid())

        # Add New Job on job list
        if job_form.is_valid():
            job = job_form.save(commit=False)
            job.save()

        # Delete Job on job list
        if 'delete_job' in request.POST.keys():
            if request.POST['delete_job'] == '1':
                if request.is_ajax():
                    print(request.POST.get('ajax_differer'))
                    if request.POST['ajax_diffferer'] == '1':
                        delete_pks = request.POST.getlist('job_pks[]')
                        for ids in delete_pks:
                            job = Job.objects.get(pk=ids)
                            job.delete()
                        return HttpResponse('done')

        if datafile_form.is_valid(): # TODO: adding validation on ajax request is not implemented
            #print('dataform in valid!')
            datafile_form.save()
            BASE = os.getcwd()
            fileowner_pk = request.POST.get('fileowner')
            fileowner_username = User.objects.get(pk=int(fileowner_pk)).username
            ext = request.FILES['filepath'].name.split('.')[-1]
            img = BASE + "/%s/files/%s" % (fileowner_username, datafile_form['filename'].value())# ext
        else:
            img = None

        x = int(request.POST.get('x', False))
        y = int(request.POST.get('y', False))
        #status = request.POST.get('ajax-status')
        styler_option = request.POST.get('styler_option')
        #print('in form : ', x+y)
        #print(request.is_ajax())
        print(img)
        if request.is_ajax():
            print('ajax call!')
            print(x, y)
            result = styler_function(img, styler_option)
            print(result[0])
            return HttpResponse(json.dumps({
                    'consoles' : result[0],
                    'plots' : result[1]
                }))
        return redirect('analyzer:image-ml-core')

    # form = image_upload_model_form()
    # images = Image.objects.all()
    # ctx={
    #     'form': form,
    #     'images': images,
    # }
    #
    messages=None
    if is_authenticated == True:
        messages = UserMessage.objects.filter(touser__user__username__exact=request.user.username)
    ctx['messages'] = messages
    ctx['is_authenticated'] = is_authenticated
    ctx['username'] = username
    ctx['jobs'] = jobs
    ctx['datafile_form'] = datafile_form
    ctx['job_form'] = job_form
    #print(datafile_form.as_p())

    return render(request, 'AnalyzerDir/image-ml-core.html', ctx)
'''
def image_mls_core(request):
    if request.method == 'POST':
        form = Form(request.POST, request.FILES)
        datafile_form = image_upload_model_form(request.POST, request.FILES)
        print(form.is_valid(), datafile_form.is_valid())

        if datafile_form.is_valid():
            datafile = form.save()
            datafile.save()

        if form.is_valid():
            print('post method is called')
            x = int(request.POST.get('x', False))
            y = int(request.POST.get('y', False))
            status = request.POST.get('ajax-')
            print('in form : ', x+y)
            if request.is_ajax():
                print('ajax call!')
                print(x, y)
                #result = plot_ols.delay(x, y)
                #print(type(result)) # Asyc.celery.result
                #result = plot_raw_test(x, y)
                result = plot_feature_all_test(x, y)
                model_result = plot_model_all_test()
                report_result = plot_report_all_test()
                print(result[2])
                if isinstance(result, list):
                    return HttpResponse(json.dumps({
                        "consoles": result[0],
                        "plots": result[1],
                        "_3dplots": result[2],
                        "xlabel": result[3],
                        "ylabel": result[4],
                        "zlabel": result[5],
                        "model_consoles": model_result[0],
                        "model_plots": model_result[1],
                        "report_stack": report_result[0],
                    }))
                else:
                    #TODO: currently not using
                    return HttpResponse(json.dumps({
                            "consoles": result.get()[0],
                            "plots": result.get()[1],
                            "_3dplots": result.get()[2],
                            "xlabel": result[3],
                            "ylabel": result[4],
                            "zlabel": result[5],
                            "model_consoles": model_result[0],
                            "model_plots": model_result[1],
                    })
                    )
    ctx={
        "jobs" : jobs,
        "datafile_form": datafile_form,
        'is_authenticated': is_authenticated,
        'username': username,
    }


    return render(request, 'AnalyzerDir/image-ml-core.html', ctx)'''



#kaggle-statefarm-disctracted-driver-detection-problem
def ksfddp(request):
    ctx = {}
    return render(request, 'AnalyzerDir/kaggle-state-farm-distracted-driver-problem.html'
                  , ctx)


#kaggle-the-nature-conservancy-fisheries-monitoring-problem
def ktncfmp(request):
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-the-nature-conservancy-fisheries-monitoring-problem.html'
                  ,ctx)


#kaggle-two-sigma-financial-modeling-challenge-problem
def ktsfmcp(request):
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-two-sigma-financial-modeling-challenge-problem.html')


#------------------------------------------------------------------------------------------------
# nlp-core
def nlp_ml_core(request):
    return render(request, 'AnalyzerDir/nlp-ml-core.html')


#kaggle-quora-question-pairs-problem
def kqqpp(request):
    ctx={}
    return render(request, 'AnalyzerDir/jupyter-kaggle-quora-question-pairs-problem.html')


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
        return render(request, 'AnalyzerDir/image_preprocess.html',ctx)

    form = image_upload_model_form()

    ctx={
        'form': form,
    }

    return redirect('analyzer:image-analysis')


def video_analysis(request):
    videos = Video.objects.all()
    ctx = {
        'videos': videos,
    }

    if request.POST :
        form = video_upload_form()

        if form.is_valid():
            return redirect('home:video-anaylsis')

    return render(request, 'AnalyzerDir/video_analysis.html', ctx)


class FileFieldView(FormView):
    form_class = FileFieldForm
    template_name = 'AnalyzerDir/image_analysis_test.html'
    success_url = 'AnalyzerDir/image_analysis_test.html'

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