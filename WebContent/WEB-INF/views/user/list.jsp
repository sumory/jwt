<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/WEB-INF/views/commons/head-meta.jsp"%>
<title>Insert title here</title>
</head>
<body>

	用户总数: ${page.total}
	<br/>
	当前页数： ${page.pageNo}
	<br/>
	每页大小： ${page.pageSize}
	<br/>
	========================当前页数据：==========================
	<c:forEach var="user" items="${page.list}">
		<br/>
		用户ID:${user.id}   ---  用户名称:${user.name}
	</c:forEach>

</body>
</html>