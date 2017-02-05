from django.db import models

from django.conf import settings

from django.contrib.auth.models import User

class Analyzer_core(models.Model):
    analyzer_name = models.CharField(max_length=50)

    class Meta:
        ordering=('-pk',)


class Job(models.Model):
    job_status = models.CharField(max_length=30,
                                   choices=((u'pending', u'pending'),
                                            (u'running', u'running'),
                                            (u'finished', u'finished'),
                                            )
                                   )
    job_priority = models.IntegerField(default=1)
    job_name = models.CharField(max_length=50)
    job_dataset_size = models.IntegerField(default=0) ## TODO: will be changed
    job_started_at = models.DateTimeField(auto_now=True)
    job_deadline = models.DateTimeField(null=True, blank=True)
    job_completed_at = models.DateTimeField(null=True, blank=True)

    class Meta:
        ordering = ('-pk',)

    def __str__(self):
        return '{}-{} : {}'.format(self.job_name, self.job_submitter, self.job_status)


class Controller(models.Model):
    controller_name = models.CharField(max_length=40, blank=True)
    controller_problem_settings = models.CharField(max_length=40,
                                                  default='classification',
                                                  choices=(
                                                      (u'classification', u'classification'),
                                                      (u'regression',u'regression'),
                                                      (u'clustering', u'clustering'),
                                                    ),
                                                  )
    controller_core_packages = models.CharField(max_length=50,
                                                default='scikit-learn',
                                                choices=(
                                                    (u'sklearn', u'sklearn'),
                                                    (u'lasagne', u'lasagne'),
                                                    (u'keras', u'keras'),
                                                    (u'theano', u'theano'),
                                                    (u'tensorflow', u'tensorflow'),
                                                    (u'torch', u'torch'),
                                                    (u'caffe', u'caffe'),
                                                ),
                                                blank=True,
                                                )
    controller_core_estimator = models.CharField(max_length=50,
                                                 default='LogisticRegression',
                                                 choices=(
                                                     (u'LogisticRegression', u'LogisticRegression'),
                                                     (u'SVM', u'SVM'),
                                                     (u'XgBoost', u'XgBoost'),
                                                     (u'AlexNet', u'AlexNet'),
                                                     (u'GoogleNet', u'GoogleNet'),
                                                     (u'ResNet', u'ResNet'),
                                                          ),
                                                 blank=True,)
    controller_core_parameters = models.CharField(max_length=100, blank=True,)
    controller_visual_tool = models.CharField(max_length=100,
                                              default='scatter',
                                              choices=(
                                                  (u'scatter', u'scatter'),
                                                       )
                                              ,)
    controller_input_file_field = models.FileField(null=True, blank=True)
    controller_created_at = models.DateTimeField(auto_now=True,)

    class Meta:
        ordering=('-pk',)


class PreProcessor(models.Model):
    preprocessor_name = models.CharField(max_length=40, default='test_preprocess')


class PostProcessor(models.Model):
    postprocessor_name = models.CharField(max_length=40, default='test_postprocess')


class Datafile(models.Model):
    filename = models.CharField(max_length=100)
    filepath = models.FilePathField()
    fileowner = models.ForeignKey(settings.AUTH_USER_MODEL)


class Image(models.Model):
    image_name = models.CharField(max_length=100)
    image_file = models.ImageField(upload_to="images/")
    image_owner = models.ForeignKey(settings.AUTH_USER_MODEL)

    def __str__(self):
        return '{}'.format(self.image_name)


class Video(models.Model):
    video_name = models.CharField(max_length=100)
    video_type = models.CharField(max_length=50)
    video_file = models.FileField(upload_to="videos/")
    video_owner = models.ForeignKey(settings.AUTH_USER_MODEL)

    class Meta:
        pass