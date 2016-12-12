from django.shortcuts import render

from django.contrib.auth.decorators import login_required

from .models import *

from .forms import LoginForm
from .forms import SigninForm

def index(request):
    is_authenticated = request.user.is_authenticated()
    print('index(request) : ',is_authenticated)
    ctx={
       'is_authenticated': is_authenticated,
    }
    return render(request,'HomeDir/index.html',ctx)

def testpage(request):
    return render(request,'HomeDir/testpage.html')

def search(request):
    q = request.GET.get("q")
    scope = request.GET.get("scope")
    modl, fids = "", ""
    if (scope.find(':')+1):
        modl = scope.split(":")[0]
        fids = scope.split(":")[1]

    #""+"__icontains"
    #Album.objects.filter(""+"__icontains"=q)
    if modl == "Album" :
        if fids == "artist":
            results = Album.objects.filter(artist__icontains=q)
        elif fids == "album_title":
            results = Album.objects.filter(album_title__icontains=q)
        elif fids == "genre":
            results = Album.objects.filter(genre__icontains=q)
    elif modl == "Song" :
        if fids == "album":
            results = Song.objects.filter(album__icontains=q)
        elif fids == "song_title":
            results = Song.objects.filter(song_title__icontains=q)
        elif fids == "song_lyrics":
            results = Song.objects.filter(song_lyrics__icontains=q)
    else:
       # you may want to return Customer.objects.none() instead
       results = Album.objects.all()

    context = dict(search_results=results, q=q, models=modl)
    return render(request, "HomeDir/index.html", context)


from django.shortcuts import redirect
from django.contrib.auth import authenticate, login, logout
from django.views.generic import View
from .forms import LoginForm
from django.contrib.auth.models import User

#@login_required
def UserLogin(request):
#TODO: 1.redirect 2.staticfiles
#    return redirect('HomeDir/login.html')
    form = LoginForm()


    if request.POST:
        username = request.POST['username']
        password = request.POST['password']
        authenticate(username=username, password=password)
        is_authenticated = request.user.is_authenticated()
        import unicodedata
        print(type(username))
        username = unicodedata.normalize('NFKD',username).encode('ascii','ignore')
        print(type(username))
        user = User.objects.get(username=username)
        print("UserLogin(request) : ", username, password, user, is_authenticated)
        ctx = {
            'is_authenticated': is_authenticated,
            'username': username,
            'is_login': True,
        }
        if request.user is not None:
            login(request, user)
            return redirect('home:index')
            #return render(request,'HomeDir/index.html',ctx)

    if form.is_valid():
        if is_authenticated == True :
            username = request.user.username

            ctx={
                'is_authenticated': is_authenticated,
                'username': username,
                 }
            return render(request, 'HomeDir/index.html', ctx)

    ctx={
        'form': form,
    }
    return render(request,'HomeDir/login.html', ctx)

def UserLogout(request):
    logout(request)
    return redirect('home:index')

# Sign in Form
class UserFormView(View):
    form_class = SigninForm
    template_name = 'HomeDir/registration_form.html'

    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, {'form': form})

    def post(self, request):
        form = self.form_class(request.POST)
        if form.is_valid():
            user = form.save(commit=False)
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user.set_password(password)
            user.save()
            user = authenticate(username = username, password = password)
        if user is not None:
            if user.is_active:
                login(request,user)
                print(request.user.username)
                #'namespace:function'
                return redirect('home:index')
        else:
            return render(request, self.template_name, {'form':form})