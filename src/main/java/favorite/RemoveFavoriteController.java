package favorite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/removeFavorite.do")
public class RemoveFavoriteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String requestData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JsonObject json = JsonParser.parseString(requestData).getAsJsonObject();

        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"User not logged in.\"}");
            return;
        }

        String title = json.get("title").getAsString();
        if (title == null || title.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid title.\"}");
            return;
        }

        FavoriteDAO favoriteDAO = new FavoriteDAO();
        boolean result = favoriteDAO.removeFavorite(userId, title);

        if (result) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Favorite removed successfully.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Favorite not found.\"}");
        }
    }
}
