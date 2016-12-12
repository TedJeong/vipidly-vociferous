# 바로 from . import signals 하면 안된다
# 순환참조가 되어버린다 Model 준비가 끝나지 않아서
# 일종의 지연평가
default_app_config = 'photos.apps.PhotosConfig'