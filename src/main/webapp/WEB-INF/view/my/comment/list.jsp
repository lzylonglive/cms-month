<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">

	function myopen(id){
		// alert(id)
		window.open("/article/getDetail?aId="+id,"_blank");
		
	}
	
	function toDel(cid,flag) { // flag为确认是否为一个用户,1为真,0为假
		$.ajax({url:"/comment/delComment",
			  data:{comId:cid},
			  method:"post",
			  success:function(res){
					if (flag == 0) {
						alert("非本评论的用户不能删除!");
					} else if (res.result == 1) {
						alert(res.errorMsg);
						$("#commentList").load("/comment/getCommentList?articleId=${article.id}" );
						history.go(0);
					}
			  }	
		})
	}
</script>
</head>
<body>

	<c:forEach items="${commentPage.list}" var="comment">
		<dl>
			<%-- <dt>文章标题：<a href="javascript:myopen(${comment.articleId })">${comment.articleTitle}</a></dt> --%>
			<dt>${comment.user.username}：${comment.content}</dt>
			<dd>发布时间:
			  <fmt:formatDate value="${comment.created}"/>&emsp;
			  <c:if test="${SESSION_USER_KEY.username == comment.user.username?true:false}">
			  <a href="javascript:toDel(${comment.id })">删除</a>
			  </c:if>
			</dd>
		</dl>
		<hr>
	</c:forEach>
	${pageUtil}
</body>

<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	//获取点击的的url
	        var url = $(this).attr('data');
	        // alert(url);
	    
	       //在中间区域显示地址的内容
	       $('#commentList').load(url);
	    });
		
	})
	
</script>
</html>