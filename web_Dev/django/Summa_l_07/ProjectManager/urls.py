from django.conf.urls import url

from .views import index
from .views import list_project
from .views import create_project

app_name = "projectmanager"

urlpatterns = [
    # /projectmanager/
    url(r'^$', index , name = "index"),
    url(r'^create-project', create_project, name="create_project"),
]