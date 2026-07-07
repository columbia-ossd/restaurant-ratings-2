import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ReviewReader {

    protected String filename;

    public ReviewReader(String filename) {
        this.filename = filename;
    }

    public List<Review> readReviews() {

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File name is not valid");
        }

        List<Review> reviews = new LinkedList<>();

        try (Scanner in = new Scanner(new File(filename))) {

            String header = in.nextLine();
            String[] tok = header.split(",");

            int restaurantIndex = -1;
            int cuisineIndex = -1;
            int ratingIndex = -1;

            for (int i = 0; i < tok.length; i++) {
                if (tok[i].trim().equals("restaurant")) {
                    restaurantIndex = i;
                } else if (tok[i].trim().equals("cuisine")) {
                    cuisineIndex = i;
                } else if (tok[i].trim().equals("rating")) {
                    ratingIndex = i;
                }
            }

            if (restaurantIndex == -1 || cuisineIndex == -1 || ratingIndex == -1) {
                throw new IllegalStateException("CSV header is missing required columns");
            }

            while (in.hasNextLine()) {
                String line = in.nextLine();
                tok = line.split(",");

                if (tok.length <= restaurantIndex || tok.length <= cuisineIndex || tok.length <= ratingIndex) {
                    continue;
                }

                Review r = new Review();
                r.restaurant = tok[restaurantIndex].trim();
                r.cuisine = tok[cuisineIndex].trim();
                r.rating = tok[ratingIndex].trim();

                reviews.add(r);
            }

        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found: " + filename);
        }

        return reviews;
    }
}
