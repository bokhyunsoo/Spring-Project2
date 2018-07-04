package com.example.spring02.model.board.dao;

import java.util.List;

import com.example.spring02.model.board.dto.BoardDTO;

public interface BoardDAO {
	//첨부파일 관려 기능
	public void deleteFile(String fullName);
	public List<String> getAttach(int bno);
	public void addAttach(String fullName);
	public void updateAttach(String fullName, int bno);
	//CRUD(Create, Read, Update, Delete)
	public void create(BoardDTO dto) throws Exception;
	public BoardDTO read(int bno) throws Exception;
	public void update(BoardDTO dto) throws Exception;
	public void delete(int bno) throws Exception;
	public List<BoardDTO> listAll(int start, int end, String search_option, String keyword) throws Exception;
	public void increaseViewcnt(int bno); //조회수 증가
	//레코드 갯수 계산
	public int countArticle(String search_option, String keyword) throws Exception;
}
