<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>对不起，您访问的出现错误</title>
<%@include file="/WEB-INF/views/commons/head-meta.jsp"%>
</head>

 <body>
	<div>
		<div>
			<div>
				<p>${errorMsg}</p>
				<div>您可以：</div>
				<ol>
					<li>检查刚才的输入: <a href="javascript:history.back();">返回上一页</a></li>
					<li>去其它地方逛逛: <a href="${ctx}" title="首页">首页</a> </li>
				</ol>
			</div>
      	</div>
		
  	</div> 

</body>

</html>
