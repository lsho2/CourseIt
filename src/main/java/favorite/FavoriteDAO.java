package favorite;

import java.sql.*;
import java.util.*;
import db.JDBCUtil;

public class FavoriteDAO {

    public boolean addFavorite(FavoriteDTO favorite) {
        String sql = "INSERT INTO favorite (userId, title, link, image, date, place) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, favorite.getUserId());
            pstmt.setString(2, favorite.getTitle());
            pstmt.setString(3, favorite.getLink());
            pstmt.setString(4, favorite.getImage());
            pstmt.setString(5, favorite.getDate());
            pstmt.setString(6, favorite.getPlace());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in addFavorite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(String userId, String title) {
        String sql = "DELETE FROM favorite WHERE userId = ? AND title = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, title);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in removeFavorite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<FavoriteDTO> getFavoritesByUser(String userId) {
        String sql = "SELECT * FROM favorite WHERE userId = ?";
        List<FavoriteDTO> favorites = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                FavoriteDTO favorite = new FavoriteDTO(
                    rs.getString("userId"),
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("date"),
                    rs.getString("place")
                );
                favorites.add(favorite);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getFavoritesByUser: " + e.getMessage());
            e.printStackTrace();
        }
        return favorites;
    }
}
