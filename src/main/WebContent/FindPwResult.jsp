<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기 결과</title>
    
	<link rel="stylesheet" type="text/css" href="resource/css/pages/FindPwResult.css">
</head>
<body>
    <div class="pw-container">
        <h1>비밀번호 찾기 결과</h1>
        <hr>
        <div>
            <c:choose>
                <c:when test="${not empty userPw}">
                    <p>입력하신 정보로 찾은 비밀번호는 <strong>${userPw}</strong>입니다.</p>
                </c:when>
                <c:otherwise>
                    <p>비밀번호를 찾을 수 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
        <hr>
        <form action="Login.jsp" method="post">
        	<input type="submit" value="로그인">
        </form>
    </div>
</body>
</html>
