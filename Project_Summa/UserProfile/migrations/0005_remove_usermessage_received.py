# -*- coding: utf-8 -*-
# Generated by Django 1.10.4 on 2017-02-06 15:50
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('UserProfile', '0004_usermessage'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='usermessage',
            name='received',
        ),
    ]
