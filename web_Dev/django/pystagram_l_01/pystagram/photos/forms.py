from django import forms

from .models import Post
from .models import Comment

from django.forms import ValidationError


class CommentForm(forms.ModelForm):
	class Meta:
		model = Comment
		fields = ('content',)
#
class SimpleForm(forms.Form):
	title = forms.CharField(min_length=3,max_length=10)
	content = forms.CharField(widget=forms.Textarea)

#import sys
#reload(sys)
#sys.setdefaultencoding('utf-8')

# Model Forme
class PostForm(forms.ModelForm):
	#content = forms.IntegerField()
	#tags= forms.CharField(required=False)# model blank=True
	tagtext = forms.CharField()
	class Meta:
		model = Post
		fields = ('category','image','content','tags',)
		#field = __all__
	def clean_content(self):
		content = self.cleaned_data['content']
		if 'fool' in content:
			raise ValidationError('There is invalid input.')
		return content
