from django.db import models

from django.conf import settings

from django.contrib.auth.models import User

from ProjectManager.models import Member
from ProjectManager.models import Task


class UserProfile(models.Model):
    user = models.OneToOneField(User)
    projectinfo = models.OneToOneField(Member)


class UserMessageSent(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL)
    fromuser = models.ForeignKey('UserProfile')
    title = models.CharField(max_length=100, blank=False, default="no title")
    content = models.TextField(null=False, blank=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class UserMessageReceived(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL)
    targetuser = models.ForeignKey('UserProfile')
    title = models.CharField(max_length=100, blank=False, default="no title")
    content = models.TextField(null=False, blank=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)