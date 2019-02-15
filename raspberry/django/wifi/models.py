from django.db import models

# Create your models here.

from django.db import models
from django.utils import timezone

class Wifi(models.Model):
    ssid = models.CharField(max_length=50)
    password = models.CharField(max_length=30)


    def __str__(self):
        return self.ssid
