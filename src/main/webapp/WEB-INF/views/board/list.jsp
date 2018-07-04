<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
$(function(){
	$("#btnWrite").click(function(){
		location.href="${path}/board/write.do";
	});
});
function view(bno){
	document.form.bno.value = bno;
	document.form.action="${path}/board/view.do";
	document.form.submit();
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>게시판</h2>
<button type="button" id="btnWrite">글쓰기</button>
${map.count}개의 게시물이 있습니다.
<table border="1" width="600px">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>이름</th>
		<th>날짜</th>
		<th>조회수</th>
	</tr>
	<c:forEach var="row" items="${map.list}">
		<tr>
			<td>${row.bno}</td>
			<td><a href="${path}/board/view.do?bno=${row.bno}">${row.title}</a>
			<a href="#" onclick="view('${row.bno}')">${row.title}</a></td>
			<td>${row.name}</td>
			<td><fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${row.viewcnt}</td>
		</tr>
	</c:forEach>
</table>
<form name="form" method="post">
<input type="hidden" name="bno">
</form>
</body>
</html>