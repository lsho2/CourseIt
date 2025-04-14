<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이프로필</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resource/css/pages/myPage.css">
</head>
<body>

	<%@ include file="/resource/common/Header.jsp" %>

    <main>
        <section id="MypageBody">
		    <h1>마이프로필</h1>
		    <%
		        String useMemName = (String) session.getAttribute("userName");
		        if (useMemName != null) {
		    %>
		    <p>안녕하세요, <%= useMemName %>님!</p>
		    <a href="<%= request.getContextPath() %>/EditMyProfile.jsp" class="button">회원정보 수정</a>
		    <a href="<%= request.getContextPath() %>/MyfavoriteExhibition.jsp" class="button">찜한 전시</a>

		    <% } else { %>
		    <p>로그인 후 이용해 주세요.</p>
		    <% } %>
		</section>


    </main>
    
    <%@ include file="/resource/common/Footer.jsp" %>
</body>
</html>
