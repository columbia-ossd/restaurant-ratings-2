import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class RatingAnalyzer {

    protected ReviewReader reviewReader;
    protected Set<Review> reviews;

    public RatingAnalyzer(String file) {
        reviews = new HashSet<>();
        reviewReader = new ReviewReader(file);
        List<Review> reviewList = reviewReader.readReviews();
        for (Review r : reviewList) {
            reviews.add(r);
        }
    }

    public RatingAnalyzer(String[] files) {
        reviews = new HashSet<>();

        for (int i = 0; i < files.length; i++) {
            reviewReader = new ReviewReader(files[i]);
            List<Review> reviewList = reviewReader.readReviews();
            for (Review r : reviewList) {
                reviews.add(r);
            }
        }
    }

    public RatingAnalyzer() {

    }

    public double averageRating(String cuisine) {

        double total = 0;
        int count = 0;

        for (Review r : reviews) {
            if (r.cuisine != null && r.cuisine.equals(cuisine)) {

                if (r.rating != null && r.rating.isBlank() == false) {
                    try {
                        double value = Double.parseDouble(r.rating);
                        if (value > 0) {
                            total += value;
                            count++;
                        }
                    } catch (NumberFormatException e) {
                        // skip non-numeric ratings
                    }
                }
            }
        }


        return total / count;
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: please provide at least one input file name.");
            return;
        }

    	RatingAnalyzer ra = new RatingAnalyzer(args);
    	String input = "";
    	Scanner in = new Scanner(System.in);
    	while (input.equalsIgnoreCase("quit") == false) {
    		System.out.print("Enter the cuisine type ('quit' to exit): ");
    		input = in.next();
    		if (input.equalsIgnoreCase("quit") == false) {
	    		System.out.println("Avg rating: " + ra.averageRating(input));
	    		System.out.println();
    		}
    	}
    	System.out.println("Good bye");
    }
}
