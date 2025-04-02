package Homework;

import Compulsory.Image;
import Compulsory.ImageRepo;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ImageRepo repository = new ImageRepo();

        if (args.length == 0) {
            System.out.println("Adding sample images to repository...");
            try {
                Image sample1 = new Image(
                        "Beach",
                        LocalDate.now().minusDays(10),
                        new File("Photos/beach.jpg"),
                        Arrays.asList("beach", "summer")
                );

                Image sample2 = new Image(
                        "Mountain",
                        LocalDate.now().minusMonths(2),
                        new File("Photos/mountain.jpg"),
                        Arrays.asList("landscape", "hike")
                );

                repository.addImage(sample1);
                repository.addImage(sample2);
            } catch (Exception e) {
                System.out.println("Note: Sample images use placeholder paths. Use the 'add' command with valid file paths.");
            }
        }

        ImageShell shell = new ImageShell(repository);
        shell.start();
    }
}