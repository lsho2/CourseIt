package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/editProfilePage.do")
public class EditProfilePageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 세션에서 현재 로그인된 사용자 ID 가져오기
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            // 로그인 상태가 아니면 로그인 페이지로 리디렉션
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        // 사용자 정보를 데이터베이스에서 조회
        UserDAO userDAO = new UserDAO();
        UserDTO user = userDAO.getUserInfo(userId);

        if (user != null) {
            // 조회한 정보를 JSP로 전달
            request.setAttribute("user", user);
        }

        // EditProfile.jsp로 포워드
        request.getRequestDispatcher("/EditProfile.jsp").forward(request, response);
    }
}
