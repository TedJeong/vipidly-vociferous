from django import forms

from .models import Image

class image_upload_form(forms.Form):
    image_name = forms.CharField(widget=forms.TextInput())
    image_type = forms.CharField()

class image_upload_model_form(forms.ModelForm):
    class Meta:
        model = Image
        fields = '__all__'

class video_upload_form(forms.Form):
    video_name = forms.CharField(widget=forms.TextInput())
    video_type = forms.CharField()
