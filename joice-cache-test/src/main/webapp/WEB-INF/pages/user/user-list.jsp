<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/plugins/bootstrap-3.3.5/css/bootstrap.css">

<title>用户列表</title>
</head>
<body>
	<div>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Type</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userPage }" var="user">
					<tr>
						<td>${user.id }</td>
						<td>${user.name }</td>
						<td>${user.type }</td>
						<td>${user.status }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<c:choose>
				<c:when test="${pageNo>0 }">
					<a
						href="${pageContext.request.contextPath}/user/list?pageNo=${pageNo-1}&pageSize=${pageSize}">
						上一页</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void(0);">上一页</a>
				</c:otherwise>
			</c:choose>
			<span>第${pageNo+1 }/${pageCount }页</span>
			<c:choose>
				<c:when test="${pageNo<pageCount-1 }">
					<a
						href="${pageContext.request.contextPath}/user/list?pageNo=${pageNo+1}&pageSize=${pageSize}">
						下一页 </a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void(0);">下一页</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>