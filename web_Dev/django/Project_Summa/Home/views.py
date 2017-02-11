from django.shortcuts import render
from django.shortcuts import redirect

from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User

from UserProfile.models import UserProfile

from django.views.generic import View

from .forms import LoginForm
from .forms import SigninForm


def index(request):
    is_authenticated = request.user.is_authenticated()
    username = request.user.username
    print('index(request) : ',is_authenticated)
    ctx={
       'is_authenticated': is_authenticated,
        'username': username,
    }
    return render(request,'HomeDir/index.html',ctx)


def UserLogin(request):
#TODO: 1.redirect 2.staticfiles
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
    return render(request, 'registration/login.html', ctx)


def UserLogout(request):
    logout(request)
    return redirect('home:index')


# Sign in Form
class UserSignupView(View):
    form_class = SigninForm
    template_name = 'registration/signup.html'

    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, {'form': form})

    def post(self, request):
        form = self.form_class(request.POST)

        if form.is_valid():
            # cleaned_data only counts after .is_valid() is called.
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']

            user = form.save(commit=False)
            user.set_password(password)
            user.save()
            profile = UserProfile.objects.create(user=user)
            profile.save()
            user = authenticate(username = username, password = password)

            if user is not None:
                if user.is_active:
                    login(request, user)
                    print(request.user.username)
                    #'namespace:function'
                    return redirect('home:index')
            else:
                return render(request, self.template_name, {'form':form})
        return render(request, self.template_name, {'form': form})