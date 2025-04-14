package middleplace;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/AddMemberServlet")
public class AddMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    // 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/courseitdb?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	response.setContentType("text/plain;charset=UTF-8"); // JSON 대신 단순 텍스트 반환
        PrintWriter out = response.getWriter();
        
        // 요청 파라미터 가져오기
        String attendeeName = request.getParameter("attendee_name"); // 참석자 이름
        String placeName = request.getParameter("place_name");
        String address = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        
        // 디버깅 로그 추가
        System.out.println("Received data:");
        System.out.println("attendee_name: " + attendeeName);
        System.out.println("place_name: " + placeName);
        System.out.println("address: " + address);
        System.out.println("latitude: " + latitude);
        System.out.println("longitude: " + longitude);
        
        // DB 연결 및 쿼리 실행
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO members (name, place_name, address, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, attendeeName);
                stmt.setString(2, placeName);
                stmt.setString(3, address);
                stmt.setDouble(4, Double.parseDouble(latitude)); // 위도를 DOUBLE로 변환
                stmt.setDouble(5, Double.parseDouble(longitude)); // 경로를 DOUBLE로 변환

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    out.print("SUCCESS");
                } else {
                    out.print("FAIL");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR: " + e.getMessage());
        } finally {
            out.close();
        }
    }
}
