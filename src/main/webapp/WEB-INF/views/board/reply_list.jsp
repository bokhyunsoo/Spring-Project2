<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
</head>
<body>
<table style="width:700px;">
<% pageContext.setAttribute("newLineChar", "\n"); %>
<c:forEach var="row" items="${list}">
<!-- 공백문자 처리 -->
	<c:set var="str" value="${fn:replace(row.replytext,'  ','&nbsp;&nbsp;')}" />
<!-- 줄바꿈 처리 -->
	<c:set var="str" value="${fn:replace(row.replytext,newLineChar,'<br>') }" />
	<tr>
		<td>${row.name} ( <fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd a HH:mm:ss"/> )<br>
			<%-- ${row.replytext} --%>
			${str}
<!-- 본인이 작성한 댓글만 수정 기능 표시 -->
<c:if test="${sessionScope.userid == row.replyer}">
	<input type="button" value="Modify">
</c:if>
		</td>
	</tr>
</c:forEach>

</table>
</body>
</html>