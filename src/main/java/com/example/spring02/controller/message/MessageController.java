package com.example.spring02.controller.message;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring02.model.message.dto.MessageDTO;
import com.example.spring02.service.message.MessageService;

@RestController // rest 방식의 컨트롤러(스프링 4.0이후)
@RequestMapping("/message/*")
public class MessageController {
	@Inject
	MessageService messageService;
// ResponseEntity : http status code+date 전달
// @RequestBody : json 형식의 입력 데이터
// @ResponseBody : json 형식의 출력 데이터
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageDTO dto){
		ResponseEntity<String> entity = null;
		try {
			//서비스 호출
			messageService.addMessage(dto);
			//new ResponseEntity("메시지", http 상태코드)
			entity = new ResponseEntity<>("success", HttpStatus.OK); // 200
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400
		}
		return entity;
	}
}
