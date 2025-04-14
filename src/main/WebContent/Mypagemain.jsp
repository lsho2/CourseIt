<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Page</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resource/css/pages/myPage.css">
</head>
<body>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f4f4f4;
}

/* 메인 컨테이너 */
.main-container {
	margin-top: 8vh;
    margin-left: 25%;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    max-width: 1000px;
    box-sizing: border-box;
}

/* 프로필 섹션 */
.profile-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
}

.profile-pic {
    position: relative;
    width: 120px;
    height: 120px;
    border-radius: 50%;
    overflow: hidden;
    background-color: #ddd;
}

.profile-pic img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.profile-plus {
    position: absolute;
    bottom: 0;
    right: 0;
    background-color: #007bff;
    color: white;
    border-radius: 50%;
    width: 25px;
    height: 25px;
    text-align: center;
    line-height: 25px;
    font-weight: bold;
    cursor: pointer;
}

.username {
    font-size: 2em;
    font-weight: bold;
    margin-bottom: 10px;
}

/* 마이로그 섹션 */
.mylog-section {
    margin-top: 20px;
}

.mylog-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ddd;
    padding-bottom: 10px;
    margin-bottom: 20px;
}

.new-log-button {
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
}

.mylog-list {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
}

.log-item {
    width: 300px;
    border: 1px solid #ddd;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.log-image {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.log-title {
    font-weight: bold;
    margin: 10px 0;
}

.no-log {
    text-align: center;
    font-size: 1.2em;
    color: #888;
}

</style>

<%@ include file="/resource/common/Header.jsp" %>

<%
    // 세션에서 사용자 ID 가져오기
    String userId1 = (String) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    // DB 연결해서 프로필 이미지와 마이로그 데이터 가져오기
    String profileImage = "/CourseIt/resource/img/headerImg/profile.png";
    List<String[]> mylogList = new ArrayList<>();

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
	
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/courseitdb", "root", "password");

        // 프로필 이미지 가져오기
        String profileSql = "SELECT userProfile FROM user WHERE userId = ?";
        pstmt = conn.prepareStatement(profileSql);
        pstmt.setString(1, userId);
        rs = pstmt.executeQuery();
        if (rs.next() && rs.getString("userProfile") != null) {
            profileImage = rs.getString("userProfile");
        }
        rs.close();
        pstmt.close();

        // 마이로그 데이터 가져오기
        String logSql = "SELECT title, image_url FROM posts WHERE userId = ? ORDER BY created_at DESC";
        pstmt = conn.prepareStatement(logSql);
        pstmt.setString(1, userId);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            mylogList.add(new String[]{rs.getString("title"), rs.getString("image_url")});
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
    }
%>

	<!-- 메인 컨테이너 -->
<div class="main-container">
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

    <div class="mylog-section">
        <div class="mylog-header">
            <h2>📄 마이로그</h2>
            <a href="postUpload.jsp" class="new-log-button">+ 새로 만들기</a>
        </div>

		<div class="mylog-list">
		    <% if (mylogList.isEmpty()) { %>
		        <p class="no-log">작성된 마이로그가 없습니다.</p>
		    <% } else { 
		        for (String[] log : mylogList) {
		            String[] images = log[1].split(",");  // 이미지 경로를 쉼표로 분리
		            String firstImage = images.length > 0 ? images[0] : "uploads/default.png";  // 첫 번째 이미지 경로만 사용
		    %>
		        <div class="log-item">
		            <img src="<%= firstImage %>" alt="마이로그 이미지" class="log-image">
		            <p class="log-title"><%= log[0] %></p>
		        </div>
		    <%  } } %>
		</div>


	</div>
</div>

<%@ include file="/resource/common/Footer.jsp" %>
</body>
</html>