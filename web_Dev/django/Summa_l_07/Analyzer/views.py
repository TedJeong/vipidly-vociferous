from django.shortcuts import render
from django.shortcuts import redirect

from .forms import ControllerForm

def index(request):
    ctx={}
    return render(request, 'AnalyzerDir/index.html', ctx)

def pe(request):
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