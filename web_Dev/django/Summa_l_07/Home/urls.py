from django.conf.urls import url

from django.conf import settings


from django.contrib.auth.views import login
from django.contrib.auth.views import logout


from .views import index
from .views import search
from .views import testpage
from .views import UserFormView

from .views import UserLogin
from .views import UserLogout

app_name='home'

urlpatterns = [
    # /home/
    url(r'^$', index, name = "index"),
    url(r'^logintemp/$', UserLogin, name='logintmp'),
    url(r'^logouttemp/$', UserLogout, name='logouttmp'),
    url(r'^login/$', login, {'template_name': 'login.html'}),
    url(r'^logout/$', logout, {'next_page': settings.LOGOUT_REDIRECT_URL}, name='logout'),
    #url(r'^logout/$', logout, {'next_page': 'index'}),
    url(r'^testpage/$', testpage, name = "testpage"),
    url(r'^register/$', UserFormView.as_view(), name = "register"),
    url(r'^searchresult/$', search, name = "searchresult"),
]
