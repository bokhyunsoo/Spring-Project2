package com.example.spring02.service.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.spring02.model.board.dto.ReplyDTO;

public interface ReplyService {
	// 댓글 목록
		public List<ReplyDTO> list(int bno, int start, int end, HttpSession session);
		public int count(int bno); // 댓글 갯수
		public void create(ReplyDTO dto); // 댓글 쓰기
		public void update(ReplyDTO dto); // 댓글 수정
		public void delete(int rno); // 댓글 삭제
		public ReplyDTO detail(int rno); // 댓글 상세
}
