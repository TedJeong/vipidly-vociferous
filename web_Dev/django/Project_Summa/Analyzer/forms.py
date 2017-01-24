from django import forms


from .models import Controller
from .models import Image

class ControllerForm(forms.ModelForm):
    class Meta:
        model = Controller
        fields = '__all__'


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

class FileFieldForm(forms.Form):
    file_field = forms.FileField(widget=forms.ClearableFileInput(attrs={'multiple':True}))

