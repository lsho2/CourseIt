<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<link rel="stylesheet" type="text/css" href="resource/css/pages/FindId.css">
</head>
<body>
    <div class="id-container">
        <div id="logo">
            <a href="Main.jsp">
                <img src="resource/img/headerImg/logoMain.png" alt="코스잇 메인 로고">
            </a>
        </div>
        <h1>아이디 찾기</h1>
        <hr>
        
        <!-- 사용자 메시지를 표시하는 영역 -->
        <c:if test="${not empty message}">
            <div class="message">
                <p>${message}</p>
            </div>
        </c:if>

        <!-- 아이디 찾기 폼 -->
        <form action="FindIdController" method="post">
            <div class="form-group">
                <label for="email">아이디를 찾고자 하는 이메일을 입력하세요</label><br><br>
                <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>
            </div>
            <hr>
            <input type="submit" value="아이디 찾기">
        </form>
    </div>
</body>
</html>
