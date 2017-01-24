from django.conf.urls import url

from django.conf import settings


from .views import index

app_name='home'

urlpatterns = [
    # /home/
    url(r'^$', index, name = "index"),
]