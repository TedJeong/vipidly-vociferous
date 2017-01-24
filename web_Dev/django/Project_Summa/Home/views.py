from django.shortcuts import render



def index(request):
    is_authenticated = request.user.is_authenticated()
    print('index(request) : ',is_authenticated)
    ctx={
       'is_authenticated': is_authenticated,
    }
    return render(request,'HomeDir/index.html',ctx)