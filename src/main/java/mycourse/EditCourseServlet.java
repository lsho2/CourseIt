package mycourse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.JDBCUtil;

@WebServlet("/EditCourseServlet")
public class EditCourseServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");

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

        // 쿼리 매개변수에서 oldName과 newName 가져오기
        String oldName = request.getParameter("oldCourseName");
        String newName = request.getParameter("newCourseName");

        // 매개변수 검증
        if (oldName == null || newName == null || oldName.trim().isEmpty() || newName.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid course names provided");
            return;
        }

        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"데이터베이스 연결 실패.\"}");
                return;
            }

            // 데이터베이스에서 코스 이름 업데이트 (사용자 ID도 조건에 추가)
            String sql = "UPDATE markers SET course_name = ? WHERE userId = ? AND course_name = ? ";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, newName);
                statement.setString(2, userId);     // 로그인한 사용자의 ID를 조건으로 추가
                statement.setString(3, oldName);


                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Course name updated successfully");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Course not found or user is not authorized to edit this course");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }
}
