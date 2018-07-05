<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script src="${path}/include/js/common.js"></script>
<!-- common.js -->
<script src="${path}/ckeditor/ckeditor.js"></script>
<script>
$(function(){
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
			success: function(data){ // 콜백함수
				//console.log(data);
				//data : 업로드한 파일 정보와 Http 상태 코드
				var fileInfo=getFileInfo(data); //첨부 파일 정보
				//console.log(fileInfo);
				var html="<a href='"+fileInfo.getLink+"'>"+
					fileInfo.fileName+"</a><br>"; // 첨부파일 링크
				html += "<input type='hidden' class='file' value='"
					+fileInfo.fullName+"'>"; // hidden 태그 추가
				$("#uploadedList").append(html); // div에 추가
			}
		});
	});
	
	$("#btnSave").click(
				function() {
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
					document.form1.submit();
				});
	});
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
<h2>글쓰기</h2>
<form id="form1" name="form1" method="post" action="${path}/board/insert.do">
	<div>
		제목 <input name="title" id="title" size="80" placeholder="제목을 입력하세요">
	</div>
	<div style="width:800px;">
		내용 <textarea id="content" name="content" rows="3" cols="80" placeholder="내용을 입력하세요"></textarea>
		<script>
		CKEDITOR.replace("content",{
			filebrowserUploadUrl : "${path}/imageUpload.do"
		});
		</script>
	</div>
	<div>
		첨부파일을 등록하세요
		<!-- 첨부파일을 드래그할 영역 -->
		<div class="fileDrop"></div>
		<!-- 첨부파일 목록이 표시되는 영역 -->
		<div id="uploadedList"></div>
	</div>
	<div style="width:700px; text-align: center;">
		<button type="button" id="btnSave">확인</button>
	</div>
</form>
</body>
</html>