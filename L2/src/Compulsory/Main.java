package Compulsory;

public class Main {
    public static void main(String[] args) {
        Project p1 = new Project("Computer Networks", Project.ProjectType.theoretical);
        Project p2 = new Project("Graph Algorithms", Project.ProjectType.theoretical);
        Project p3 = new Project("Java", Project.ProjectType.practical);
        Project p4 = new Project("Python", Project.ProjectType.practical);

        Student s1 = new Student("John", "01-04-2003", 1);
        Student s2 = new Student("Mary", "26-05-2003", 2);
        Student s3 = new Student("Alex", "17-12-2004", 3);
        Student s4 = new Student("Craig", "11-08-2002", 4);

        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s3.toString());
        System.out.println(s4.toString());

        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        System.out.println(p4.toString());
    }
}