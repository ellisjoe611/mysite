<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Board</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />

		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board?page=1" method="get">
					<c:if test="${empty kwd }">
						<input type="text" id="kwd" name="kwd" value="">
					</c:if>
					<c:if test="${not empty kwd }">
						<input type="text" id="kwd" name="kwd" value="${kwd }">
					</c:if>
					<input type="submit" value="제목찾기">
				</form>


				<c:choose>
					<c:when test="${empty boardList }">
						<h2 align="center">개시판 내용이 없거나 검색 결과가 없습니다.</h2>
					</c:when>
					<c:otherwise>
						<table class="tbl-ex">
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>&nbsp;</th>
							</tr>

							<c:forEach items='${boardList }' var='vo' varStatus='status'>
								<tr>
									<td>${status.count + (param.page - 1) * 5 }</td>
									<td style="text-align:left; padding-left:${15*vo.depth }px">
										<c:if test="${vo.depth > 0 }">
											<img src='${pageContext.request.contextPath }/assets/images/reply.png'>
										</c:if>
										<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
									</td>
									<td>${vo.member_name }</td>
									<td>${vo.hit }</td>
									<td>${vo.reg_date }</td>
									<td>
									<c:if test="${not empty authUser && authUser.no == vo.member_no}">
										<a href="${pageContext.request.contextPath }/board/delete/${vo.no }" class="del">삭제</a>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							
						</table>

						<!-- pager 추가 -->
						<div class="pager">
							<ul>
								<c:if test="${param.page >= 6 && empty kwd }">
									<li><a href="${pageContext.request.contextPath }/board?page=${begin-1 }">◀</a></li>
								</c:if>
								<c:if test="${param.page >= 6 && not empty kwd }">
									<li><a href="${pageContext.request.contextPath }/board?page=${begin-1 }&kwd=${kwd }">◀</a></li>
								</c:if>

								<c:forEach begin='${begin }' end='${end }' var='i' step='1'>

									<!-- page가 i랑 같으면 선택된 걸로 처리 -->
									<c:if test="${i == param.page}">
										<li class="selected">${i }</li>
									</c:if>
									<c:if test="${i != param.page && empty kwd }">
										<li><a href="${pageContext.request.contextPath }/board?page=${i }">${i }</a></li>
									</c:if>
									<c:if test="${i != param.page && not empty kwd}">
										<li><a href="${pageContext.request.contextPath }/board?page=${i }&kwd=${kwd }">${i }</a></li>
									</c:if>

								</c:forEach>

								<c:if test="${endArrowOpened eq true && empty kwd }">
									<li><a href="${pageContext.request.contextPath }/board?page=${end+1 }">▶</a></li>
								</c:if>
								<c:if test="${endArrowOpened eq true && not empty kwd}">
									<li><a href="${pageContext.request.contextPath }/board?page=${end+1 }&kwd=${kwd }">▶</a></li>
								</c:if>
							</ul>
						</div>
						<!-- pager 추가 -->
						
					</c:otherwise>
				</c:choose>

				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board/add" id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>