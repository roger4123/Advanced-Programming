package Compulsory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public record Image(String name, LocalDate date, File location, List<String> tags) {

    public Image(String name, LocalDate date, File location, List<String> tags) {
        this.name = name;
        this.date = date;
        this.tags = tags;
        this.location = location;
    }

    public void addTag(String tag) {
        if(!tags.contains(tag)) {
            tags.add(tag);
        } else {
            System.out.println("Tag " + tag + " already exists");
        }
    }

    public void removeTag(String tag) {
        if(tags.contains(tag)) {
            tags.remove(tag);
        } else {
            System.out.println("Tag " + tag + " does not exist");
        }
    }

    public void openImage() throws IOException {
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if(desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(location);
            } else {
                System.out.println("Open is not supported");
            }
        } else {
            System.out.println("Desktop is not supported");
        }
    }

    @Override
    public LocalDate date() {
        return date;
    }

    @Override
    public List<String> tags() {
        return tags;
    }

    @Override
    public File location() {
        return location;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", tags=" + tags +
                ", location=" + location.getAbsolutePath() +
                '}';
    }
}


