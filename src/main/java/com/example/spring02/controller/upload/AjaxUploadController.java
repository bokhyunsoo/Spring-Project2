package com.example.spring02.controller.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring02.service.board.BoardService;
import com.example.spring02.util.MediaUtils;
import com.example.spring02.util.UploadFileUtils;

@Controller
public class AjaxUploadController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxUploadController.class);
	
	@Inject
	BoardService boardService;
	
	// servlet-context.xml에 선언된 값
	@Resource(name = "uploadPath")
	String uploadPath;

	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {
		// views/upload/uploadAjax.jsp로 이동
	}

	// 업로드한 파일은 MultipartFile 변수에 저장됨
	@ResponseBody // json 형식으로 리턴
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		// 파일 정보를 로그에 출력
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		// new ResponseEntity(데이터, 상태코드)
		// new ResponseEntity(업로드된 파일이름, 상태코드)
		// 업로드한 파일 정보와 Http 상태 코드를 함께 리턴
		return new ResponseEntity<String>(
				UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
	}
	
	// 이미지 표시 기능
	@ResponseBody // view가 아닌 data 리턴
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) 
			throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		try {
			// 확장자 검사
			// test.jpg	a.b.c.d.jpg
			String formatName = fileName.substring(
					fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			// 헤더 구성 객체
			HttpHeaders headers = new HttpHeaders();
			// InputStream 생성
			in = new FileInputStream(uploadPath + fileName);
//			if (mType != null) { // 이미지 파일이면
//				headers.setContentType(mType);
//			} else { // 이미지가 아니면
				fileName = fileName.substring(
						fileName.indexOf("_") + 1); // uuid를 제외한 파일 이름
				// 다운로드용 컨텐트 타입
				headers.setContentType(
						MediaType.APPLICATION_OCTET_STREAM); // 컨텐트 타입
				// 큰 따옴표 내부에 " \" "
				// 바이트배열을 스트링으로
				// iso-8859-1 서유럽언어
				// new String(fileName.getBytes("utf-8"),"iso-8859-1")
				headers.add("Content-Disposition",
						"attachment; filename=\"" 
								+ new String(
fileName.getBytes("utf-8"), "iso-8859-1") + "\""); // 첨부파일정보
				// headers.add("Content-Disposition"
				// ,"attachment; filename='"+fileName+"'");
//			}
			// 바이트배열, 헤더
			entity = new ResponseEntity<byte[]>(
					IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(
					HttpStatus.BAD_REQUEST);
		} finally {
			if (in != null)
				in.close(); // 스트림 닫기
		}
		return entity;
	}
	
	@ResponseBody //뷰가 아닌 데이터를 리턴
	@RequestMapping(value="/upload/deleteFile"
		,method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		logger.info("fileName:"+fileName); 
		//확장자 검사
		String formatName=fileName.substring(
				fileName.lastIndexOf(".")+1);//확장자 검사
		MediaType mType=MediaUtils.getMediaType(formatName);
		if(mType != null) { //이미지 파일이면 원본이미지 삭제
			String front=fileName.substring(0, 12);
			String end=fileName.substring(14);
// 		File.separatorChar : 유닉스 / 윈도우즈\	
			new File(uploadPath+(front+end).replace(
					'/',File.separatorChar)).delete();// 썸네일 삭제
		}
		//원본 파일 삭제(이미지이면 썸네일 삭제)
		new File(uploadPath+fileName.replace(
				'/',File.separatorChar)).delete(); // 파일 삭제
		//레코드 삭제
		boardService.deleteFile(fileName);
		return new ResponseEntity<String>("deleted"
				,HttpStatus.OK);
	}
}
