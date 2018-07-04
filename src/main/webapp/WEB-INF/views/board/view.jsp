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
	$("#btnUpdate").click(function(){
		document.form1.action="${path}/board/update.do";
		document.form1.submit();
	});
});
$(function(){
	$("#btnDelete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			document.form1.action="${path}/board/delete.do";
			document.form1.submit();
		}
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>게시물 보기</h2>
<form id="form1" name="form1" method="post">
<div>작성일자 : <fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd a HH:mm:ss"/>
</div>
<div>조회수 : ${dto.viewcnt}</div>
<div>이름 : ${dto.name}</div>
<div>제목 : <input name="title" value="${dto.title}"></div>
<div style="width:80%";>내용 : <textarea rows="3" cols="80" name="content" id="content">${dto.content}</textarea>
</div>
<script>
CKEDITOR.replace("content",{filebrowserUploadUrl : "${path}/imageUpload.do"});
</script>
<div>
	<input type="hidden" name="bno" value="${dto.bno}">
	<!-- 본인의 게시물만 수정, 삭제 버튼 표시 -->
	<c:if test="${sessionScope.userid == dto.writer}">
	<button type="button" id="btnUpdate">수정</button>
	<button type="button" id="btnDelete">삭제</button>
	</c:if>
	<button type="button" id="btnList">목록</button>
</div>
</form>
</body>
</html>