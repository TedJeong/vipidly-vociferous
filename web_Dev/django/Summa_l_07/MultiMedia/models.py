from __future__ import unicode_literals

from django.db import models


class Image(models.Model):
    image_name = models.CharField(max_length=100)
    image_file = models.ImageField(upload_to="images/")

    def __str__(self):
        return '{}'.format(self.image_name)

class Video(models.Model):
    video_name = models.CharField(max_length=100)
    video_type = models.CharField(max_length=50)
    video_file = models.FileField(upload_to="videos/")
    class Meta:
        pass