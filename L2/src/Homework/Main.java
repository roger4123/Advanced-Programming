package Homework;

import Compulsory.Project;
import Compulsory.Student;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("Alice", "20-05-2000", 1001);
        Student student2 = new Student("Bob", "06-11-2002", 1002);
        Student student3 = new Student("Carl", "11-08-2003", 1003);
        Student student4 = new Student("Dan", "19-01-2002", 1004);
        Student student5 = new Student("Emily", "19-09-2003", 1005);
        Student student6 = new Student("Felix", "19-09-2004", 1006);

        Project project1 = new Project("AI Research", Project.ProjectType.theoretical);
        Project project2 = new Project("Software Engineering", Project.ProjectType.practical);
        Project project3 = new Project("Computer Science", Project.ProjectType.theoretical);
        Project project4 = new Project("Advanced Programming", Project.ProjectType.practical);
        Project project5 = new Project("Computer Networks", Project.ProjectType.practical);

        Teacher teacher1 = new Teacher("Dr. Smith", "10-03-1975", new Project[]{project1, project3});
        Teacher teacher2 = new Teacher("Prof. Johnson", "18-07-1980", new Project[]{project2});
        Teacher teacher3 = new Teacher("Prof. Brown", "02-10-1989", new Project[]{project4, project5});


        Problem problem = new Problem(new Student[]{student1, student2, student3, student4, student5, student6}, new Teacher[]{teacher1, teacher2, teacher3});

        Person[] allPersons = problem.getAllPersons();
        for (Person allPerson : allPersons) {
            System.out.println(allPerson.toString());
        }

        System.out.println();
        problem.allocateProjects();


    }
}
