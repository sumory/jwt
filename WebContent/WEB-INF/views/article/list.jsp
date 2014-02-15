<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/WEB-INF/views/commons/head-meta.jsp"%>
<title>Insert title here</title>
</head>
<body>

	文章总数: ${page.total}
	<br/>
	文章页数： ${page.pageNo}
	<br/>
	每页大小： ${page.pageSize}
	<br/>
	========================当前页数据：==========================
	<c:forEach var="article" items="${page.list}">
		<br/>
			题目: ${article.title}   ---   作者: ${article.userName}
	</c:forEach>

</body>
</html>