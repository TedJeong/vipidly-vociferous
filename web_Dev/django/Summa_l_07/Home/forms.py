from django.contrib.auth import get_user_model
from django import forms

class LoginForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    class Meta:
        model = get_user_model()
        fields = ['username','password']



class SigninForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    class Meta:
        model = get_user_model()
        fields = ['username', 'email', 'password']