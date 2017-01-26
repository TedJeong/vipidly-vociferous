from django.conf.urls import url

from django.conf import settings

from django.conf.urls import include

from .views import index

app_name='home'

urlpatterns = [
    # /home/
    url(r'^$', index, name = "index"),
    url(r'^analyzer/', include('Analyzer.urls'), name="analyzer"),
    url(r'^projectmanager/', include('ProjectManager.urls'), name="projectmanager"),
    url(r'^contact/', include('Contact.urls'), name="contact"),
]