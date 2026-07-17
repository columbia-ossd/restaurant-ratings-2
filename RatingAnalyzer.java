import java.util.ArrayList;
import java.util.Comparator;
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
    
    public List<Review> getRestaurantsByCuisine(String cuisine) {
        List<Review> matches = new ArrayList<>();

        for (Review r : reviews) {
            if (r.cuisine != null && r.cuisine.equals(cuisine)) {
                matches.add(r);
            }
        }

        matches.sort(new Comparator<Review>() {
            @Override
            public int compare(Review r1, Review r2) {
                return r1.restaurant.compareToIgnoreCase(r2.restaurant);
            }
        });

        return matches;
    }

    public void printRestaurantsByCuisine(String cuisine) {
        List<Review> matches = getRestaurantsByCuisine(cuisine);

        if (matches.size() == 0) {
            System.out.println("No restaurants found for cuisine: " + cuisine);
        } else {
            System.out.println("Restaurants for " + cuisine + ":");
            for (Review r : matches) {
                System.out.println(r.restaurant + ": " + r.rating);
            }
        }
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
                List<Review> matches = ra.getRestaurantsByCuisine(input);

	    		if (matches.size() == 0) {
                    System.out.println("No restaurants found for cuisine: " + input);
                } else {
                    ra.printRestaurantsByCuisine(input);
                    System.out.println("Avg rating: " + ra.averageRating(input));
                    ra.topRated(input);
                }
                
                System.out.println();
    		}
    	}

        in.close();
    	System.out.println("Good bye");
    }
}
