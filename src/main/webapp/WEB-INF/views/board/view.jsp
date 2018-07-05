<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script src="${path}/include/js/common.js"></script>
<script src="${path}/ckeditor/ckeditor.js"></script>
<script>
$(function(){
	listAttach(); // 첨부파일 목록 로딩
	
	//드래그 기본효과 막음
	$(".fileDrop").on("dragenter dragover",function(e){
		e.preventDefault();
	});
	$(".fileDrop").on("drop",function(e){
		e.preventDefault();
		//첫번째 첨부파일
		var files=e.originalEvent.dataTransfer.files;
		var file=files[0];
		//폼 데이터에 첨부파일 추가
		var formData=new FormData();
		//폼 데이터에 추가
		formData.append("file",file);
		//processData: false - header가 아닌 body로 전송
		$.ajax({
			url: "${path}/upload/uploadAjax",
			data: formData,
			dataType: "text",
			processData: false,
			contentType: false,
			type: "post",
			success: function(data){
				//console.log(data);
				//data : 업로드한 파일 정보와 Http 상태 코드
				var fileInfo=getFileInfo(data);
				//console.log(fileInfo);
				var html="<a href='"+fileInfo.getLink+"'>"+
					fileInfo.fileName+"</a><br>";
				html += "<input type='hidden' class='file' value='"
					+fileInfo.fullName+"'>";
				$("#uploadedList").append(html);
			}
		});
	});
	
	//첨부파일 삭제
	$("#uploadedList").on("click",".file_del",function(e){
		var that=$(this); // 클릭한 태그
		$.ajax({
			type: "post",
			url : "${path}/upload/deleteFile",
			data : {fileName:$(this).attr("data-src")},
			dataType: "text",
			success: function(result){
				if( result == "deleted") {
					that.parent("div").remove();
				}
			}
		});
	});
	
	$("#btnUpdate").click(function(){
		
		var str = "";
		// uploadedList 내부의 .file 태그 각각 반복
		$("#uploadedList .file").each(
				function(i) {
					console.log(i);
					//hidden 태그 구성
					str += "<input type='hidden' name='files[" + i
							+ "]'	value='" + $(this).val() + "'>";
				});
		//폼에 hidden 태그들을 붙임
		$("#form1").append(str);
		
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
//첨부파일 리스트를 출력하는 함수
function listAttach(){
	$.ajax({
		type : "post",
		url : "${path}/board/getAttach/${dto.bno}",
		success : function(list){
			// list => json 형식의 데이터
			$(list).each(function(){
				var fileInfo = getFileInfo(this);
				var html = "<div><a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a>&nbsp;&nbsp;";
				html+="<a href='#' class='file_del' data-src='"+this+"'>[삭제]</a></div>";
				$("#uploadedList").append(html);
			});
		}
	});
}
</script>
<style>
.fileDrop {
	width: 600px;
	height: 100px;
	border: 1px dotted gray;
	background-color: gray;
}
</style>
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

<!-- 첨부파일 목록 -->
<div id="uploadedList"></div>
<!-- 새로운 첨부파일을 올릴 영역 -->
<div class="fileDrop"></div>

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