package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkId.do")
public class CheckUserIdController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // 사용가능한 아이디인지 체크
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        UserDAO userDao = new UserDAO();
        boolean isDuplicate = userDao.idCheck(userId);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        if (isDuplicate) {
            response.getWriter().write("DUPLICATE");
        } else {
            response.getWriter().write("AVAILABLE");
        }
    }
}

