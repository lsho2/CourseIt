package middleplace;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/SearchMemberServlet")
public class SearchMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/courseitdb?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {
	    	response.setContentType("application/json; charset=UTF-8"); // JSON 형식으로 응답
	        PrintWriter out = response.getWriter();
	        List<Member> dbMembers = new ArrayList<>();

        try {
            // 데이터베이스 드라이버 로드 및 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT name, place_name FROM members";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // 결과를 객체 리스트에 추가
            while (rs.next()) {
                String name = rs.getString("name");
                String placeName = rs.getString("place_name");
                dbMembers.add(new Member(name, placeName));
            }

            // JSON 문자열로 변환 후 응답 출력
            Gson gson = new Gson();
            String json = gson.toJson(dbMembers); // JSON 객체를 문자열로 변환
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\": \"An error occurred while retrieving members.\"}");
        } finally {
            out.close();
        }
    }
}


// Member 클래스 정의
class Member {
    private String name;
    private String placeName;

    public Member(String name, String placeName) {
        this.name = name;
        this.placeName = placeName;
    }

    public String getName() {
        return name;
    }

    public String getPlaceName() {
        return placeName;
    }
}
