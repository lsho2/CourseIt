<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="resource/css/pages/FindPw.css">
</head>
<body>
    <div class="pw-container">
        <div id="logo">
            <a href="Main.jsp">
                <img src="resource/img/headerImg/logoMain.png" alt="코스잇 메인 로고">
            </a>
        </div>
        <h1>비밀번호 찾기</h1><hr>
        <form action="FindPwController" method="post">
		    <div class="form-group">
		        <label for="id">비밀번호를 찾고자 하는 아이디와 이메일을 입력하세요</label><br><br>
		        <label for="email">이메일</label>
		        <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>           
		    </div>
		    <div class="form-group">
		        <label for="id">아이디</label>
		        <input type="text" id="id" name="id" placeholder="아이디를 입력하세요" required>
		    </div><hr>
		    <input type="submit" value="비밀번호 찾기">
		</form>

    </div>
</body>
</html>