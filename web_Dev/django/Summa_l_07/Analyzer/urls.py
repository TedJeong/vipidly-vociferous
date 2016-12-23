from django.conf.urls import url

from .views import index
from .views import pe
from .views import kdsfddp
from .views import kdsprp

app_name = 'analyzer'

urlpatterns=[
    #/analyzer/
    url(r'^$', index ,name="index"),
    url(r'^package-examples/$', pe, name="package-examples"),
    url(r'^kaggle-demo-state-farm-distracted-driver-problem/',
        kdsfddp , name="kaggle-demo-state-farm-distracted-driver-problem"),
    url(r'^kaggle-demo-santander-product-recommendation-problem/',
        kdsprp , name="kaggle-demo-santander-product-recommendation-problem"),
]