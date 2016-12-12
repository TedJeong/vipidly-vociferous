<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page isErrorPage="true" %>
<% response.setStatus(200);  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<img src="img/systemicons/oo.png" class="img-responsive" alt="">
 <h3>에러가 발생했어요. 조속히 고치도록 하겠습니다.</h3>
<body>
<%=exception.getMessage() //can be used for declaration isErrorPage="true"
%>
</body>
</html>