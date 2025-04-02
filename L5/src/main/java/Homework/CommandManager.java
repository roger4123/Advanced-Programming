package Homework;

import Compulsory.Image;
import Compulsory.ImageRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import freemarker.template.TemplateException;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


class AddImageCommand implements Command {
    private String name;
    private LocalDate date;
    private String filePath;
    private List<String> tags;

    public AddImageCommand(String name, LocalDate date, String filePath, List<String> tags) {
        this.name = name;
        this.date = date;
        this.filePath = filePath;
        this.tags = tags;
    }

    @Override
    public void executeCommand(ImageRepo repo) throws InvalidCommandException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new InvalidCommandException("File does not exist: " + filePath);
            }

            Image newImage = new Image(name, date, file, tags);
            repo.addImage(newImage);
        } catch (Exception e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }
}

class RemoveImageCommand implements Command {
    private String name;

    public RemoveImageCommand(String name) {
        this.name = name;
    }


    @Override
    public void executeCommand(ImageRepo repo) throws InvalidCommandException {
        Image imageDeleted = repo.findImage(name);
        if (imageDeleted == null) {
            throw new InvalidCommandException("Image does not exist: " + name);
        }

        if (!repo.deleteImage(imageDeleted)) {
            throw new InvalidCommandException("Failed to delete image: " + name);
        }
    }
}

class UpdateImageCommand implements Command {
    private String name;
    private LocalDate newDate;
    private List<String> newTags;
    private String newFilePath;

    public UpdateImageCommand(String name, LocalDate newDate, String newFilePath,  List<String> newTags) {
        this.name = name;
        this.newDate = newDate;
        this.newTags = newTags;
        this.newFilePath = newFilePath;
    }

    @Override
    public void executeCommand(ImageRepo repo) throws InvalidCommandException {
        Image originalImage = repo.findImage(name);
        if (originalImage == null) {
            throw new InvalidCommandException("Image does not exist: " + name);
        }

        try {
            LocalDate updatedDate;
            List<String> updatedTags;
            File updatedPath;

            if (newDate != originalImage.date()) {
                updatedDate = newDate;
            } else {
                updatedDate = originalImage.date();
            }

            if (newTags != originalImage.tags()) {
                updatedTags = newTags;
            } else {
                updatedTags = originalImage.tags();
            }

            if (!new File(newFilePath).equals(originalImage.location())) {
                updatedPath = new File(newFilePath);
            } else {
                updatedPath = originalImage.location();
            }

            System.out.println(updatedPath.getPath());
            /*if (!updatedPath.exists()) {
                throw new InvalidDataException("File does not exist: " + newFilePath);
            }*/

            Image updatedImage = new Image(name, updatedDate, updatedPath, updatedTags);

            repo.deleteImage(originalImage);
            repo.addImage(updatedImage);
        } catch (Exception e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }
}

class LoadImagesCommand implements Command {
    private String filePath;
    private String format;

    public LoadImagesCommand(String path, String format) {
        this.filePath = path;
        this.format = format;
    }

    private void loadFromJson(ImageRepo repository) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(filePath);
        List<Image> images = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Image.class));

        images.forEach(repository::addImage);
    }

    private void loadFromSerialized(ImageRepo repository) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Image> images = (List<Image>) ois.readObject();
            images.forEach(repository::addImage);
        } catch (IOException e) {
            System.err.println("Failed to load image from file " + filePath);
        }
    }

    private void loadFromText(ImageRepo repository) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Image image = new Image(parts[0], LocalDate.parse(parts[1]), new File(parts[2]), Arrays.stream(parts[3].split(":")).toList());
                    repository.addImage(image);
                }
            }
        }
    }

    @Override
    public void executeCommand(ImageRepo repo) throws InvalidCommandException {
        try {
            switch (format.toLowerCase()) {
                case "json":
                    loadFromJson(repo);
                    break;
                case "ser":
                    loadFromSerialized(repo);
                    break;
                case "txt":
                    loadFromText(repo);
                    break;
                default:
                    throw new InvalidCommandException("Unsupported format: " + format);
            }
        } catch (Exception e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }
}

class SaveImagesCommand implements Command {
    private String filePath;
    private String format;

    public SaveImagesCommand(String filePath, String format) {
        this.filePath = filePath;
        this.format = format;
    }

    @Override
    public void executeCommand(ImageRepo repository) throws InvalidCommandException {
        try {
            List<Image> images = repository.getImages();

            switch (format.toLowerCase()) {
                case "json":
                    saveToJson(images);
                    break;
                case "ser":
                    saveToSerialized(images);
                    break;
                case "txt":
                    saveToText(images);
                    break;
                default:
                    throw new InvalidCommandException("Unsupported file format: " + format);
            }
        } catch (Exception e) {
            throw new InvalidCommandException("Failed to save images: " + e.getMessage());
        }
    }

    private void saveToJson(List<Image> images) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(new File(filePath), images);
    }

    private void saveToSerialized(List<Image> images) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(images);
        }
    }

    private void saveToText(List<Image> images) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Image image : images) {
                // Format: name,date,filepath,tag1:tag2:tag3
                writer.write(String.format("%s,%s,%s,%s\n",
                        image.name(),
                        image.date(),
                        image.location().getAbsolutePath(),
                        String.join(":", image.tags())
                ));
            }
        }
    }
}

class ReportCommand implements Command {
    private String outputPath;

    public ReportCommand(String outputPath) {
        this.outputPath = "src/main/java/report.html";
    }

    @Override
    public void executeCommand(ImageRepo repository) throws InvalidCommandException {
        try {
            generateHtmlReport(repository);
            openReport();
        } catch (Exception e) {
            throw new InvalidCommandException("Failed to generate report: " + e.getMessage());
        }
    }

    private void generateHtmlReport(ImageRepo repository) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

        cfg.setClassForTemplateLoading(ReportCommand.class, "/templates");

        Template template = cfg.getTemplate("report-template.ftl");

        Map<String, Object> data = new HashMap<>();
        data.put("images", repository.getImages());
        data.put("title", "Image Collection Report");
        data.put("generatedDate", java.time.LocalDateTime.now().toString());

        File outputFile = new File(outputPath);
        try (Writer writer = new FileWriter(outputFile)) {
            template.process(data, writer);
        }
    }

    private void openReport() throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(new File(outputPath));
            } else {
                System.out.println("Report generated at: " + outputPath);
                System.out.println("Open action is not supported on this platform.");
            }
        } else {
            System.out.println("Report generated at: " + outputPath);
            System.out.println("Desktop is not supported on this platform.");
        }
    }
}