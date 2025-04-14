<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" type="text/css" href="resource/css/pages/Login.css">
</head>
<body>
    <div class="login-container">
        <div id="logo">
            <a href="Main.jsp">
                <img src="resource/img/headerImg/logoMain.png" alt="코스잇 메인 로고">
            </a>
        </div>
        <h1>로그인</h1>
        <hr><br>
        
        <!-- 에러 메시지 확인 및 alert 표시 -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <script>
            alert("<%= errorMessage %>");
        </script>
        <% } %>
        
        
        <form action="<%= request.getContextPath() %>/login.do" method="post" >
            <div class="form-group">
                <label for="userId">아이디</label>
                <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요" required>
            </div>
            <div class="form-group">
                <label for="userPw">비밀번호</label>
                <input type="password" id="userPw" name="userPw" placeholder="비밀번호를 입력하세요" required>
            </div><br>
            <input type="submit" value="로그인"><br><br><hr>
        </form>
        <div class="footer">
            <a href="<%= request.getContextPath() %>/FindId.jsp">아이디 찾기</a>         	
            |
			<a href="<%= request.getContextPath() %>/FindPw.jsp">비밀번호 찾기</a> 
            |
            <a href="<%= request.getContextPath() %>/Sign_up.jsp">회원 가입</a> 
        </div>
    </div>
</body>
</html>
