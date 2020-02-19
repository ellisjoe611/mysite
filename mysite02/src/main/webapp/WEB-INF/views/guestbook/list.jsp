<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>Guest Book</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.request.contextPath }/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />

		<div id="content">
			<div id="guestbook">
				<!--방문록 작성 포멧-->
				<form action="${pageContext.request.contextPath }/guestbook"
					method="post">
					<input type="hidden" name="a" value="insert">

					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="pw"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="contents" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>

				</form>

				<!--방문록 리스트 보여주기-->
				<ul>
					<li>
						<c:choose>
							<c:when test="${empty list }">
								<h2 align="center">등록된 방문글이 없습니다. 위에서 새로 추가해보세요!</h2>
							</c:when>
							<c:otherwise>
								<c:forEach items='${list }' var='vo' varStatus='status'>
									<table>
										<tr>
											<td>${status.count }</td>
											<td>${vo.name }</td>
											<td>${vo.reg_date }</td>
											<td>
												<a href="${pageContext.request.contextPath }/guestbook?a=deleteform&view_no=${status.count }&no=${vo.no }">삭제</a>
											</td>
										</tr>
										<tr>
											<td colspan=4>${fn:replace(vo.contents, newLine, "<br>") }</td>
										</tr>
									</table>
									<br>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>