package Compulsory;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageRepo repo = getImageRepo();

        Image findImage = repo.findImage("Sunset");
        Image findImage2 = repo.findImage("Error");

        if (findImage != null) {

            try {
                findImage.openImage();
                System.out.println(findImage.toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            try {
                findImage2.openImage();
                System.out.println(findImage2.toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static ImageRepo getImageRepo() {
        ImageRepo repo = new ImageRepo();

        Image img1 = new Image("Sunset", LocalDate.of(2024, 3, 25), new File("src/Photos/sunset.jpg"), new ArrayList<>(List.of("sun", "warm")));
        Image img2 = new Image("Mountain", LocalDate.of(2024, 3, 26), new File("src/Photos/mountain.jpg"), new ArrayList<>(List.of("hike", "fresh air")));
        Image img3 = new Image("Beach", LocalDate.of(2024, 3, 26), new File("src/Photos/beach.jpg"), new ArrayList<>(List.of("sand", "fun")));
        Image img4 = new Image("Rain", LocalDate.of(2024, 3, 25), new File("src/Photos/rain.jpg"), new ArrayList<>(List.of("umbrella")));

        img4.addTag("water");
        img1.removeTag("water");

        repo.addImage(img1);
        repo.addImage(img2);
        repo.addImage(img3);
        repo.addImage(img4);
        return repo;
    }
}