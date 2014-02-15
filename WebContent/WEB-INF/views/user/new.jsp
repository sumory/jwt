<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/WEB-INF/views/commons/head-meta.jsp"%>
<title>Insert title here</title>
</head>
<body>
	<form action="${ctx}/user/new" method="post"> 
		姓名：<input type="text"  name="name" value=""/><br/>
		地址：<input type="text"  name="address" value=""/><br/>
		<input type="submit" value="提交"/>
	</form>

</body>
</html>