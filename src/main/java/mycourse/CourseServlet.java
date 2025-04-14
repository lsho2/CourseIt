package mycourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.JDBCUtil;

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
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

        // JSON 데이터 읽기
        String jsonData = request.getParameter("data"); // 쿼리 파라미터에서 JSON 데이터 가져오기
        if (jsonData == null || jsonData.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No data provided");
            return;
        }

        Gson gson = new Gson();
        Type markerListType = new TypeToken<List<Marker>>() {}.getType();
        List<Marker> markers = gson.fromJson(jsonData, markerListType);

        // 데이터베이스에 저장
        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"데이터베이스 연결 실패.\"}");
                return;
            }

            String sql = "INSERT INTO markers (userId, course_name, place_name, address, latitude, longitude) VALUES (? ,?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                System.out.println("JSON Data: " + jsonData);
                System.out.println("Parsed Markers: " + markers);
                for (Marker marker : markers) {
                    statement.setString(1, userId);  // 로그인한 사용자 ID를 저장
                    statement.setString(2, marker.getCourseName()); 
                    statement.setString(3, marker.getName());
                    statement.setString(4, marker.getAddress());
                    statement.setDouble(5, marker.getLatitude());
                    statement.setDouble(6, marker.getLongitude());


                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Data saved successfully!");
    }

    // Marker 클래스 정의
    public static class Marker {
        private String courseName;
        private String name;
        private String address;
        private double latitude;
        private double longitude;

        // Getters and Setters
        public String getCourseName() { return courseName; } 
        public void setCourseName(String courseName) { this.courseName = courseName; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
    }
}
