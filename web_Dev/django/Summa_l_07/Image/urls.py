from django.conf.urls import url


from .views import index
from .views import simple

app_name = 'image'

urlpatterns = [
    # /image/
    url(r'^$', index , name="index"),
    url(r'^matplotlib-demo/$', simple, name="simple"),
]
