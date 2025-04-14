package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;

@WebServlet("/CheckLoginStatus.do") // 반드시 요청 URL과 일치해야 함
public class CheckLoginStatusController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");

        response.setContentType("application/json; charset=UTF-8");
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("isLoggedIn", userId != null);
        response.getWriter().write(jsonResponse.toString());
    }
}
