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
	window.open("/article/show?id="+id,"_blank");
	
}

function toUpdate(id) {
	$('#center').load("/article/update?id="+id);
}

function del(id) {
	var result = confirm("您确定要删除文章么？");
	if(!result)
		return;
	
	$.post("/user/delArticle",{id:id},function(data){
		if(data){
			alert('删除成功')		
			$("#center").load("/user/myarticlelist")
		}else{
			alert('删除失败')
		}
	},"json"
	)
}

</script>
</head>
<body>
		
		<div align="center">
		<form action="" method="post">
			<input type="text" name="terms" placeholder="输入关键字..."/>
			<input type="submit" value="搜索"/><br><br>
		</form>
		</div>
 	<c:forEach items="${pageArticle.list}" var="article">
		<dl>
			<dt><a href="javascript:myopen(${article.id })">${article.title }</a></dt>
			<dd>作者:${article.user.username} 发布时间:
			  <fmt:formatDate value="${article.created}"/>
				频道:${article.channel.name}  分类:${article.cat.name}  &nbsp;标签:${article.terms }  &nbsp;浏览:${article.hits }
			    <a href="javascript:toUpdate(${article.id })">修改</a>
			    <a href="javascript:del(${article.id })">删除</a>
			</dd>
		</dl>
		<hr>
	</c:forEach>
	${pageStr}
	
<%-- 	<table>
	  <c:forEach items="${pageArticle.list}" var="article">
	  	<tr style="border-bottom: 2px solid #e5e5e5;">
		    <td>
		    	<img src="../pic/${pageArticle.picture}" width="160px" height="90px">
		    </td>
		    <td>
		    	<dl>
					<dt><a href="javascript:myopen(${article.id})">${article.title}</a></dt>
					<dd>作者:${article.user.username} 发布时间:
					  <fmt:formatDate value="${article.created}"/>
						频道:${article.channel.name}  分类:${article.category.name}
					    <a href="javascript:toUpdate(${article.id})">修改</a>
					    <a href="javascript:delAtr(${article.id})">删除</a>
					</dd>
				</dl>
		    </td>
	 	</tr>
	</c:forEach>
	</table>
	
	<input type="button" onclick="page(1)" value="首页">&nbsp;
	<input type="button" onclick="page(${pageArticle.prePage == 0 ? 1 : pageArticle.prePage})" value="上一页">
	&emsp;${pageArticle.lastPage == 0 ? 0 : pageArticle.pageNum}/${pageArticle.lastPage}&emsp;
	<input type="button" onclick="page(${pageArticle.nextPage == 0 ? pageArticle.lastPage : pageArticle.nextPage})" value="下一页">&nbsp;
	<input type="button" onclick="page(${pageArticle.lastPage})" value="尾页"> --%>


</body>
<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	
	    	  //获取点击的的url
	        var url = $(this).attr('data');
	        //alert(url);
	    
	       //在中间区域显示地址的内容
	       $('#center').load(url);
	       
	    });
	})
	
/* 	// 翻页
	function page(url) {
		$('#center').load("myarticlelist?pageNum="+url);
	} */
	
</script>
</html>