# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-11-27 15:31
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Contact', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='post',
            name='title',
            field=models.CharField(default='no title', max_length=100),
        ),
    ]
