from __future__ import unicode_literals
from django.db import models

# Progress is determined when unitest problem is passed.
# each problem is assigned a value and it is summed up to progress.
class Progress(models.Model):
    progress_name = models.CharField(max_length=100, default="progress_")
    progress_project = models.ForeignKey('Project')
    # progress_task can be assigned to Project itself, or Task under project
    progress_task = models.OneToOneField('Task', null=True, blank=True)
    # only takes 99.99?
    progress_gauge = models.DecimalField(default=0.00, decimal_places=2, max_digits=4)

    def __str__(self):
        return '{}-{}%'.format(self.progress_name, str(self.progress_gauge))
    class Meta:
        pass


class Task(models.Model):
    task_status = models.BooleanField(default=False)
    task_priority = models.IntegerField(default=1)
    task_related = models.ManyToManyField('self', null=True, blank=True)
    task_name = models.CharField(max_length=100, default="task_")
    task_description = models.CharField(max_length=300, null=True, blank=True)
    task_project = models.ForeignKey('Project')
    task_submitter = models.CharField(max_length=100, default="Anonymous", null=True, blank=True)
    task_members = models.ManyToManyField('Member', null=True, blank=True)
    task_initiated_at = models.DateTimeField(auto_now=True)
    task_deadline = models.DateTimeField(null=True, blank=True)
    task_completed_at = models.DateTimeField(null=True, blank=True)

    def __str__(self):
        return '{}-{} : {}'.format(self.task_name, self.task_project, self.task_members)


class Member(models.Model):
    member_name = models.CharField(max_length=100, default="member_")
    member_department = models.CharField(max_length=100 ,
                                         choices=((u'project manager',u'project manager'),
                                                  (u'team manager',u'team manager'),
                                                  (u'freelancer',u'freelancer'),
                                                  (u'backend programmer', u'backend programmer'),
                                                  (u'frontend programmer', u'frontend programmer')),
                                         default="undesignated")
    def __str__(self):
        return '{}-{}'.format(self.member_name, self.member_department)


class Project(models.Model):
    project_name = models.CharField(max_length=100, default="project_")
    project_members = models.ManyToManyField('Member')
    project_workspaces = models.ManyToManyField('Workspace')
    project_controller = models.ForeignKey('Controller')
    project_category = models.ForeignKey('Category')

    project_start_date = models.DateTimeField(auto_now=True)
    project_end_date = models.DateTimeField(null=True, blank=True)
    project_deadline = models.DateTimeField(null=True, blank=True)

    project_description = models.CharField(max_length=300, null=True,
                                           default="test project_", blank=True)

    def __str__(self):
        return '{} - {} name: {} at {}'.format(
            self.project_controller,
            [workspace.workspace_name for workspace in self.project_workspaces.all()],
            self.project_name,
            self.project_start_date)

    def get_absolute_url(self):
        pass

    def describe(self):
        return '{}'.format(self.workspace_description)

    class Meta:
        pass


class Category(models.Model):
    category_parent = models.ForeignKey('self', null=True, blank=True)
    category_name = models.CharField(max_length=100, default="category_")

    def __str__(self):
        return 'category - {}'.format(self.category_name)


class Workspace(models.Model):
    workspace_name = models.CharField(max_length=100, default="workspace_")
    workspace_controller = models.ForeignKey('Controller')
    workspace_description = models.CharField(max_length=300, default="test workspace_",
                                             null="True", blank=True)
    def __str__(self):
        return 'workspace - {}'.format(self.workspace_name)

    def describe(self):
        return '{}'.format(self.workspace_description)


class Controller(models.Model):
    controller_name = models.CharField(max_length=100, default="controller_")
    controller_description = models.CharField(max_length=300, default="test workspace_")

    def __str__(self):
        return '{}'.format(self.controller_name)

    def describe(self):
        return '{}'.format(self.controller_description)