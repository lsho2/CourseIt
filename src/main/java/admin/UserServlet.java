package admin;

import beans.user.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/userServlet.do")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 요청 인코딩 설정
        response.setCharacterEncoding("UTF-8"); // 응답 인코딩 설정
        response.setContentType("text/html; charset=UTF-8"); // Content-Type 설정

        String action = request.getParameter("action");
        System.out.println("Action: " + action); // 디버깅 로그 출력

        if ("add".equals(action)) {
            addUser(request, response);
        } else if ("edit".equals(action)) {
            editUser(request, response);
        } else if ("update".equals(action)) { // Update 액션 추가
            updateUser(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        }
    }

    // 사용자 추가 메서드
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");

        UserDTO newUser = new UserDTO();
        newUser.setUserId(userId);
        newUser.setUserPw(userPw);
        newUser.setUserName(userName);
        newUser.setUserEmail(userEmail);

        if (userDao.userInsert(newUser)) {
            response.sendRedirect(request.getContextPath() + "/AdminPage/User_management.jsp");
        } else {
            response.getWriter().println("Error adding user.");
        }
    }

    // 수정 화면 표시 메서드
    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        UserDTO user = userDao.getUserInfo(userId);

        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/AdminPage/User_Edit.jsp").forward(request, response);
        } else {
            response.getWriter().println("User not found.");
        }
    }

    // 사용자 정보 업데이트 메서드
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userPw = request.getParameter("userPw");

        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserPw(userPw);

        if (userDao.updateUser(user)) {
            response.sendRedirect(request.getContextPath() + "/AdminPage/User_management.jsp");
        } else {
            response.getWriter().println("Error updating user.");
        }
    }

    // 사용자 삭제 메서드
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");

        if (userDao.deleteUser(userId)) {
            response.sendRedirect(request.getContextPath() + "/AdminPage/User_management.jsp");
        } else {
            response.getWriter().println("Error deleting user.");
        }
    }
}
