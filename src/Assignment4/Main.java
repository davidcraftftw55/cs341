package Assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Dictionary dictionary = new Dictionary();
        System.out.println(System.getProperty("user.dir"));

        Scanner input = new Scanner(new File("src/assignment4/input.txt"));
        while (input.hasNext()) {
            dictionary.addWord(input.next().toLowerCase());
        }
        System.out.println(dictionary);
    }
}
