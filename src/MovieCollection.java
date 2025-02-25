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
        readData();
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
        System.out.print("Enter a title search term: ");
        String searchTerm = scan.nextLine();
        ArrayList<String> containTitles = new ArrayList<>();
        boolean moviesFound = false;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                containTitles.add(movies.get(i).getTitle());
                moviesFound = true;
            }
        }
        if (!(moviesFound)) {
            System.out.println();
            System.out.println("No movie titles match that search term!");
        }
        else {
            sortWordList(containTitles);
            removeDuplicate(containTitles);
            printList(containTitles);
            System.out.println("Which movie would you like to learn more about? ");
            System.out.print("Enter number: ");
            int num = scan.nextInt();
            scan.nextLine();
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equals(containTitles.get(num - 1))) {
                    printMovieInfo(movies.get(i));
                }
            }
        }
    }

    public void searchCast() {
        System.out.print("Enter a person to search for (first or last name): ");
        String searchCast = scan.nextLine();
        ArrayList<String> containCast = new ArrayList<>();
        boolean exist = false;
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies.get(i).getCast().size(); j++) {
                if (movies.get(i).getCast().get(j).toLowerCase().contains(searchCast.toLowerCase())) {
                    containCast.add(movies.get(i).getCast().get(j));
                    exist = true;
                }
            }
        }
        if (!(exist)) {
            System.out.println();
            System.out.println("No results match your search");
        } else {
            sortWordList(containCast);
            removeDuplicate(containCast);
            printList(containCast);
            System.out.println();
            System.out.println("Which would you like to see all movies for?");
            System.out.print("Enter number: ");
            int num = scan.nextInt();
            scan.nextLine();
            ArrayList<Movie> movieFound = findMovie(containCast.get(num - 1));
            sortMovieList(movieFound);
            printMovie(movieFound);
            System.out.println();
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int movieNum = scan.nextInt();
            scan.nextLine();
            for (int i = 0 ; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equals(movieFound.get(movieNum-1).getTitle())) {
                    printMovieInfo(movies.get(i));
                }
            }
        }
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
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Directed by: " + movie.getDirector());
        System.out.print("Cast: " );
        printArray(movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
    }
    
    public void removeDuplicate(ArrayList<String> list) { {
        for (int i = 0; i < list.size(); i++) {
            String current = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                if (current.equals(list.get(j))) {
                    list.remove(j);
                    j--;
                }
            }
        }
    }
        
    }

    public void printArray(ArrayList<String> array) {
        for (String members : array) {
            System.out.print(members + " ,");
        }
    }

    public void printMovie(ArrayList<Movie> movies) {
        int counter = 1;
        for (Movie movie : movies) {
            System.out.println(counter + ". " + movie.getTitle());
            counter++;
        }
    }

    public ArrayList<Movie> findMovie(String person) {

        ArrayList<Movie> movie = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            boolean isInMovie = false;
            Movie currentMovie = movies.get(i);
            for (int j = 0; j < currentMovie.getCast().size(); j++) {
                if (person.equals(currentMovie.getCast().get(j))) {
                    isInMovie = true;
                }
            }
            if (isInMovie) {
                movie.add(movies.get(i));
            }
        }
        return movie;
    }

    public void sortMovieList(ArrayList<Movie> list) {
        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getTitle().compareTo(list.get(minIndex).getTitle()) < 0) {
                    minIndex = j;
                }
            }
            Movie temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }







}
