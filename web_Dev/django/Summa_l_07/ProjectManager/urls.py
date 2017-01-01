from django.conf.urls import url

from .views import index
from .views import list_project
from .views import list_category
from .views import create_project
from .views import task_graph_added


app_name = "projectmanager"

urlpatterns = [
    # /projectmanager/
    url(r'^$', index , name = "index"),
    url(r'^create-project', create_project, name="create_project"),
    url(r'^list-category', list_category, name="list_category"),
    url(r'^api/task-graph-added$', task_graph_added, name="task_graph_added")
]