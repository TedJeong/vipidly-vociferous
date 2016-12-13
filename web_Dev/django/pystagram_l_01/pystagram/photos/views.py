from django.shortcuts import render
from django.shortcuts import redirect
from django.shortcuts import get_object_or_404
from .models import Post
from .models import Tag
from .models import Comment
from .models import Like

from django.core.paginator import Paginator
from django.core.paginator import EmptyPage
from django.core.paginator import PageNotAnInteger

from django.http import HttpResponseBadRequest
from django.contrib.auth.decorators import login_required

from .forms import PostForm
from .forms import CommentForm

import logging

from .sample_exceptions import HelloWorldError

from django.views.generic import ListView
from django.views.generic import CreateView
from django.views.generic import UpdateView
from django.views.generic import DeleteView

from django.http import HttpResponse

from django.core import serializers
from rest_framework import serializers as drf_serializers
from rest_framework import viewsets

from django.views.decorators.cache import cache_page

from django.core.cache import cache



logger = logging.getLogger('django')

@login_required
def like_post(request, post_pk):
	post = get_object_or_404(Post, pk=post_pk)
	if request.method == 'POST':
		raise Exception('bad request')
		qs = post.like_set.filter(user=request.user)
		if qs.exists():
			like = qs.get()
			like.delete()
		else:
			like = Like()
			like.post = post
			like.user = request.user
			like.save()
		like, is_created = Like.objects.get_or_create(post=post,\
													  user=request.user,\
													  defaults={'post':post, 'user':request.user})
	return redirect(post)

@login_required
def create_post(request):
#	if not request.user.is_authenticated():
#		raise Exception('누구 세요?')

	if request.method == "GET":
		form = PostForm()
	elif request.method == "POST":
		form = PostForm(request.POST, request.FILES)
		if form.is_valid():
			# to avoid integrity error put commit=False
			post = form.save(commit=False)
			post.user = request.user
			post.save()

			#tag_text = form.cleaned_data.get('tags','')
			tag_text = form.cleaned_data.get('tagtext','')
			tags = tag_text.split(',')
			for _tag in tags:
				_tag = _tag.strip()
				tag,_ = Tag.objects.get_or_create(name=_tag,defaults={'name':_tag})
				post.tags.add(tag)
				# rollback default false, during add  error
			return redirect('photos:view',pk=post.pk) 
	ctx={
		'form':form,
	}
	return render(request,'edit_post.html',ctx)


# Create your views here.
def list_posts(request):
	#raise HelloWorldError('Problem')
	#logger.warning('warning warning')
	per_page=2
	page = request.GET.get('page',1)
	posts = Post.objects.all().order_by('-created_at','-pk')
	pg = Paginator(posts,per_page)
	
	try:	
		contents = pg.page(page)
	except PageNotAnInteger:
		contents = pg.page(1)
	except EmptyPage:
		contents=[]

	# Http request is ajax - > serialize
	if request.is_ajax():
		data = serializers.serialize('json', contents)
		return HttpResponse(data)
	ctx = {
		'posts' : contents,
	}
	return render(request,'list.html',ctx)


"""
class PostListView(ListView):
	model = Post
	context_object_name = 'posts'
	template_name = 'list.html'
	paginate_by = 2
#	queryset = Post.objects.order_by('created_at')
	def get_queryset(self):# Usage: certain data pick
		return Post.objects.order_by('created_at')
list_posts = PostListView.as_view()

class PostCreateView(CreateView):
	model = Post
	form_class = PostForm
	template_name = 'edit_post.html'
	success_url = 'list.html'
	def get_success_url(self): #or create Post model get_absolute_url()
		return 'list.html'
create_post = PostCreateView.as_view()
"""

# view 와 comment 둘 다 로그인 기반으로 할 때만 login_required 붙여준다.

@login_required
#@cache_page(60*4) # test1 순서 반대로 쓰면 로그인 안됐다고 계속 나올 수 있다. 단위는 초.
def view_post(request,pk):
	key = 'post_object_{}'.format(pk)
	post = cache.get(key)
	# test2
	if not post:
		post = Post.objects.get(pk=pk)
		cache.set(key, post, 300)
		print('get data')
	else:
		print('get cached data')
	# test2 end

	if request.method == 'GET':
		form = CommentForm(request.GET)
	elif request.method == 'POST':
		form = CommentForm(request.POST)

		if form.is_valid():
			comment = form.save(commit=False)
			comment.user = request.user
			comment.post = post
			comment.save()
			return redirect(post)
			# return redirect('photos:view', pk=post.pk)
			# get_absolute_url call inside Post

#	form = CommentForm()
	ctx = {
		'post' : post,
		'comment_form' : form,
	}
	return render(request,'view.html',ctx)

"""
def list_postss(request):
	# string as value
	# string to int
	# page error
	try:
		page = int(request.GET.get('page',1))
	except Exception:
		page = 1
	finally:
		if page < 0:
			page = 1

	per_page = 2
	start_page = (page-1)*per_page
	end_page = page*per_page
	posts = Post.objects.all().order_by('-created_at','-pk')
	#posts = Post.objects.all().order_by('-created_at','-pk')[start_page:end_page]

	pg = Paginator(posts,per_page)
	# empty page error
	try:	
		contents = pg.page(page)
	except EmptyPage:
		contents=[]

	ctx = {
#		'posts' : posts,
		'posts' : contents,
	}
	return render(request,'list.html',ctx)

"""

def delete_comment(request,pk):
	if request.method != "POST":
		raise HttpResponseBadRequest
#		raise Exception('bad request')
	comment = get_object_or_404(Comment, pk=pk)
	comment.delete()
	return redirect(comment.post)


import base64
def get_base64_image(data):
	if data is None or ';base64' not in data:
		return None

	_format, _content = data.split(';base64,')
	return base64.b64decode(_content)

from django.core.files.uploadedfile import SimpleUploadedFile



class PostSerializer(drf_serializers.ModelSerializer):
	class Meta:
		model = Post
		fields = ['category', 'content', ]


class PostViewSet(viewsets.ModelViewSet):
	queryset = Post.objects.all()
	serializer_class = PostSerializer