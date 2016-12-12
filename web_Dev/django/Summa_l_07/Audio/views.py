from django.shortcuts import render, get_object_or_404
from django.urls import reverse_lazy

from django.views import generic

from django.views.generic.edit import CreateView
from django.views.generic.edit import UpdateView
from django.views.generic.edit import DeleteView

from .models import Audio, Album, Song

def index(request):
    Albums = Album.objects.all()
    Songs = Song.objects.all()
    Audios = Audio.objects.all()
    ctx = {
        'albums_all':Albums,
        'audios_all':Audios,
        'songs_all':Songs
        }
    return render(request,'AudioDir/index.html',ctx)


def detail(request, album_id):
    album = get_object_or_404(Album, pk=album_id)
    return render(request,'album_detail.html',{'album' : album,})


class IndexView(generic.ListView):
    template_name = 'AudioDir/index.html'
    context_object_name = 'albums_all'

    def get_queryset(self):
        return Album.objects.all()


class DetailView(generic.DetailView):
    model = Album
    template_name = 'AudioDir/album_detail.html'


class AlbumCreate(CreateView):
    model = Album
    fields = ['artist', 'album_title', 'genre', 'album_logo']


class AlbumUpdate(UpdateView):
    model = Album
    fields = ['artist', 'album_title', 'genre', 'album_logo']


class AlbumDelete(DeleteView):
    model = Album
    # goto the music app index page when successfully remove object
    success_url = reverse_lazy('audio:index')