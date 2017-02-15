from django import template
from ProjectManager.models import Progress

from decimal import Decimal

register = template.Library()

@register.filter(name='check_profile_photo_bool')
def check_profile_photo_bool(profile_photo_field):
    return bool(profile_photo_field)


@register.filter(name='member_progress_sum')
def member_progress_sum(member):
    val=0
    for gauges in Progress.objects.filter(progress_member__exact=member):
        val += gauges.progress_gauge
    return val


@register.filter(name='project_select_by_category_name')
def project_select_by_category_name(project):
    return None


@register.filter(name='project_progress_sum')
def project_progress_sum(project):
    val=0
    for gauges in Progress.objects.filter(progress_project__exact=project):
        val += gauges.progress_gauge
    return val

@register.filter(name='project_in_workspace')
def projects_in_workspace(workspace):
    return workspace.project_set.all()

@register.filter(name='get_tasks_in_project')
def get_tasks_in_project(project):
    return project.task_set.all()

@register.filter(name='get_members_in_task')
def get_members_in_task(task):
    return task.task_members.all()

@register.filter(name='get_member_gauge_in_task')
def get_member_gauge_in_task(task, member):
    # get function query does not exist error if query is empty
    #task.progress_set.get(progress_member__exact=member).progress_gauge
    if task.progress_set.filter(progress_member__exact=member).exists():
        return task.progress_set.get(progress_member__exact=member).progress_gauge
    else:
        return 0

@register.filter(name='get_member_opendate_in_task')
def get_member_opendate_in_task(task, member):
    if task.progress_set.filter(progress_member__exact=member).exists():
        return task.progress_set.get(progress_member__exact=member).progress_open_date
    else:
        return None

# TODO: check contains function is also valid in list form
@register.filter(name='member_project_check')
def member_project_check(member, project):
    name = member.member_name
    return project.project_members.filter(member_name__contains=name).exists()