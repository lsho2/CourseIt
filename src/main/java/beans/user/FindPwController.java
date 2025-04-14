package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 서블릿 매핑
@WebServlet("/FindPwController")
public class FindPwController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터 가져오기
        String userId = request.getParameter("id");
        String userEmail = request.getParameter("email");

        // DAO를 이용하여 비밀번호 찾기
        UserDAO userDAO = new UserDAO();
        try {
            String userPw = userDAO.findUserPassword(userId, userEmail);

            if (userPw != null) {
                // 비밀번호 찾기 성공
                request.setAttribute("userPw", userPw);
                request.getRequestDispatcher("/FindPwResult.jsp").forward(request, response);
            } else {
                // 비밀번호 찾기 실패
                request.setAttribute("message", "입력하신 정보에 해당하는 비밀번호를 찾을 수 없습니다.");
                request.getRequestDispatcher("/FindPw.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "비밀번호 찾기 중 오류가 발생했습니다.");
            request.getRequestDispatcher("/FindPw.jsp").forward(request, response);
        }
    }
}
