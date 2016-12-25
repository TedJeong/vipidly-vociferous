from django.db import models

class Analyzer_core(models.Model):
    analyzer_name = models.CharField(max_length=50)

    class Meta:
        ordering=('-pk',)


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