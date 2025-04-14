<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기 결과</title>
	<link rel="stylesheet" type="text/css" href="resource/css/pages/FindIdResult.css">
</head>
<body>
    <div class="id-container">
        <h1>아이디 찾기 결과</h1>
        <hr>
        <div>
            <c:choose>
                <c:when test="${not empty userId}">
                    <p>입력하신 이메일로 등록된 아이디는 <strong>${userId}</strong>입니다.</p>
                </c:when>
                <c:otherwise>
                    <p>아이디를 찾을 수 없습니다.</p>
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
