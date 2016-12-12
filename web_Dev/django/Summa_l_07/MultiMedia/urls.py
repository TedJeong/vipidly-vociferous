from django.conf.urls import url


from .views import index
from .views import video_analysis
from .views import image_analysis
from .views import image_preprocess

app_name = 'multimedia'

urlpatterns = [
    # /multimedia/
    url(r'^$', index, name="index"),
    url(r'^image-analysis/$', image_analysis, name="image-analysis"),
    url(r'^video-analysis/$', video_analysis, name="video-analysis"),
    url(r'^image-analysis/image-preprocess/$', image_preprocess, name="image-preprocess"),
]
