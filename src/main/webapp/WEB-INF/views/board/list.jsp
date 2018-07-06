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
function list(page){
	location.href="${path}/board/list.do?curPage="+page+"&search_option=${map.search_option}"+"&keyword=${map.keyword}";
}
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
							<!-- 댓글 갯수 표시 -->
							<c:if test="${row.cnt > 0}">
								<span style="color:red;">( ${row.cnt} )</span>
							</c:if>
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
	<tr>
		<td colspan="5" align="center">
			<c:if test="${map.pager.curBlock > 1}">
				<a href="#" onclick="list('1')">[처음]</a>
			</c:if>
			<c:if test="${map.pager.curBlock > 1}">
				<a href="#" onclick="list('${map.pager.prevPage}')">
				[이전]</a>
			</c:if>
			<c:forEach var="num" 
				begin="${map.pager.blockBegin}"
				end="${map.pager.blockEnd}">
				<c:choose>
					<c:when test="${num == map.pager.curPage}">
					<!-- 현재 페이지인 경우 하이퍼링크 제거 -->
						<span style="color:red;">${num}</span>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="list('${num}')">${num}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${map.pager.curBlock < map.pager.totBlock}">
				<a href="#" 
				onclick="list('${map.pager.nextPage}')">[다음]</a>
			</c:if>
			<c:if test="${map.pager.curPage < map.pager.totPage}">
				<a href="#" 
				onclick="list('${map.pager.totPage}')">[끝]</a>
			</c:if>
		</td>
	</tr>
</table>
<form name="form" method="post">
<input type="hidden" name="bno">
</form>
</body>
</html>