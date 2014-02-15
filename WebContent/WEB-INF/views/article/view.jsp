<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/WEB-INF/views/commons/head-meta.jsp"%>
<title>Insert title here</title>
</head>
<body>

	id: ${article.id}
	<br/>
	title: ${article.title}
	<br/>
	content: ${article.content}
	<br/>
	userId: ${article.userId}
	<br/>
	user.Name: ${article.user.name}
	<br/>
	user.address : ${article.user.address}
	

</body>
</html>