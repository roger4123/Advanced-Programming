package Homework;

class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}

class ImageNotFoundException extends InvalidCommandException {
    public ImageNotFoundException(String message) {
        super(message);
    }
}

class InvalidImageException extends InvalidCommandException {
    public InvalidImageException(String message) {
        super("Image not found: " + message);
    }
}

class InvalidDataException extends InvalidCommandException {
    public InvalidDataException(String message) {
        super(message);
    }
}
