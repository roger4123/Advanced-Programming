package Homework;

import Compulsory.ImageRepo;

public interface Command {
    void executeCommand(ImageRepo repo) throws InvalidCommandException;
}
