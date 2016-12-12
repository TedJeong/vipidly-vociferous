from django.shortcuts import render

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