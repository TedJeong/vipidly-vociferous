# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-12-04 09:38
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Video',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('video_name', models.CharField(max_length=100)),
                ('video_type', models.CharField(max_length=50)),
                ('video_file', models.FileField(upload_to='videos/')),
            ],
        ),
    ]
