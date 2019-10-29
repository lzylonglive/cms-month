<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/resource/js/cms.js"></script>
</head>
<body>

	<div class="container-fluid">
		
		<div>
		     链接管理/链接管理
		<br/>
		<br/>
		<button type="button" class="btn btn-info" onclick="add()">添加链接</button>
		</div>

		<table class="table table-sm  table-hover table-bordered ">
			<thead class="thead-light">
				<tr align="center">
					<td>序号</td>
					<td>名称</td>
					<td>链接地址</td>
					<td>创建日期</td>
					<td>操作</td>
				</tr>
			</thead>
			<c:forEach items="${list}" var="l" varStatus="index">
				<tr align="center">
					<td>${index.index+1 }</td>
					<td>${l.title}</td>
					<td>${l.url}</td>
					<%-- <td>${u.birthday}</td> --%>
					<td>
					 	<fmt:formatDate  value="${l.created}" pattern="yyyy-MM-dd HH:mm"/>
					 	<%-- <c:if test="${u.createTime!=null}"></c:if> --%>
					 </td>
					 <td>
					 
					 	<input type="button" value="移除" onclick="remove(${l.id})"/>
					 
					 </td>
				</tr>

			</c:forEach>

		</table>
		<div>
		    ${pageUtil}
		</div>
	</div>

</body>
<script type="text/javascript">

	function add(){
		$("#content-wrapper").load("/links/add");
	}
	
	function remove(id) {
		if(confirm("您确认要删除吗？")) {
			$("#content-wrapper").load("/links/remove?id="+id);
		}
	}

</script>
</html>