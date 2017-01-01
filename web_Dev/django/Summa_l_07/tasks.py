#from celery.task import task
import time
import os

from PIL import Image
from celery import Celery

app = Celery(
    'tasks', #__filename.py__
    broker = "amqp://guest:guest@localhost:5672/",
    #broker='redis://localhost:6379/0',
    #broker='redis://192.168.0.115:6379/3'
    #backend='redis://localhost:6379/0',
    backend='amqp://localhost:5672/',
)


import time
@app.task
def add(x,y):
    time.sleep(10)
    return x+y