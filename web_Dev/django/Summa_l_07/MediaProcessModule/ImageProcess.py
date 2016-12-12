import base64

def get_base64_image(data):
    if data is None or ';base64' not in data:
        return None

    _format, _content = data.split(';base64,')
    return base64.b64decode(_content)
