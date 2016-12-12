from __future__ import unicode_literals
from django.db import models


class Progress(models.Model):
    progress_name = models.CharField(max_length=100, default="progress_")
    progress_project = models.ForeignKey('Project')
    progress_task = models.OneToOneField('Task')

    class Meta:
        pass


class Task(models.Model):
    task_name = models.CharField(max_length=100, default="task_")
    task_project = models.ForeignKey('Project')
    task_members = models.ManyToManyField('Member')


class Member(models.Model):
    member_name = models.CharField(max_length=100, default="member_")
    member_department = models.CharField(max_length=100)


class Project(models.Model):
    project_name = models.CharField(max_length=100, default="project_")
    project_members = models.ManyToManyField('Member')
    project_workspaces = models.ManyToManyField('Workspace')
    project_controller = models.ForeignKey('Controller')
    project_category = models.ForeignKey('Category')
    project_start_date = models.DateTimeField(auto_now=True)

    def __str__(self):
        return 'project - {}:{}'.format(self.project_name, self.project_start_date)

    def get_absolute_url(self):
        pass

    class Meta:
        pass


class Category(models.Model):
    category_name = models.CharField(max_length=100, default="category_")

    def __str__(self):
        return 'category - {}'.format(self.category_name)


class Workspace(models.Model):
    workspace_name = models.CharField(max_length=100, default="workspace_")
    workspace_controller = models.ForeignKey('Controller')

    def __str__(self):
        return 'workspace - {}'.format(self.workspace_name)


class Controller(models.Model):
    controller_name = models.CharField(max_length=100, default="controller_")

    def __str__(self):
        return '{}'.format(self.controller_name)