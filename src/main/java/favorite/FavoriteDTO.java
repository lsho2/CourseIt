package favorite;

public class FavoriteDTO {
    private String userId;
    private String title;
    private String link;
    private String image;
    private String date;
    private String place;

    public FavoriteDTO(String userId, String title, String link, String image, String date, String place) {
        this.userId = userId;
        this.title = title;
        this.link = link;
        this.image = image;
        this.date = date;
        this.place = place;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
