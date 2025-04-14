package beans.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editMyProfile.do")
public class EditMyProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 요청 파라미터 추출
        String userId = request.getParameter("userId"); // 수정 불가
        String userPw = request.getParameter("userPw");
        String pwdre = request.getParameter("pwdre");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");

        UserDAO userDao = new UserDAO();
        UserDTO existingUser = userDao.getUserInfo(userId); // 기존 사용자 정보 가져오기

        // 비밀번호 입력이 비어있으면 기존 비밀번호 유지
        String updatedPw = (userPw == null || userPw.isEmpty()) ? existingUser.getUserPw() : userPw;

        // 비밀번호 확인 검사 (비밀번호를 변경하려는 경우에만)
        if (!userPw.isEmpty() && !userPw.equals(pwdre)) {
            request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("EditMyProfile.jsp").forward(request, response);
            return;
        }

        // 입력값이 비어 있는 경우 기존 값 유지
        String updatedName = (userName == null || userName.isEmpty()) ? existingUser.getUserName() : userName;
        String updatedEmail = (userEmail == null || userEmail.isEmpty()) ? existingUser.getUserEmail() : userEmail;

        // DTO 생성 및 데이터 설정
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setUserPw(updatedPw);
        user.setUserName(updatedName);
        user.setUserEmail(updatedEmail);

        // DAO를 사용하여 정보 수정 처리
        boolean isUpdated = userDao.updateUser(user);

        if (isUpdated) {
            // 수정 성공 시 세션 업데이트 및 메인 페이지 이동
            request.getSession().setAttribute("userName", updatedName);
            request.getSession().setAttribute("userEmail", updatedEmail);
            response.sendRedirect("Main.jsp");
        } else {
            // 수정 실패 시 오류 메시지 전달
            request.setAttribute("errorMessage", "정보 수정에 실패했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("EditMyProfile.jsp").forward(request, response);
        }
    }
}
