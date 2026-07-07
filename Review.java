public class Review {

    public String restaurant;
    public String cuisine;
    public String rating;

    public Review(String restaurant, String cuisine, String rating) {
        this.restaurant = restaurant;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Review() {

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj instanceof Review == false) return false;
        Review other = (Review) obj;
        return other.restaurant != null && other.restaurant.equals(this.restaurant)
                && other.cuisine != null && other.cuisine.equals(this.cuisine)
                && other.rating != null && other.rating.equals(this.rating);
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        if (restaurant != null) hashcode += restaurant.hashCode();
        if (cuisine != null) hashcode += cuisine.hashCode();
        if (rating != null) hashcode += rating.hashCode();
        return hashcode;
    }

    @Override
    public String toString() {
        return "restaurant: " + restaurant + ", cuisine: " + cuisine + ", rating: " + rating;
    }
}
