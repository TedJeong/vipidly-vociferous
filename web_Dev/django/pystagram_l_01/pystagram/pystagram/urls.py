"""pystagram URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url,include
from django.contrib import admin

from django.conf.urls.static import static
from django.conf import settings

from django.contrib.auth.views import login
from django.contrib.auth.views import logout

from rest_framework import routers

from photos.views import PostViewSet
router = routers.DefaultRouter()
router.register(r'photos', PostViewSet)

urlpatterns = [
    # 기본으로 registration/login.html 이므로 registration folder를
    # 만들어주거나,
    url(r'^login/$', login, {'template_name':'login.html'}),
    # logout 이후에 갈 url
    url(r'^logout/$', logout, {'next_page':settings.LOGIN_URL}),
    url(r'^admin/', admin.site.urls),
    url(r'^photos/', include('photos.urls')),
    url(r'api/', include(router.urls)),
]

# static only works with debug mode
# urlpatterns.extends(static(..))
urlpatterns += static(
	settings.MEDIA_URL, document_root=settings.MEDIA_ROOT
)

urlpatterns.append(
    url(r'',include('social.apps.django_app.urls', namespace='social')),
)