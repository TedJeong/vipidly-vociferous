from django.db import models

from django.conf import settings

from django.contrib.auth.models import User

from ProjectManager.models import Member
from ProjectManager.models import Task

from .views import get_user_profile_folder_filename

class UserProfile(models.Model):
    user = models.OneToOneField(User)
    projectinfo = models.OneToOneField(Member, blank=True, null=True)
    profile_location = models.CharField(max_length=50, default="Seoul")
    profile_age = models.IntegerField(blank=True, null=True)
    profile_facebook = models.EmailField(blank=True, null=True) # TODO: social auth
    profile_photo = models.FileField(upload_to=get_user_profile_folder_filename, blank=True, null=True)

    def __str__(self):
        return self.user.username


class UserMessage(models.Model):
    fromuser = models.ForeignKey(UserProfile, related_name='fromuser')
    touser = models.ForeignKey(UserProfile, related_name='touser')

    title = models.CharField(max_length=100, blank=False, default="no title")
    content = models.TextField(null=False, blank=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return '{} : {} to {}'.format(self.title, self.fromuser.user.username, self.touser.user.username)