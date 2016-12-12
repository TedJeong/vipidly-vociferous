import time
import os

from PIL import Image
from celery import Celery

app = Celery(
    'taskqueue', #__filename.py__
    broker='redis://localhost:6379/0',
    #broker='redis://192.168.0.115:6379/3'
    backend='redis://localhost:6379/0',
)


# Celery에 worker로 등록
# 추가시 celery server 종료하고 다시시작
@app.task
def add(a, b):
    time.sleep(5)
    return a+b


@app.task
def sum2(values):
    #assert isinstance(values, [list, tuple])
    time.sleep(5)
    return sum(values)


@app.task
def make_thumbnail(path, width, height):
    # file 이름과 경로, 확장자 분리
    # ('filepath','.png')
    filepath, ext = os.path.splitext(path)
    output_path = '{}_thumb{}'.format(filepath, ext)

    if os.path.exists(output_path):
        return output_path

    im = Image.open(path)
    # size, filter(anti aliasing)
    im.thumbnail([width, height], Image.LANCZOS)
    im.save(output_path)
    im.close()

    # 객체가 __enter__,__exit__  이라는 method가 있을때 with문 사용가능
    """
    with Image.open(path) as im:
        im.thumbnail([width,height], Image.LANCZOS)
        im.save(output_path)
    """
    return output_path