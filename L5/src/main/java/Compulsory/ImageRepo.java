package Compulsory;

import java.util.ArrayList;
import java.util.List;

public class ImageRepo {
    private List<Image> images;

    public ImageRepo() {
        images = new ArrayList<Image>();
    }

    public void addImage(Image image) {
        if (!images.contains(image)) {
            images.add(image);
            System.out.println("Image " + image.name() + " added successfully");
        } else {
            System.out.println("Image " + image.name() + " already exists");
        }
    }

    public List<Image> getImages() {
        return new ArrayList<>(images);
    }

    public Image findImage(String name) {
        return images.stream().filter(image -> image.name().equals(name)).findFirst().orElse(null);
    }

    public boolean deleteImage(Image image) {
        return images.remove(image);
    }
}
