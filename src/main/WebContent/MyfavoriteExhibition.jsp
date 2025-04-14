<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>내가 찜한 전시</title>
	<link rel="stylesheet" type="text/css" href="resource/css/pages/MyFavoriteExhibition.css">
</head>
<body>
    <%@ include file="/resource/common/Header.jsp" %>
    <main>
        <h2 class="exhibition-header">
            <% 
                // 세션에서 사용자 이름 가져오기 (변수 이름 변경)
                String sessionUserName = (String) session.getAttribute("userName");
                if (sessionUserName == null || sessionUserName.trim().isEmpty()) {
                    sessionUserName = "내"; // 기본값
                }
            %>
            <%= sessionUserName %>가 찜한 전시
        </h2>
        <div class="exhibition-list"></div>
    </main>
    <script>
        var contextPath = "<%= request.getContextPath() %>";
        var isLoggedIn = <%= session.getAttribute("userId") != null %>;
    </script>
    <script src="<%= request.getContextPath() %>/resource/js/MyfavoriteExhibition.js"></script>
</body>
</html>
