<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请用户登录</title>
</head>
<body>
	<h2>欢迎登录</h2>
	<form action="<c:url value='/doLogin'/>" method="post">
		<label>用户名：</label>
		<input type="text" name="id"><br>
		<label>密码：</label>
		<input type="password" name="password" ><br>
		<input type="submit" value="登录">
	</form>
</body>
</html>