# -*- coding: utf-8 -*-
# Generated by Django 1.10.4 on 2017-01-23 14:19
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Category',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('category_name', models.CharField(default='category_', max_length=100)),
                ('category_parent', models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Category')),
            ],
        ),
        migrations.CreateModel(
            name='Controller',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('controller_name', models.CharField(default='controller_', max_length=100)),
                ('controller_description', models.CharField(default='test workspace_', max_length=300)),
            ],
        ),
        migrations.CreateModel(
            name='Member',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('member_name', models.CharField(default='member_', max_length=100)),
                ('member_department', models.CharField(choices=[('project manager', 'project manager'), ('team manager', 'team manager'), ('freelancer', 'freelancer'), ('backend programmer', 'backend programmer'), ('frontend programmer', 'frontend programmer')], default='undesignated', max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Progress',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('progress_name', models.CharField(default='progress_', max_length=100)),
                ('progress_gauge', models.DecimalField(decimal_places=2, default=0.0, max_digits=4)),
            ],
        ),
        migrations.CreateModel(
            name='Project',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('project_name', models.CharField(default='project_', max_length=100)),
                ('project_start_date', models.DateTimeField(auto_now=True)),
                ('project_end_date', models.DateTimeField(blank=True, null=True)),
                ('project_deadline', models.DateTimeField(blank=True, null=True)),
                ('project_description', models.CharField(blank=True, default='test project_', max_length=300, null=True)),
                ('project_category', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Category')),
                ('project_controller', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Controller')),
                ('project_members', models.ManyToManyField(to='ProjectManager.Member')),
            ],
        ),
        migrations.CreateModel(
            name='Task',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('task_status', models.BooleanField(default=False)),
                ('task_priority', models.IntegerField(default=1)),
                ('task_name', models.CharField(default='task_', max_length=100)),
                ('task_description', models.CharField(blank=True, max_length=300, null=True)),
                ('task_submitter', models.CharField(blank=True, default='Anonymous', max_length=100, null=True)),
                ('task_initiated_at', models.DateTimeField(auto_now=True)),
                ('task_deadline', models.DateTimeField(blank=True, null=True)),
                ('task_completed_at', models.DateTimeField(blank=True, null=True)),
                ('task_members', models.ManyToManyField(blank=True, null=True, to='ProjectManager.Member')),
                ('task_project', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Project')),
                ('task_related', models.ManyToManyField(blank=True, null=True, related_name='_task_task_related_+', to='ProjectManager.Task')),
            ],
        ),
        migrations.CreateModel(
            name='Workspace',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('workspace_name', models.CharField(default='workspace_', max_length=100)),
                ('workspace_description', models.CharField(blank=True, default='test workspace_', max_length=300, null='True')),
                ('workspace_controller', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Controller')),
            ],
        ),
        migrations.AddField(
            model_name='project',
            name='project_workspaces',
            field=models.ManyToManyField(to='ProjectManager.Workspace'),
        ),
        migrations.AddField(
            model_name='progress',
            name='progress_project',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Project'),
        ),
        migrations.AddField(
            model_name='progress',
            name='progress_task',
            field=models.OneToOneField(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='ProjectManager.Task'),
        ),
    ]
