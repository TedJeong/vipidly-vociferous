from django.conf.urls import url
from django.contrib import admin

from . import views

app_name = 'audio'

urlpatterns = [
    # /audio/
    url(r'^$', views.index , name="index"),

    # /audio/album/712/
    url(r'^album/(?P<pk>[0-9]+)/$', views.DetailView.as_view(), name = "detail"),
#url(r'^(?P<album_id>[0-9]+)/$', views.detail, name = "detail"),

    # /audio/album/add/
    url(r'^album/add/$', views.AlbumCreate.as_view(), name='album-add'),

    # /audio/album/2/
    url(r'^album/(?P<pk>[0-9]+)/update/$', views.AlbumUpdate.as_view(), name='album-update'),

    # /audio/album/2/delete
    url(r'^album/(?P<pk>[0-9]+)/delete/$', views.AlbumDelete.as_view(), name='album-delete'),
]
