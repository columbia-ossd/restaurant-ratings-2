import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;

public class RatingAnalyzer {

    protected ReviewReader reviewReader;
    protected Set<Review> reviews;

    public RatingAnalyzer(String file) {
        reviewReader = new ReviewReader(file);
        List<Review> reviewList = reviewReader.readReviews();
        reviews = new HashSet<>();
        for (Review r : reviewList) {
            reviews.add(r);
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

    public void topRated(String cuisine) {

        double max = -1;
        boolean found = false;

        for (Review r : reviews) {
            if (r.cuisine != null && r.cuisine.equals(cuisine)) {

                if (r.rating != null && r.rating.isBlank() == false) {
                    try {
                        double value = Double.parseDouble(r.rating);
                        if (value > 0) {
                            found = true;
                            if (value > max) {
                                max = value;
                            }
                        }
                    } catch (NumberFormatException e) {
                        // skip non-numeric ratings
                    }
                }
            }
        }

        if (found == false) {
            System.out.println("No " + cuisine + " restaurants found.");
            return;
        }

        List<Review> topRated = new ArrayList<>();

        for (Review r : reviews) {
            if (r.cuisine != null && r.cuisine.equals(cuisine)) {

                if (r.rating != null && r.rating.isBlank() == false) {
                    try {
                        double value = Double.parseDouble(r.rating);
                        if (value == max) {
                            topRated.add(r);
                        }
                    } catch (NumberFormatException e) {
                        // skip non-numeric ratings
                    }
                }
            }
        }

        for (int i = 0; i < topRated.size() - 1; i++) {
            for (int j = i + 1; j < topRated.size(); j++) {
                if (topRated.get(i).restaurant.compareTo(topRated.get(j).restaurant) > 0) {
                    Review temp = topRated.get(i);
                    topRated.set(i, topRated.get(j));
                    topRated.set(j, temp);
                }
            }
        }

        System.out.println("Top-rated " + cuisine + " restaurant(s):");
        for (Review r : topRated) {
            System.out.println(r.restaurant + ": " + r.rating);
        }
    }

    public static void main(String[] args) {
    	RatingAnalyzer ra = new RatingAnalyzer("reviews.csv");
    	String input = "";
    	Scanner in = new Scanner(System.in);
    	while (input.equalsIgnoreCase("quit") == false) {
    		System.out.print("Enter the cuisine type ('quit' to exit): ");
    		input = in.next();
    		if (input.equalsIgnoreCase("quit") == false) {
	    		System.out.println("Avg rating: " + ra.averageRating(input));
	    		ra.topRated(input);
	    		System.out.println();
    		}
    	}
    	System.out.println("Good bye");
    }
}
