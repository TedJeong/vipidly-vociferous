from django.contrib.auth import get_user_model
from django import forms


class LoginForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)

    class Meta:
        model = get_user_model()

        fields = ['username','password']

        # widgets = {
        #     'username': forms.Textarea(attrs={'cols': 80, 'rows': 20}),
        # }
        labels = {
            'username': (u'아이디 '),
            'password': (u'비밀번호 '),
        }
        help_texts = {
            'username': (''),
        }
        error_messages = {
            'username': {
                'max_length': ("아이디 길이는 50자 이하 입니다."),
            },
        }


class SigninForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    class Meta:
        model = get_user_model()
        fields = ['username', 'email', 'password']