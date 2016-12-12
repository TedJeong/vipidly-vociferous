from django.conf.urls import url

from .views import index

app_name = "projectmanager"

urlpatterns = [
    # /projectmanager/
    url(r'^$', index, name = "index"),
]