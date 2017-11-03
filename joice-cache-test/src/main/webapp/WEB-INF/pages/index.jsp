<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/plugins/bootstrap-3.3.5/css/bootstrap.css">
<title>首页</title>
</head>
<body>
	<div>
		<h3>通过链接访问示例</h3>
		<ul>
			<li><h4>
					<a href="${pageContext.request.contextPath}/user/list"
						target="_blank">列表&#38;分页</a>&nbsp;<small>通过设置pageSize的值调整每页个数</small>
				</h4></li>
		</ul>
	</div>
</body>
</html>