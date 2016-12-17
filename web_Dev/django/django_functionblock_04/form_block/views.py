from django.shortcuts import render
from django.shortcuts import redirect

from .models import SampleModel

from .forms import SampleForm
from .forms import SampleModelForm

def index(request):
    # auto_id : generate name and id field automatically as id_%s
    # initial :
    # prefix :
    # SampleForm(auto_id=False,initial={'formfield_1':'1'},prefix='tsmp')
    form = SampleForm(auto_id=False)
    modelform = SampleModelForm()

    ctx={
        'form':form,
        'modelform':modelform,
    }
    return render(request, 'form_block/index.html', ctx)

def modal_form_index(request):
    modelform = SampleModelForm()
    print(request.method)
    if request.method == 'POST':
        model = SampleModel
        modelform = SampleModelForm(request.POST, request.FILES)
        print('request get post!')
        print(modelform.is_valid())
        if modelform.is_valid():
            model = modelform.save(commit=False)
            print('model is loaded!')
            ctx = {
                'model': model,
            }
            return redirect('form_block:modal_form_result',ctx)

    ctx = {
        'form': modelform,
    }

    return render(request, 'form_block/modal_form_index.html', ctx)

# redirect, render, HttpResponse, JsonResponse
# redirect(modelinstance) get_absolute_url
# hardcode html
# url resolver {% url 'appname:viewname' %}
# reverse, reverse_lazy
#

def modal_form_result(request):
    return render(request,'modal_form_result.html')