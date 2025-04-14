package middleplace;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

@WebServlet("/DeleteMemberServlet")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/courseitdb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");


        if (name == null || name.isEmpty()) {
            response.getWriter().write("{\"error\":\"멤버 이름이 필요합니다.\"}");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 멤버 삭제 쿼리
            String deleteQuery = "DELETE FROM members WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setString(1, name);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // 삭제 후 남아있는 멤버 반환
                    response.setContentType("application/json; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");

                    List<String> members = new ArrayList<>();
                    String selectQuery = "SELECT name FROM members";
                    try (PreparedStatement statement = conn.prepareStatement(selectQuery);
                         ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            members.add(resultSet.getString("name"));
                        }
                    }

                    // JSON으로 반환
                    Gson gson = new Gson();
                    String json = gson.toJson(members);
                    response.getWriter().write(json);
                } else {
                    response.getWriter().write("{\"error\":\"멤버를 찾을 수 없습니다.\"}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"서버 오류 발생.\"}");
        }
    }
}
