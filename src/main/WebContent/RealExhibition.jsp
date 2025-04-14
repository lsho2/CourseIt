<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>전시/공연 목록</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/pages/RealExhibition.css">
</head>
<body>
	<%@ include file="/resource/common/Header.jsp" %>s
    <main>
        <h2 class="exhibition-header">전시/공연 전체보기</h2>
        <div id="exhibition-list" class="exhibition-list">
            <!-- JavaScript로 데이터가 동적으로 삽입됩니다 -->
        </div>
    </main>

    <!-- JavaScript에서 사용할 변수 전달 -->
    <script>
        var contextPath = "<%= request.getContextPath() %>";
        var isLoggedIn = <%= session.getAttribute("userId") != null ? "true" : "false" %>;
    </script>
    <script src="${pageContext.request.contextPath}/resource/js/RealExhibition.js"></script>
</body>
</html>
