
import java.util.Scanner;

public class MusicAlbum {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String albumName[] = new String[50];
        String artistName[] = new String[50];
        String genre[] = new String[50];
        String recordLabel[] = new String[50];
        String releaseDate[] = new String[50];

        String anotherAlbum;

        int albumCount = 0;

        System.out.println("Welcome to the Music Album Registration!\n");

        do {

            System.out.println("Please input the following:");

            albumName[albumCount] = InputString("Enter Album Name: ");
            artistName[albumCount] = InputString("Artist/Band Name: ");
            genre[albumCount] = InputString("Genre: ");
            recordLabel[albumCount] = InputString("Record Label: ");
            releaseDate[albumCount] = InputString("Release Date: ");

            albumCount++;
            System.out.print("Register another Album? (Y/N): ");
            anotherAlbum = sc.nextLine();

        } while (anotherAlbum.equalsIgnoreCase("y"));

        DisplayAlbum(albumName, artistName, genre, recordLabel, releaseDate, albumCount);

    }

    public static String InputString(String prompt) {
        System.out.println(prompt);
        String strInput = sc.nextLine();
        return strInput;
    }

    public static void DisplayAlbum(String[] albumData, String[] artistName, String[] genre, String[] recordLabel, String[] releaseDate, int albumCount) {

        for (int i = 0; i < albumCount; i++) {
            System.out.println("Album Name: " + albumData[i]);
            System.out.println("Artist/Band Name: " + artistName[i]);
            System.out.println("Genre: " + genre[i]);
            System.out.println("Record Label: " + recordLabel[i]);
            System.out.println("Release Date: " + releaseDate[i]);
        }

        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "Album", "Artist", "Genre", "Record", "Released");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < albumCount; i++) {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", albumData[i], artistName[i], genre[i], recordLabel[i], releaseDate[i]);
        }
    }
}
