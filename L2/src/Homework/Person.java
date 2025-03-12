package Homework;

public abstract class Person {
    protected String name;
    protected String dateOfBirth;

    public Person(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dob) {
        this.dateOfBirth = dob;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return name.equals(person.name) && dateOfBirth.equals(person.dateOfBirth);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', dateOfBirth=" + dateOfBirth + "}";
    }
}
