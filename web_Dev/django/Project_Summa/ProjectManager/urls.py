from django.conf.urls import url

from .views import index
from .views import user_profile

app_name = "projectmanager"

urlpatterns = [
    # /projectmanager/
    url(r'^$', index , name = "index"),
    url(r'^user-profile', user_profile, name = "profile")
]