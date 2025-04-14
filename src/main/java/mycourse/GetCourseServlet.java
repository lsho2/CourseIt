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

@WebServlet("/GetCourseServlet")
public class GetCourseServlet extends HttpServlet {
    
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

        // 모든 코스를 조회하기 위한 리스트
        List<Marker> markers = new ArrayList<>();
        
        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"데이터베이스 연결 실패.\"}");
                return;
            }

            // 사용자 ID에 해당하는 데이터만 조회
            String sql = "SELECT course_name, place_name, address, latitude, longitude FROM markers WHERE userId = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId); // 사용자 ID로 필터링

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
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
            return;
        }

        // JSON으로 반환
        Gson gson = new Gson();
        String json = gson.toJson(markers);
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
