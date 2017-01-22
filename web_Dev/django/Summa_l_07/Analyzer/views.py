from django.shortcuts import render
from django.shortcuts import redirect
from django.shortcuts import HttpResponse

from .forms import ControllerForm
from django.forms import Form

import json

from tasks import add
from tasks_test.tasks import mul
from tasks_test.tasks import plot_ols

def index(request):

    ctx={
    }
    return render(request, 'AnalyzerDir/index.html', ctx)



def pe(request):

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
                #result = add.delay(x, y)
                #result = mul.delay(x, y)
                #result = plot_ols.delay(x,y)
                result = plot_ols.delay(x, y)

                return HttpResponse(json.dumps({
                        "consoles": result.get()[0],
                        "plots": result.get()[1]
                })
                )
            #return redirect('celerytest:index')

    ctx={}
    return render(request, 'AnalyzerDir/package-examples.html', ctx)

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