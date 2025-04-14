<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
	<link rel="stylesheet" type="text/css" href="resource/css/pages/Sign_up.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
	    var contextPath = "<%= request.getContextPath() %>";
	</script>
	<script src="resource/js/Sign_up.js"></script>




</head>
<body>
    <div class="sign_up-container">
        <!-- 로고 -->
        <div id="logo">
            <a href="Main.jsp">
                <img src="resource/img/headerImg/logoMain.png" alt="코스잇 메인 로고">
            </a>
        </div>
        <!-- 제목 -->
        <h1>회원가입</h1>
        <hr>
        <!-- 회원가입 폼 -->
        <form name="signUpForm" method="post" action="<%= request.getContextPath() %>/signUp.do">
            <!-- 아이디 입력 -->
			<div class="form-group">
			    <label for="userId">아이디 *</label>
			    <input type="text" id="userId" name="userId" placeholder="4~16자 영문/숫자만 허용" required>
			    <button type="button" class="check-btn">중복 확인</button>
			    <span id="userIdFeedback" class="feedback"></span>
			</div>
			
            <!-- 비밀번호 입력 -->
            <div class="form-group">
                <label for="userPw">비밀번호 *</label>
                <input type="password" id="userPw" name="userPw" placeholder="영문, 숫자 조합 8~16자" required>
            </div>
            <!-- 비밀번호 확인 -->
				<div class="form-group">
				    <label for="pwdre">비밀번호 확인 *</label>
				    <input type="password" id="pwdre" name="pwdre" placeholder="비밀번호를 한 번 더 입력하세요" required>
				    <span id="pwdreFeedback" class="feedback"></span>
				</div>
            <!-- 닉네임 -->
            <div class="form-group">
                <label for="userName">닉네임 *</label>
                <input type="text" id="userName" name="userName" placeholder="예) 홍길동123" required>
            </div>
            <!-- 이메일 -->
            <div class="form-group">
                <label for="userEmail">이메일 *</label>
                <input type="email" id="userEmail" name="userEmail" placeholder="예) example@example.com" required>
            </div>
            <hr>
            <!-- 회원가입 버튼 -->
            <div class="form-group">
                <button type="submit" class="submit-btn">회원가입</button>
            </div>
        </form>
    </div>
</body>
</html>
