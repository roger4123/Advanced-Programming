package Compulsory;

import java.util.Objects;
import Homework.Person;

public class Student extends Person {
    private int registrationNumber;

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

    @Override
    public String toString() {
        return "Student{" +
                "registrationNumber=" + registrationNumber +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}

