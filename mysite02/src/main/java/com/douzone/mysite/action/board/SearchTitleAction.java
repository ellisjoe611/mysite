package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardDAO;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class SearchTitleAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<BoardVO> boardList = null;
		Integer page = null;

		// 검색어를 불러온다
		// 있는 경우 "%검색어%"
		// 없는 경우는 ""
		String kwd = (request.getParameter("kwd") == null || "".equals(request.getParameter("kwd")) ? ""
				: request.getParameter("kwd"));

		BoardVO searchVo = new BoardVO();
		searchVo.setTitle("%" + kwd + "%");

		// page 파라미터가 누락된 경우
		// 각각에 알맞는 검색 결과를 불러온다
		if (request.getParameter("page") == null || Integer.parseInt(request.getParameter("page")) <= 0) {
			page = 1;
			boardList = new BoardDAO().findByTitle(searchVo, 0);
		} else {
			page = Integer.parseInt(request.getParameter("page"));
			boardList = new BoardDAO().findByTitle(searchVo, (page - 1) * 5);
		}

		// boardList (검색 결과)가 없으면 ...
		if (boardList == null || boardList.size() <= 0) {
			WebUtil.foward("/WEB-INF/views/board/list.jsp?page=1&kwd=" + kwd, request, response);
			return;
		}

		request.setAttribute("boardList", boardList);

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

		request.setAttribute("begin", begin);
		request.setAttribute("end", end);
		request.setAttribute("endArrowOpened", (currentlevel < maxlevel ? true : false));
		request.setAttribute("kwd", kwd);

		WebUtil.foward("/WEB-INF/views/board/list.jsp?page=" + page.intValue() + "&kwd=" + kwd, request, response);
	}

}
