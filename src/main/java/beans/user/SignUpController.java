package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signUp.do")
public class SignUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	// 요청 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    	
        // 요청 파라미터로부터 사용자 정보 추출
        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("userEmail");
        String userPw = request.getParameter("userPw");
        String pwdre = request.getParameter("pwdre");
        String userName = request.getParameter("userName");

        // 비밀번호 확인
        if (!userPw.equals(pwdre)) {
            // 비밀번호가 일치하지 않는 경우
            request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("Sign_up.jsp").forward(request, response);
            return;
        }

        // DTO 생성 및 데이터 설정
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setUserEmail(userEmail);
        user.setUserPw(userPw);
        user.setUserName(userName);

        // DAO를 사용하여 회원가입 처리
        UserDAO userDao = new UserDAO();
        boolean isInserted = userDao.userInsert(user);

        if (isInserted) {
            // 회원가입 성공
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            // 회원가입 실패
            request.setAttribute("errorMessage", "회원가입에 실패했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("Sign_up.jsp").forward(request, response);
        }
    }
}
