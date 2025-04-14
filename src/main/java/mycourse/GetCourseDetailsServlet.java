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

@WebServlet("/GetCourseDetailsServlet")
public class GetCourseDetailsServlet extends HttpServlet {
	
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
            return;
        }

        String courseName = request.getParameter("courseName");

        if (courseName == null || courseName.isEmpty()) {
            response.getWriter().write("{\"error\":\"코스 이름이 필요합니다.\"}");
            return;
        }

        List<Marker> courseDetails = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"데이터베이스 연결 실패.\"}");
                return;
            }

            // 사용자 ID와 코스 이름을 기준으로 조회
            String sql = "SELECT course_name, place_name, address FROM markers WHERE userId = ? AND course_name = ?  ";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId); // 사용자 ID를 추가하여 해당 사용자의 코스만 조회
                statement.setString(2, courseName);


                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Marker marker = new Marker();
                        marker.setName(resultSet.getString("place_name"));
                        marker.setAddress(resultSet.getString("address"));

                        courseDetails.add(marker);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
            return;
        }

        // JSON으로 반환
        Gson gson = new Gson();
        String json = gson.toJson(courseDetails);
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
