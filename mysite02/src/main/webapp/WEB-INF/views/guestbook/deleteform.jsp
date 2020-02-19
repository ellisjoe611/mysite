<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>방명록 삭제하기</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="content">
			<div id="guestbook" class="delete-form">
				<h2 align="center">[${param.view_no }]번 방문글을 삭제할까요?</h2>
				
				<form method="post" action="${pageContext.request.contextPath }/guestbook">
					<input type="hidden" name="a" value="delete">
					<input type='hidden' name="no" value="${param.no }">
					
					<label>비밀번호</label>
					<input type="password" name="pw">
					<input type="submit" value="삭제하기">		
				</form>
				
				<a href="${pageContext.request.contextPath }/guestbook">방명록 리스트</a>
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>