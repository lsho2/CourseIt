package favorite;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/MyfavoriteExhibition.do")
public class FavoriteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) request.getSession().getAttribute("userId");

        // 사용자 로그인 상태 확인
        if (userId == null || userId.trim().isEmpty()) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("{\"error\": \"User not logged in.\"}");
            System.out.println("DEBUG: Unauthorized access attempt.");
            return;
        }

        // DAO를 통해 사용자의 찜한 데이터 가져오기
        FavoriteDAO favoriteDAO = new FavoriteDAO();
        List<FavoriteDTO> favorites = favoriteDAO.getFavoritesByUser(userId);

        // 디버깅 메시지 출력
        System.out.println("DEBUG: User ID = " + userId);
        System.out.println("DEBUG: Number of favorites = " + favorites.size());

        // JSON 형식으로 변환 후 응답 반환
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(favorites);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
        System.out.println("DEBUG: Favorites JSON response sent.");
    }
}
