# 게시물 객체, 어떤 user가 좋아요 했는지 객체

from django import template
from django.template.base import VariableNode
from django.contrib.auth import get_user_model

register = template.Library()

@register.tag(name='addnim')
def add_nim(parser, token):
    nodelist = parser.parse(('end_add_nim', 'endaddnim'))
    parser.delete_first_token()
    return NimNode(nodelist)


class NimNode(template.Node):
    def __init__(self, nodelist):
        self.nodelist = nodelist
        self.user_class = get_user_model()

    def render(self, context):# context 는 render 가 기본적으로 받음
        outputs = []
        #outputs.append('hello')
        #outputs.append('world')
        for node in self.nodelist:
            # Node 가 variable Node 이고, Node 객체가 User Model class 인것을
            # 찾아서 '님' 자를 붙여준다.
            if not isinstance(node, VariableNode):
                outputs.append(node.render(context))
                continue
            # node에서 python 객체 자체를 가지고 온다.
            obj = node.filter_expression.resolve(context)
            if not isinstance(obj, self.user_class):
                outputs.append(node.render(context))
                continue

            text = '{}님'.format(node.render(context))
            outputs.append(text)

        return ''.join(outputs)


# template 에서 filter 사용할 수 있도록
# 교재는 ManyToMany 라 조금 다르다
@register.filter(name='did_like')
def did_like(post, user):
    return post.like_set.filter(user=user).exists()


@register.simple_tag
def helloworld(*args, **kwargs):
    return '<p>위치 인자 : {}</p>,<p> 키워드 인자 : {}</p>'.format(args, kwargs)