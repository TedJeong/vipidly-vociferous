from django.utils.deprecation import MiddlewareMixin

from django.shortcuts import render

from .sample_exceptions import HelloWorldError

from raven.contrib.django.raven_compat.models import sentry_exception_handler


class SampleMiddleWare(MiddlewareMixin):
    def process_request(self, request):
        request.just_say = 'Lorem Ipsum'

    def process_exception(self, request, exc):
        if isinstance(exc, HelloWorldError):
            return render(request, 'error.html',{'error':exc, 'status':500,}, status=500)

        sentry_exception_handler(request=request)
        return render(request, 'error.html',{
            'error':exc,
            'status':500,
        })