package favorite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/addFavorite.do")
public class AddFavoriteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String requestData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JsonObject json = JsonParser.parseString(requestData).getAsJsonObject();

        String userId = (String) request.getSession().getAttribute("userId");
        String title = json.get("title").getAsString();
        String link = json.get("link").getAsString();
        String image = json.get("image").getAsString();
        String date = json.get("date").getAsString();
        String place = json.get("place").getAsString();

        if (userId == null || title == null || link == null || image == null || date == null || place == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Missing required fields\"}");
            return;
        }

        FavoriteDTO favorite = new FavoriteDTO(userId, title, link, image, date, place);
        FavoriteDAO favoriteDAO = new FavoriteDAO();
        boolean result = favoriteDAO.addFavorite(favorite);

        if (result) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Failed to save favorite\"}");
        }
    }
}
