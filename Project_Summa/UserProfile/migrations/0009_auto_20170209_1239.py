# -*- coding: utf-8 -*-
# Generated by Django 1.10.4 on 2017-02-09 12:39
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('UserProfile', '0008_auto_20170208_1350'),
    ]

    operations = [
        migrations.RenameField(
            model_name='userprofile',
            old_name='profile_photo_path',
            new_name='profile_photo',
        ),
    ]
