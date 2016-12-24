from django.shortcuts import render



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
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-demo-santander-product-recommendation-problem.html'
                  ,ctx)

#kaggle-the-nature-conservancy-fisheries-monitoring-problem
def ktncfmp(request):
    ctx={}
    return render(request, 'AnalyzerDir/kaggle-the-nature-conservancy-fisheries-monitoring-problem.html'
                  ,ctx)