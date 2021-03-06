package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring02.model.board.dao.BoardDAO;
import com.example.spring02.model.board.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	BoardDAO boardDao;
	
	@Override
	public void deleteFile(String fullName) {
		boardDao.deleteFile(fullName);
	}

	@Override
	public List<String> getAttach(int bno) {
		return boardDao.getAttach(bno);
	}

	@Transactional
	@Override
	public void create(BoardDTO dto) throws Exception {
		boardDao.create(dto);
		//첨부파일 정보 저장
		String[] files = dto.getFiles();
		if(files==null) return; //첨부파일이 없으면 리턴
		for (String name : files) {
			boardDao.addAttach(name);
		}
	}

	@Override
	public BoardDTO read(int bno) throws Exception {
		return boardDao.read(bno);
	}
	
	@Transactional
	@Override
	public void update(BoardDTO dto) throws Exception {
		boardDao.update(dto);
		// 첨부파일 정보 저장
		String[] files = dto.getFiles();
		if (files == null)
			return; // 첨부파일이 없으면 리턴
		for (String name : files) {
			boardDao.updateAttach(name, dto.getBno());
		}

	}

	@Override
	public void delete(int bno) throws Exception {
		boardDao.delete(bno);

	}

	@Override
	public List<BoardDTO> listAll(String search_option, String keyword, int start, int end) throws Exception {
		return boardDao.listAll(start, end, search_option, keyword);
	}

	@Override
	public void increaseViewcnt(int bno, HttpSession session) throws Exception {
		long update_time = 0;
		//세션에 저장된 게시물의 조회시간 검색
		if(session.getAttribute("update_time_"+bno)!=null) {
			update_time=(long)session.getAttribute("update_time_"+bno);
		}
		//현재 시간
		long current_time = System.currentTimeMillis();
		//일정 시간이 경과된 후 조회수 증가 처리
		if(current_time - update_time > 10*1000 ) {
			boardDao.increaseViewcnt(bno);
			session.setAttribute("update_time_"+bno, current_time);
		}
	}

	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		return boardDao.countArticle(search_option, keyword);
	}

}
