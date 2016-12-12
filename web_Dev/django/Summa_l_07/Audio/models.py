from __future__ import unicode_literals

from django.db import models
from django.core.urlresolvers import reverse

from datetime import datetime
# datetime, pytz, django.utils.timezone()
# default=datetime.now().__str__()
class Category(models.Model):
    catergories = models.CharField(max_length=200)

class Tag(models.Model):
    tags = models.CharField(max_length=200)

class Audio(models.Model):
    filename = models.CharField(max_length=200)
    datafile = models.FileField(verbose_name='datafile')
    filetype = models.CharField(max_length=10)
    tagkey = models.ManyToManyField('Tag')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return '{}'.format(self.filename)

class Voice(models.Model):
    pass

class Album(models.Model):
    artist = models.CharField(max_length=250)
    album_title = models.CharField(max_length=500)
    genre = models.CharField(max_length=100)
    album_logo = models.CharField(max_length=1000)

    tagkey = models.ManyToManyField('Tag',blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


    def get_absolute_url(self):
        return reverse('audio:detail', kwargs={'pk': self.pk})

    def __str__(self):
        return self.album_title + ' - ' + self.artist

class Song(models.Model):
    album = models.ForeignKey(Album, on_delete=models.CASCADE)
    song_title = models.CharField(max_length=250)
    file_type = models.CharField(max_length=10,default='mp3')
    song_lyrics = models.TextField(default="")
    is_favorite = models.BooleanField(default=False)

    def __str__(self):
        return self.song_title

class AlbumComment(models.Model):
    album = models.ForeignKey(Album)
    comment = models.CharField(max_length=300)
    created_at = models.DateTimeField(auto_now_add=True)
    class Meta:
        ordering = ['-created_at']