import sys
reload(sys)
sys.setdefaultencoding('utf-8')


from photos.forms import SimpleForm
data = {}
data['title'] = '안녕하세요'
data['content'] = '글 본문입니다.'

data2={'title':'..','content':'hello'}
f3=SimpleForm(data2)
f3.is_valid()

data5={'title'}
f5=SimpleForm(data5,initial={'title':'defaulttitle'})

data6={'title':'              타이틀입니다','content':'yes!'}
f6=SimpleForm(data)
f6.cleaned_data # 문자열 앞뒤 trim, image meta 정보에서 plain text 제거 해주는 정도, widget이 들고 있는것이 아니라 CharField가 cleaned_data가 해주는 것
data6

# email, model 에서도 필드도 full_clean이 있긴하지만 form 에서 대체로 한다


f3.errors
f3.errors['title']
f3['title'].errors
