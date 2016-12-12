from django.conf.urls import url

from .views import index

app_name = 'form_block'

urlpatterns = [
    #/form_block/
    url(r'^$', index, name='index'),
]