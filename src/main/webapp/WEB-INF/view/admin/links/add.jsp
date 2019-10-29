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

	<div class="container">
	
		<form action="" id="spform">
			<table class="table table-sm  table-hover table-bordered ">
					<tr>
						<td>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" name="title"/> </td>
					</tr>
					<tr>
						<td>链接地址：<input name="url" value="http://" id="url"/></td>
					</tr>
			</table>
					<input type="button" value="提交" onclick="addata()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="返回" onclick="goback()">
		</form>
		
	</div>

</body>
<script type="text/javascript">

		function addata(){
			$.post("/links/add",$("#spform").serialize(),function(msg){
				if(msg.result==1){
					alert("处理成功")
					$("#content-wrapper").load("/links/friendLink")
				}else{
					alert(msg.errorMsg);
				}
			},"json")
		}
		
		function goback() {
			$("#content-wrapper").load("/links/friendLink")
		}
		
</script>
</html>