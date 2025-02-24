import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays; 


public class MovieCollection {

    private ArrayList<Movie> movies;
    private Scanner scan;

    public MovieCollection() {
        movies = new ArrayList<>();
        scan = new Scanner(System.in);
    }
    
    
    public void getData() {

    }

    private void readData() {
        // TODO: write this method: load the shopping list data from your shoppinglist.txt file and populate shoppingList.
        //  note that this method gets called immediately at the start of the "start" method;
        //  you only need to read the data in one time to populate the shoppingList arraylist
        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String title = splitData[0];
                String cast = splitData[1];
                String[] castMembers = cast.split("\\|");
                ArrayList<String> newCast = new ArrayList<>(Arrays.asList(castMembers));
                splitData = data.split(",");
                String director = splitData[2];
                String overview = splitData[3];
                int runtime = Integer.parseInt(splitData[4]);
                double userRating = Double.parseDouble(splitData[5]);
                Movie m = new Movie(title,newCast,director,overview,runtime,userRating);
                movies.add(m);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void start() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }


    public void searchTitles() {
        System.out.println("Enter a title search term: ");
        String searchTerm = scan.nextLine();
        ArrayList<String> containTitles = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().contains(searchTerm)) {
                containTitles.add(movies.get(i).getTitle());
            }
        }
        sortWordList(containTitles);
        removeDuplicate(containTitles);
        printList(containTitles);
        System.out.println("Which movie would you like to learn more about? ");
        System.out.print("Enter number: ");
        int num = scan.nextInt();
        printMovieInfo(movies.get(num-1));
    }

    public void searchCast() {
        System.out.println("Enter a person to search for (first or last name): ");
        String searchCast = scan.nextLine();
        ArrayList<String> containCast = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies.get(i).getCast().size(); j++) {
                if (movies.get(i).getCast().get(j).contains(searchCast)) {
                    containCast.add(movies.get(i).getCast().get(j));
                }
            }
        }
        sortWordList(containCast);
        removeDuplicate(containCast);
        printList(containCast);
        System.out.println("Which would you like to see all movies for?");
        System.out.print("Enter number: ");
        int num = scan.nextInt();
    }

    public void sortWordList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            String temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }

    public void printList(ArrayList<String> list) {
        int counter = 1;
        for (String word : list) {
            System.out.println(counter + ". " + word);
            counter++;
        }
    }

    public void printMovieInfo(Movie movie) {
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Runtime: " + movie.getRuntime());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
    }
    
    public void removeDuplicate(ArrayList<String> list) { {
        for (int i = 0; i < list.size(); i++) {
            String current = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                if (current.equals(list.get(j))) {
                    list.remove(j);
                    j++; 
                }
            }
        }
    }
        
    }


}
