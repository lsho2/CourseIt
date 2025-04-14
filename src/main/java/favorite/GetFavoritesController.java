package favorite;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getFavorites.do")
public class GetFavoritesController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        FavoriteDAO favoriteDAO = new FavoriteDAO();
        List<FavoriteDTO> favorites = favoriteDAO.getFavoritesByUser(userId);

        System.out.println("DEBUG: Favorites in controller = " + favorites.size()); // 디버깅 추가
        request.setAttribute("favorites", favorites);
        request.getRequestDispatcher("/MyFavorites.jsp").forward(request, response);
    }
}
