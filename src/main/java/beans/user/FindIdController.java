package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// FindIdController 서블릿 매핑
@WebServlet("/FindIdController")
public class FindIdController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 입력 받은 이메일
        String userEmail = request.getParameter("email");
        
        // 유저 정보를 처리하는 DAO 객체 생성
        UserDAO userDAO = new UserDAO();
        
        try {
            // 이메일로 사용자 아이디 찾기
            String userId = userDAO.findUserIdByEmail(userEmail);
            
            if (userId != null) {
                // 아이디를 찾은 경우
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("/FindIdResult.jsp").forward(request, response);
            } else {
                // 아이디를 찾지 못한 경우
                request.setAttribute("message", "입력하신 이메일에 해당하는 아이디를 찾을 수 없습니다.");
                request.getRequestDispatcher("/FindId.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "아이디 찾기 중 오류가 발생했습니다.");
            request.getRequestDispatcher("/FindId.jsp").forward(request, response);
        }
    }
}

