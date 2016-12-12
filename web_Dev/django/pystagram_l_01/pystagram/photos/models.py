from __future__ import unicode_literals

from django.db import models

from django.urls import reverse_lazy

from django.conf import settings

from django.contrib.contenttypes.fields import GenericForeignKey
from django.contrib.contenttypes.fields import GenericRelation
from django.contrib.contenttypes.models import ContentType

from django.dispatch import receiver
from django.db.models.signals import post_delete


class LikePol(models.Model):
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE)
    object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('content_type','object_id')


class Category(models.Model):
    name = models.CharField(max_length=40)
    parent = models.ForeignKey('self', null=True)

    def __str__(self):
        return '{}'.format(self.name)


class Post(models.Model):
    likes = GenericRelation(LikePol)
    user = models.ForeignKey(settings.AUTH_USER_MODEL)
    # user = models.ForeignKey('auth.user')
    category = models.ForeignKey(Category)
    content = models.TextField(null=False, blank=False)
    image = models.ImageField(upload_to='%Y/%m/%d/', null=True, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return '{}'.format(self.pk)

    tags = models.ManyToManyField('Tag', blank=True)

    """
	class Meta:
		ordering = ('-created_at','-id')
	"""

    def get_absolute_url(self):
        return reverse_lazy('photos:view', kwargs={'pk': self.pk})

class Comment(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL)
    post = models.ForeignKey(Post)
    content = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    likes = GenericRelation(LikePol)
    class Meta:
        ordering = ['-created_at']


# Create your models here.
class Tag(models.Model):
    name = models.CharField(max_length=40)

    def __str__(self):
        return '{}'.format(self.name)

class Like(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL)
    post = models.ForeignKey(Post)
    created_at = models.DateTimeField(auto_now_add=True)

""" moved to photos.signals.py model이 너무 길어지는 것을 방지하기 위해서
# 분리시 apps.py 에 등록을 해준다.
# 첫번째 방법
@receiver(post_delete, sender=Post)
# Post 모델에서 post_delete 를 사용 Post class name은 우연히 같은것
def delete_attachmented_image(sender, instance, **kwargs):
    print('-'*40)
    print('called signal error')
    print('-' * 40)
    if not instance.image: # early return
        return
    instance.image.delete()
    #instance.image.delete(save=False)
    # admin에서 파일만 지워지고 post는 남아있는 상황이 생긴다
    # file 은 지우지만 DB save 하지마라
# 두번째 방법
# 코드 복붙시 실수할 여지가 있다
#post_delete.connect(delete_attachmented_image, sender=Post)

#model class 단에서 delete method 자체를 구현해서 delete 할 수도 있지만,
# queryset을 이용해서 지웠을 경우에는 호출 되지 않기 때문에, 비추천.
"""