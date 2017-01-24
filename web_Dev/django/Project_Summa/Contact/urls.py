from django.conf.urls import url
from django.contrib import admin

from .views import index

app_name = 'contact'

urlpatterns = [
    # /contact/
    url(r'^$', index, name="index"),
]
