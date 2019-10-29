<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
</head>
<body>

	<form action="photo" id="form" method="post" enctype="multipart/form-data">
		<div class="container-fluid">
			<label for="title">上传头像</label> <input type="file" class="form-control" id="file" name="file">
		</div>
		<br>
		<div align="center">
			<input type="submit" value="上传"/>
		</div>
	</form>
		

</body>
<script type="text/javascript">

/* 	function photo() {
		$.ajax({
			type:"post",
			data:formData,
			url:"/user/photo",
			success:function(obj){
				if(obj){
					alert("发布成功")
				}else{
					alert("发布失败")
				}
				
			}
			
		})
	} */

</script>
</html>