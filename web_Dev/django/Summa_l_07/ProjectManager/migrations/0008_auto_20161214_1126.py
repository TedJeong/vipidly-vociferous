# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-12-14 02:26
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('ProjectManager', '0007_auto_20161214_1105'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='task',
            name='task_parent',
        ),
        migrations.AddField(
            model_name='task',
            name='task_related',
            field=models.ManyToManyField(blank=True, null=True, related_name='_task_task_related_+', to='ProjectManager.Task'),
        ),
    ]
