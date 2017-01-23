from django.shortcuts import render
from django.shortcuts import redirect

from .models import Video
from .models import Image

from .forms import image_upload_form
from .forms import image_upload_model_form
from .forms import video_upload_form

def index(request):
    ctx = {
        'datalen': range(1,10),
    }
    return render(request,'MultiMediaDir/index.html',ctx)


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
