<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.user.UserDTO" %>

<%
    UserDTO user = (UserDTO) request.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>사용자 수정</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
	<%@ include file="/resource/common/AdminHeader.jsp" %>
    <div class="container mt-5">
        <h2>사용자 수정</h2>
        <form method="post" action="<%= request.getContextPath() %>/userServlet.do">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="userId" value="<%= user.getUserId() %>">
            
            <div class="mb-3">
                <label for="userName" class="form-label">이름</label>
                <input type="text" class="form-control" id="userName" name="userName" value="<%= user.getUserName() %>" required>
            </div>

            <div class="mb-3">
                <label for="userEmail" class="form-label">이메일</label>
                <input type="email" class="form-control" id="userEmail" name="userEmail" value="<%= user.getUserEmail() %>" required>
            </div>

            <div class="mb-3">
                <label for="userPw" class="form-label">새 비밀번호 (선택)</label>
                <input type="password" class="form-control" id="userPw" name="userPw" placeholder="비밀번호를 변경하려면 입력하세요.">
            </div>

            <button type="submit" class="btn btn-primary">수정 완료</button>
            <a href="<%= request.getContextPath() %>/AdminPage/User_management.jsp" class="btn btn-secondary">취소</a>
        </form>
    </div>
</body>
</html>
