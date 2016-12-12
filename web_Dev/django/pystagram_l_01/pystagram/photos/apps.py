from __future__ import unicode_literals

from django.apps import AppConfig


class PhotosConfig(AppConfig):
    name = 'photos'
    # app 이 준비되면 호출 됨
    def ready(self):
        from . import signals
