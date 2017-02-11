from django.forms import ModelForm

from .models import UserProfile

class UpdateUserProfileForm(ModelForm):
    class Meta:
        model = UserProfile
        fields = '__all__'
