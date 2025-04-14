package mycourse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import db.JDBCUtil;
import mycourse.CourseServlet.Marker;

@WebServlet("/DeleteCourseServlet")
public class DeleteCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) request.getSession().getAttribute("userId");

        // 사용자 로그인 상태 확인
        if (userId == null || userId.trim().isEmpty()) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("{\"error\": \"User not logged in.\"}");
            System.out.println("DEBUG: Unauthorized access attempt.");
            return;
        }

        String courseName = request.getParameter("courseName");

        if (courseName == null || courseName.isEmpty()) {
            response.getWriter().write("{\"error\":\"코스 이름이 필요합니다.\"}");
            return;
        }

        // 데이터베이스 연결 및 삭제 처리
        try (Connection conn = JDBCUtil.getConnection()) {
            if (conn == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"데이터베이스 연결 실패.\"}");
                return;
            }

            // 코스 삭제 쿼리
            String query = "DELETE FROM markers WHERE userId = ? AND course_name = ?  ";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, userId); // 로그인한 사용자의 ID 추가
                pstmt.setString(2, courseName);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // 삭제 후 남아있는 코스를 반환하기 위해 markers 테이블에서 데이터 조회
                    List<Marker> markers = new ArrayList<>();
                    String sql = "SELECT course_name, place_name, address, latitude, longitude FROM markers WHERE userId = ?";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, userId); // 로그인한 사용자의 코스만 조회
                        try (ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                Marker marker = new Marker();
                                marker.setCourseName(resultSet.getString("course_name"));
                                marker.setName(resultSet.getString("place_name"));
                                marker.setAddress(resultSet.getString("address"));
                                marker.setLatitude(resultSet.getDouble("latitude"));
                                marker.setLongitude(resultSet.getDouble("longitude"));

                                markers.add(marker);
                            }
                        }
                    }

                    // JSON으로 반환
                    Gson gson = new Gson();
                    String json = gson.toJson(markers);
                    response.setContentType("application/json; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } else {
                    response.getWriter().write("{\"error\":\"코스를 찾을 수 없습니다.\"}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"서버 오류 발생.\"}");
        }
    }
}
