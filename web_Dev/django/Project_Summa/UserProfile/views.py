from django.shortcuts import render

from django.contrib.auth.decorators import login_required

# Create your views here.
@login_required
def get_user_profile_folder(instance, filename):
    return "%s/info/%s" % (instance.user.username, filename)


@login_required
def get_user_file_folder(instance, filename):
    return "%s/file/%s" % (instance.user.username, filename)


@login_required
def get_user_imagefile_folder(instance, filename):
    return "%s/imagefile/%s" % (instance.user.username, filename)