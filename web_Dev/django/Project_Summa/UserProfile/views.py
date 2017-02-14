from django.shortcuts import render

from django.contrib.auth.decorators import login_required

# UserProfile doesn't have view.

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
    """
    TODO:
    Currently this path is hardcoded in index, base in ProjectManager and Analyzer Apps.
    Changes should be in accordance with them.
    :param instance:
    :param _:
    :return:
    """
    return "%s/info/profile-photo_%s_%s.jpg" % (instance.user.username, instance.user.username, instance.user.pk)


@login_required
def get_user_upload_profile_photo_callback(instance, filename):
    def get_user_profile_folder_filename(instance, _):
        return "%s/info/profile-photo_%s_%s" % (instance.user.username, instance.user.username, instance.user.pk)
    return get_user_profile_folder_filename


@login_required
def get_user_uploadfile_folder(project_or_task, filename):
    if isinstance(project_or_task, Project):
        return "ProjectManager/%s/%s" % (project_or_task.project_name, filename)
    if isinstance(project_or_task, Task):
        return "ProjectManager/%s/%s/%s" % (project_or_task.task_project.project_name, project_or_task.task_name, filename)