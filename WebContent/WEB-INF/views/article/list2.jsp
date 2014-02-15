<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/WEB-INF/views/commons/head-meta.jsp"%>
<title>Insert title here</title>
</head>
<body>
	========================当前页数据：==========================
	<c:forEach var="article" items="${list}">
		<br/>
			题目: ${article.title}   ---   作者: ${article.user_name}
	</c:forEach>

</body>
</html>