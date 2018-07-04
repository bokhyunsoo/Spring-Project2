<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script src="${path}/ckeditor/ckeditor.js"></script>
<script>
$(function(){
	$("#btnSave").click(function(){
		document.form1.submit();
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>글쓰기</h2>
<form id="form1" name="form1" method="post" action="${path}/board/insert.do">
	<div>
		제목 <input name="title" id="title" size="80" placeholder="제목을 입력하세요">
	</div>
	<div style="width:800px;">
		내용 <textarea id="content" name="content" rows="5" cols="80" placeholder="내용을 입력하세요"></textarea>
		<script>
		CKEDITOR.replace("content",{
			filebrowserUploadUrl : "${path}/imageUpload.do"
		});
		</script>
	</div>
	<div style="width:700px; text-align: center;">
		<button type="button" id="btnSave">확인</button>
	</div>
</form>
</body>
</html>