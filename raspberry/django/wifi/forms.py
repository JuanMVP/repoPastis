from django import forms

from .models import Wifi

from .utils import *


class WifiConfigurerForm(forms.Form):


    ssid = forms.ChoiceField(choices=choice_wifi())
    passwd = forms.CharField(max_length=32, widget=forms.PasswordInput)
