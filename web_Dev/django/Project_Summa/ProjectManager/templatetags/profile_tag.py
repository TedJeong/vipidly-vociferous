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


# TODO: check contains function is also valid in list form
@register.filter(name='member_project_check')
def member_project_check(member, project):
    name = member.member_name
    return project.project_members.filter(member_name__contains=name).exists()