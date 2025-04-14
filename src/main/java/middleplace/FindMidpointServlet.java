package middleplace;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/FindMidpointServlet")
public class FindMidpointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/courseitdb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        List<Map<String, Object>> memberDetails = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, latitude, longitude FROM members")) {
            
            while (rs.next()) {
                String name = rs.getString("name");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                Map<String, Object> memberData = new HashMap<>();
                memberData.put("name", name);
                memberData.put("latitude", latitude);
                memberData.put("longitude", longitude);
                memberDetails.add(memberData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Database error\"}");
            return;
        }
        
        if (memberDetails.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No data available\"}");
            return;
        }
        
        double sumLatitude = 0;
        double sumLongitude = 0;
        int numOfMembers = memberDetails.size();

        for (Map<String, Object> member : memberDetails) {
            sumLatitude += (double) member.get("latitude");
            sumLongitude += (double) member.get("longitude");
        }

        double midpointLatitude = sumLatitude / numOfMembers;
        double midpointLongitude = sumLongitude / numOfMembers;

        // JSON 문자열을 생성하여 응답 출력
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{");
        jsonResponse.append(String.format("\"midpointLatitude\": %.6f, \"midpointLongitude\": %.6f, \"members\": [", 
            midpointLatitude, midpointLongitude));

        for (int i = 0; i < memberDetails.size(); i++) {
            Map<String, Object> member = memberDetails.get(i);
            jsonResponse.append(String.format(
                "{\"name\": \"%s\", \"latitude\": %.6f, \"longitude\": %.6f}",
                member.get("name"), member.get("latitude"), member.get("longitude")
            ));
            if (i < memberDetails.size() - 1) {
                jsonResponse.append(", ");
            }
        }
        jsonResponse.append("]}");

        out.print(jsonResponse.toString());
    }
}
