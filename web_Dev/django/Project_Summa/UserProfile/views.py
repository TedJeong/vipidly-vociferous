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


@login_required
def get_user_profile_folder_filename(instance, _):
    return "%s/info/profile-photo_%s_%s.jpg" % (instance.user.username, instance.user.username, instance.user.pk)

@login_required
def get_user_upload_profile_photo_callback(instance, filename):
    def get_user_profile_folder_filename(instance, _):
        return "%s/info/profile-photo_%s_%s" % (instance.user.username, instance.user.username, instance.user.pk)
    return get_user_profile_folder_filename