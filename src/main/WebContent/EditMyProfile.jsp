<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원정보수정</title>
    <link rel="stylesheet" type="text/css" href="resource/css/pages/EditMyProfile.css">
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
        <h1>회원정보수정</h1>
        <hr>
        <!-- 회원정보 수정 폼 -->
        <form name="editProfileForm" method="post" action="<%= request.getContextPath() %>/editMyProfile.do">
		    <!-- 아이디 (수정 불가) -->
		    <div class="form-group">
		        <label for="userId">아이디 *</label>
		        <input type="text" id="userId" name="userId" value="<%= session.getAttribute("userId") %>" readonly>
		    </div>
		    <!-- 비밀번호 수정 -->
		    <div class="form-group">
		        <label for="userPw">새 비밀번호</label>
		        <input type="password" id="userPw" name="userPw" placeholder="변경하지 않으려면 비워두세요">
		    </div>
		    <!-- 비밀번호 확인 -->
		    <div class="form-group">
		        <label for="pwdre">비밀번호 확인</label>
		        <input type="password" id="pwdre" name="pwdre" placeholder="변경하지 않으려면 비워두세요">
		        <span id="pwdreFeedback" class="feedback"></span>
		    </div>
		    <!-- 닉네임 -->
		    <div class="form-group">
		        <label for="userName">닉네임 *</label>
		        <input type="text" id="userName" name="userName" 
		               value="<%= session.getAttribute("userName") %>">
		    </div>
		    <!-- 이메일 -->
		    <div class="form-group">
		        <label for="userEmail">이메일 *</label>
		        <input type="email" id="userEmail" name="userEmail" 
		               value="<%= session.getAttribute("userEmail") %>">
		    </div>
		    <hr>
		    <!-- 정보 수정 버튼 -->
		    <div class="form-group">
		        <button type="submit" class="submit-btn">정보 수정</button>
		    </div>
		</form>

    </div>
</body>
</html>
