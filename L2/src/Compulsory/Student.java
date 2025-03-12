package Compulsory;

import java.util.Objects;
import Homework.Person;

public class Student extends Person {
    private int registrationNumber;
    private Project[] acceptableProjects;

    public Student(String name, String dateOfBirth, int registrationNumber) {
        super(name, dateOfBirth);
        this.registrationNumber = registrationNumber;
    }


    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Project[] getAcceptableProjects() {
        return acceptableProjects;
    }

    public void setAcceptableProjects(Project[] acceptableProjects) {
        this.acceptableProjects = acceptableProjects;
    }

    public boolean isAcceptable(Project project) {
        for (Project p : acceptableProjects) {
            if (p.equals(project)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student{" +
                "registrationNumber=" + registrationNumber +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}

