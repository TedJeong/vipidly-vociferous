from django.contrib import admin

from .models import Audio
from .models import Album
from .models import Song


class AudioAdmin(admin.ModelAdmin):
    list_display = ('id','filename','filetype','created_at')
    search_fields = ('tagkey',)

class AlbumAdmin(admin.ModelAdmin):
    list_display = ('id','artist','album_title','genre','created_at')
    search_fields = ('genre','artist','album_title')

# AudioAdmin class should be above.
admin.site.register(Audio, AudioAdmin)
admin.site.register(Album, AlbumAdmin)
admin.site.register(Song)