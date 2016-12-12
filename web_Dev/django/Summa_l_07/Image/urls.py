from django.conf.urls import url


from .views import index

app_name = 'image'

urlpatterns = [
    # /image/
    url(r'^$', index , name="index"),
]
