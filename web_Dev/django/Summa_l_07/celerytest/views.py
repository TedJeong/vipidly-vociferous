from django.shortcuts import render
from django.shortcuts import redirect
from django.shortcuts import HttpResponse

from django.forms import Form

from tasks import add

from django.views.decorators.csrf import csrf_protect


def index(request):
    form = Form()
    if request.method == 'POST':
        form = Form(request.POST, request.FILES)
        print(form.is_valid())
        if form.is_valid():
            print('post method is called')
            x = int(request.POST.get('x', False))
            y = int(request.POST.get('y', False))
            print('in form : ',x+y)
            if request.is_ajax():
                print('ajax call!')
                print(x, y)
                result = add.delay(x, y)
                print(result.get())
                return HttpResponse(result.get())
            #return redirect('celerytest:index')

    ctx={
        'form' : form,
    }

    return render(request, 'index.html', ctx)