from django.contrib import admin

from .models import Progress
from .models import Task
from .models import Project
from .models import Category
from .models import Workspace
from .models import Controller


class ProgressAdmin(admin.ModelAdmin):
    list_display = ('progress_name', 'progress_project', 'progress_task', )
    search_fields = ('progress_name', 'progress_project', 'progress_task', )


admin.site.register(Progress, ProgressAdmin)
admin.site.register(Task)
admin.site.register(Project)
admin.site.register(Category)
admin.site.register(Workspace)
admin.site.register(Controller)


