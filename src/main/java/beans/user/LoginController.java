package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login.do")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        UserDAO userDAO = new UserDAO();
        boolean isValidUser = userDAO.loginCheck(userId, userPw);
        System.out.println("DEBUG: loginCheck result = " + isValidUser);

        if (isValidUser) {
            String userName = userDAO.getUserName(userId); // DB에서 userName 가져오기
            String userEmail = userDAO.getUserEmail(userId); // DB에서 userName 가져오기

            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("userName", userName);
            session.setAttribute("userEmail", userEmail);

            response.sendRedirect(request.getContextPath() + "/Main.jsp");
        } else {
            System.out.println("DEBUG: Invalid login attempt for userId = " + userId); // 디버깅 출력
            request.setAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }

    }

}