package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardDAO;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ViewFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BoardVO searchVo = new BoardVO();
		searchVo.setNo(Long.parseLong(request.getParameter("formno")));
		
		BoardVO boardVo = new BoardDAO().find(searchVo);
		request.setAttribute("boardVo", boardVo);
		
		//new BoardDAO().addhit(boardVo);
		
		WebUtil.foward("/WEB-INF/views/board/view.jsp", request, response);
	}

}
