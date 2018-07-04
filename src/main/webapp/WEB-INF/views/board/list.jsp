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

<!-- 검색폼 -->
<form name="form1" method="post" action="${path}/board/list.do">
	<select name="search_option">
	<c:choose>
		<c:when test="${map.search_option == 'name' }">
			<option value="all">이름+내용+제목</option>
			<option value="name" selected>이름</option>
			<option value="content">내용</option>
			<option value="title">제목</option>
		</c:when>
		<c:when test="${map.search_option == 'content' }">
			<option value="all">이름+내용+제목</option>
			<option value="name">이름</option>
			<option value="content" selected>내용</option>
			<option value="title">제목</option>
		</c:when>
		<c:when test="${map.search_option == 'title' }">
			<option value="all">이름+내용+제목</option>
			<option value="name">이름</option>
			<option value="content">내용</option>
			<option value="title" selected>제목</option>
		</c:when>
		<c:otherwise>
			<option value="all" selected>이름+내용+제목</option>
			<option value="name">이름</option>
			<option value="content">내용</option>
			<option value="title">제목</option>
		</c:otherwise>
	</c:choose>
		
	</select>
	<input type="text" name="keyword" value="${map.keyword}">
	<input type="submit" value="조회">
</form>

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
		<c:choose>
			<c:when test="${row.show == 'Y'}">
					<tr>
						<td>${row.bno}</td>
						<td>
							<%-- <a href="${path}/board/view.do?bno=${row.bno}">${row.title}</a> --%>
							<a href="#" onclick="view('${row.bno}')">${row.title}</a>
						</td>
						<td>${row.name}</td>
						<td><fmt:formatDate value="${row.regdate}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${row.viewcnt}</td>
					</tr>
			</c:when>
			<c:otherwise>
				<!-- 숨김처리한 게시물 -->
				<tr>
					<td>${row.bno}</td>
					<td colspan="4" align="center">삭제된 게시물입니다.</td>
				</tr>
			</c:otherwise>
		</c:choose>
		
	</c:forEach>
</table>
<form name="form" method="post">
<input type="hidden" name="bno">
</form>
</body>
</html>