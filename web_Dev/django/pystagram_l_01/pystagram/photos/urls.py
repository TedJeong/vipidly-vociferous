from django.conf.urls import url
from .views import list_posts,view_post,create_post,delete_comment

app_name = 'photos'

urlpatterns = [
    url(r'^$', list_posts, name='list'),
    url(r'^(?P<pk>[0-9]+)/$', view_post, name='view'),
    url(r'^new/$', create_post, name='new'),
    url(r'^comments/(?P<pk>[0-9]+)/delete$',delete_comment, name="delete_comment")
]

