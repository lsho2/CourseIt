<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*" session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="resource/css/pages/daylog.css">
<title>Course it! - Posts</title>
</head>
<body>
    <%@ include file="resource/common/Header.jsp" %>
    <div id='travel'>
    <h1>여행기록들</h1>
    <table border="1" cellpadding="10" cellspacing="0">
        <thead>
            <tr>

                <th>작성자</th>
                <th>제목</th>
                <th>Content</th>
                <th>Image</th>
                <th>작성일</th>
                <th>수정시</th>
            </tr>
        </thead>
        <tbody>
        <%
            // 데이터베이스 연결 정보
            String url = "jdbc:mysql://localhost:3306/courseitdb";
            String user = "root";
            String password = "password";

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                // JDBC 드라이버 로드
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 데이터베이스 연결
                conn = DriverManager.getConnection(url, user, password);

                // SQL 쿼리 실행
                String sql = "SELECT * FROM posts";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                // 결과 출력
                while (rs.next()) {
                    String puserId = rs.getString("userId");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String imageUrl = rs.getString("image_url");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp updatedAt = rs.getTimestamp("updated_at");
        %>
            <tr>
                <td><%= puserId %></td>
                <td><%= title %></td>
                <td><%= content %></td>
                <td>
                    <% if (imageUrl != null && !imageUrl.isEmpty()) { %>
                        <img src="<%= imageUrl %>" alt="Post Image" width="100">
                    <% } else { %>
                        No Image
                    <% } %>
                </td>
                <td><%= createdAt %></td>
                <td><%= updatedAt %></td>
            </tr>
        <%
                }
            } catch (Exception e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            } finally {
                // 자원 정리
                if (rs != null) try { rs.close(); } catch (SQLException e) { }
                if (stmt != null) try { stmt.close(); } catch (SQLException e) { }
                if (conn != null) try { conn.close(); } catch (SQLException e) { }
            }
        %>
        </tbody>
    </table>
	</div>
    <%@ include file="/resource/common/Footer.jsp" %>
</body>
</html>
