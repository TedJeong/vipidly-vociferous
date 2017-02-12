from django import template
from ProjectManager.models import Progress

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