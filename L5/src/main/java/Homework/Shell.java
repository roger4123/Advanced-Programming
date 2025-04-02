package Homework;

import Compulsory.ImageRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

class CommandParser {
    public static Command parseCommand(String input) throws InvalidCommandException {
        String[] tokens = input.trim().split("\\s+", 2);

        if (tokens.length == 0 || tokens[0].isEmpty()) {
            throw new InvalidCommandException("No command provided");
        }

        String commandName = tokens[0].toLowerCase();
        String args = tokens.length > 1 ? tokens[1] : "";

        switch (commandName) {
            case "add":
                return parseAddCommand(args);
            case "remove":
                return parseRemoveCommand(args);
            case "update":
                return parseUpdateCommand(args);
            case "load":
                return parseLoadCommand(args);
            case "save":
                return parseSaveCommand(args);
            case "report":
                return parseReportCommand(args);
            case "exit":
                System.exit(0);
            case "help":
                printHelp();
                return null;
            default:
                throw new InvalidCommandException("Unknown command: " + commandName);
        }
    }

    private static AddImageCommand parseAddCommand(String args) throws InvalidCommandException {
        String[] parts = args.split("\\s+", 4);
        if (parts.length < 4) {
            throw new InvalidCommandException(
                    "Invalid add command. Usage: add <name> <date> <file_path> <tag1,tag2,...>");
        }

        String name = parts[0];

        LocalDate date;
        try {
            date = LocalDate.parse(parts[1], DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException("Invalid date format. Use ISO format (YYYY-MM-DD)");
        }

        String filePath = parts[2];
        List<String> tags = Arrays.asList(parts[3].split(","));

        return new AddImageCommand(name, date, filePath, tags);
    }

    private static RemoveImageCommand parseRemoveCommand(String args) throws InvalidCommandException {
        if (args.isEmpty()) {
            throw new InvalidCommandException("Invalid remove command. Usage: remove <name>");
        }

        return new RemoveImageCommand(args.trim());
    }

    private static UpdateImageCommand parseUpdateCommand(String args) throws InvalidCommandException {
        String[] parts = args.split("\\s+", 2);
        if (parts.length < 1) {
            throw new InvalidCommandException(
                    "Invalid update command. Usage: update <name> [date=<new_date>] [path=<new_path>] [tags=<tag1,tag2,...>]");
        }

        String name = parts[0];
        String params = parts.length > 1 ? parts[1] : "";

        LocalDate newDate = null;
        String newFilePath = "";
        List<String> newTags = List.of();

        for (String param : params.split("\\s+")) {
            if (param.startsWith("date=")) {
                try {
                    String dateStr = param.substring(5);
                    newDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
                } catch (DateTimeParseException e) {
                    throw new InvalidCommandException("Invalid date format. Use ISO format (YYYY-MM-DD)");
                }
            } else if (param.startsWith("path=")) {
                newFilePath = param.substring(5);
            } else if (param.startsWith("tags=")) {
                String tagsStr = param.substring(5);
                newTags = Arrays.asList(tagsStr.split(","));
            }
        }

        return new UpdateImageCommand(name, newDate, newFilePath, newTags);
    }

    private static LoadImagesCommand parseLoadCommand(String args) throws InvalidCommandException {
        String[] parts = args.split("\\s+", 2);
        if (parts.length < 2) {
            throw new InvalidCommandException(
                    "Invalid load command. Usage: load <file_path> <format>");
        }

        String filePath = parts[0];
        String format = parts[1].toLowerCase();

        if (!Arrays.asList("json", "ser", "txt").contains(format)) {
            throw new InvalidCommandException("Unsupported format. Use json, ser, or txt");
        }

        return new LoadImagesCommand(filePath, format);
    }

    private static SaveImagesCommand parseSaveCommand(String args) throws InvalidCommandException {
        String[] parts = args.split("\\s+", 2);
        if (parts.length < 2) {
            throw new InvalidCommandException(
                    "Invalid save command. Usage: save <file_path> <format>");
        }

        String filePath = parts[0];
        String format = parts[1].toLowerCase();

        if (!Arrays.asList("json", "ser", "txt").contains(format)) {
            throw new InvalidCommandException("Unsupported format. Use json, ser, or txt");
        }

        return new SaveImagesCommand(filePath, format);
    }

    private static ReportCommand parseReportCommand(String args) throws InvalidCommandException {
        if (args.isEmpty()) {
            throw new InvalidCommandException(
                    "Invalid report command. Usage: report <output_path>");
        }

        return new ReportCommand(args.trim());
    }

    private static void printHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("  add <name> <date> <file_path> <tag1,tag2,...>");
        System.out.println("  remove <name>");
        System.out.println("  update <name> [date=<new_date>] [path=<new_path>] [tags=<tag1,tag2,...>]");
        System.out.println("  load <file_path> <format>");
        System.out.println("  save <file_path> <format>");
        System.out.println("  report <output_path>");
        System.out.println("  help");
        System.out.println("  exit");
    }
}

// Interactive Shell
class ImageShell {
    private ImageRepo repository;
    private BufferedReader reader;

    public ImageShell(ImageRepo repository) {
        this.repository = repository;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        System.out.println("Image Management Shell");
        System.out.println("Type 'help' for available commands");

        while (true) {
            try {
                System.out.print("> ");
                String input = reader.readLine();

                if (input == null || input.trim().equalsIgnoreCase("exit")) {
                    break;
                }

                Command command = CommandParser.parseCommand(input);
                if (command != null) {
                    command.executeCommand(repository);
                }
            } catch (InvalidCommandException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}
