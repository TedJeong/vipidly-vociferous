from django.contrib import admin
from .models import Post, Comment

# Register your models here.

class CommentInlineAdmin(admin.StackedInline):#admin.TabularInline
	model = Comment
	extra = 3

class PostAdmin(admin.ModelAdmin):
	list_display = ('id','category','created_at',)
	list_display_links = ('id','created_at',)
	ordering = ('id','-created_at',)
	inlines = (CommentInlineAdmin,)
	search_fields = ('id','content',)
	list_filter = ('category','tags',)
	date_hierarcy = 'created_at' # ImproperlyConfigured pytz module required

admin.site.register(Post,PostAdmin)
