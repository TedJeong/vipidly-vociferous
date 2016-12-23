from django.db import models

class analyzer_core():
    analyzer_name = models.CharField(max_length=50)

    class Meta:
        ordering=('-pk')