# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-12-04 09:38
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Contact', '0002_post_title'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='image',
            field=models.ImageField(blank=True, null=True, upload_to='%Y/%m/%d/'),
        ),
    ]
