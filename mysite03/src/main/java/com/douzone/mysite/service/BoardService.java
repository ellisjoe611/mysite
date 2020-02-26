package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.UserVO;

@Service
public class BoardService {

	@Autowired
	private BoardRepository repository;

	// 목록을 조회할 때...
	// 이 때는 model에다가 모든 파라미터를 넣어서 보낸다.
	public Model getUpdatedListModel(Integer page, String kwd, Model model) {
		if (page <= 0) {
			page = 1;
		}
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("startFrom", (page - 1) * 5);
		searchMap.put("kwd", kwd);

		List<BoardVO> boardList = repository.findall(searchMap);
		model.addAttribute("boardList", boardList);
		model.addAttribute("kwd", kwd);
		if(boardList == null || boardList.size() <= 0) {
			return model;
		}

		// 이하는 boardList가 있다는 가정 하에 실행한다.
		Integer counted = boardList.get(0).getCounted().intValue(); // 전체 board 갯수. (1 ~ )
		Integer totalpages = (counted % 5 == 0 ? counted / 5 : counted / 5 + 1); // 전체 페이지 수 (1 ~ )

		// 입력받은 page가 전체 페이지 수보다 많으면 page를 1로 날린다
		if (totalpages < page) {
			page = 1;
		}

		// level (0|1|2|...)
		Integer currentlevel = (page - 1) / 5;
		Integer maxlevel = (totalpages - 1) / 5;

		// begin페이지번호 & end페이지번호
		Integer begin = currentlevel * 5 + 1;
		Integer end = (currentlevel < maxlevel ? begin + 4 : totalpages);

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("endArrowOpened", (currentlevel < maxlevel ? true : false));
		
//		System.out.println("page : " + page + ", counted : " + counted);
//		System.out.println("currentlevel : " + currentlevel + ", maxlevel : " + maxlevel);
//		System.out.println("begin : " + begin + ", end : " + end + ", endArrowOpened : " + (currentlevel < maxlevel ? true : false));

		return model;
	}
	
	// 해당 개시물 내용 조회
	public BoardVO find(Long no) {
		return repository.find(no);
	}
	
	// 개시물 추가 (세션 확인 필요)
	public Boolean addBoard(BoardVO vo) {
		return repository.insert(vo);
	}
	
	// 개시물 수정 (세션 확인 필요)
	// authUser로부터 no를 받은 상태에서 vo.member_no에 추가
	public Boolean modifyBoard(BoardVO vo, UserVO authUser) {
		if(getPermissionForBoardManagement(vo.getNo(), authUser) == false) {
			return false;
		}
		vo.setMember_no(authUser.getNo());
		return repository.modify(vo);
	}
	
	// 해당 개시물의 조회수 증가
	public Boolean addHit(Long no) {
		return repository.addhit(no);
	}
	
	// 해당 개시물을 삭제 (세션 확인 필요)
	public Boolean remove(Long boardNo, UserVO authUser) {
		if(getPermissionForBoardManagement(boardNo, authUser) == false) {
			return false;
		}
		return repository.delete(boardNo);
	}
	
	// 댓글 추가 (세션 확인 필요)
	// 기존의 vo(대상의 no, 대상의 g_no, 대상의 o_no, depth, title, contents) + member_no (authUser.no)
	public Boolean addReply(BoardVO inputVo, Long authUserNo){
		inputVo.setMember_no(authUserNo);
		if(repository.updategroup(inputVo) == false) {
			return false;
		}
		if(repository.addreply(inputVo) == false) {
			return false;
		}
		return true;
	}
	
	public Boolean getPermissionForBoardManagement(Long boardNo, UserVO authUser) {
		return repository.getBoardOwnerNo(boardNo) == authUser.getNo();
	}

}
