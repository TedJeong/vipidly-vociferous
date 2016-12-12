from .models import Post
from django.dispatch import receiver
from django.db.models.signals import post_delete

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
# queryset을 이용해서 지웠을 경우에는 호출 되지 않기 때문에, 비추천.s