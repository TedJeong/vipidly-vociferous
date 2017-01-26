from django.conf.urls import url

from .views import index
from .views import ml_core
from .views import kdsfddp
from .views import kdsprp
from .views import ktncfmp
from .views import ktsfmcp

from .views import video_analysis
from .views import image_analysis
from .views import image_preprocess
from .views import FileFieldView

app_name = 'analyzer'

urlpatterns=[
    #/analyzer/
    url(r'^$', index ,name="index"),
    url(r'^image-analysis/$', image_analysis, name="image-analysis"),
    url(r'^video-analysis/$', video_analysis, name="video-analysis"),
    url(r'^image-analysis/image-preprocess/$', image_preprocess, name="image-preprocess"),
    url(r'^image-analysis/multiple-image-upload-test/$', FileFieldView.as_view(), name="multiple-image-upload-test"),
    url(r'^ml-core/$', ml_core, name="ml-core"),
    url(r'^kaggle-demo-state-farm-distracted-driver-problem/',
        kdsfddp , name="kaggle-demo-state-farm-distracted-driver-problem"),
    url(r'^kaggle-demo-santander-product-recommendation-problem/',
        kdsprp , name="kaggle-demo-santander-product-recommendation-problem"),
    url(r'^kaggle-the-nature-conservancy-fisheries-monitoring-problem/',
        ktncfmp, name="kaggle-the-nature-conservancy-fisheries-monitoring-problem"),
    url(r'^kaggle-two-sigma-financial-modeling-challenge-problem/',
        ktsfmcp, name='kaggle-two-sigma-financial-modeling-challenge-problem'),
]