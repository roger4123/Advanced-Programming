package Homework;

import Compulsory.Project;
import Compulsory.Student;

public class Problem {
    private Student[] students;
    private Teacher[] teachers;
    private int studentCount;
    private int teacherCount;
    static final int DEFAULT_ARRAY_LENGTH = 10;

    /**
     * Constructor for Problem with initial arrays of students and teachers.
     * Creates new arrays to avoid external modification.
     */
    public Problem(Student[] students, Teacher[] teachers) {
        // Create new arrays with proper size
        this.students = new Student[students != null ? students.length : DEFAULT_ARRAY_LENGTH];
        this.teachers = new Teacher[teachers != null ? teachers.length : DEFAULT_ARRAY_LENGTH];

        // Copy elements and count valid entries
        if (students != null) {
            for (int i = 0; i < students.length; i++) {
                if (students[i] != null) {  
                    this.students[i] = students[i];
                    studentCount++;
                }
            }
        }

        if (teachers != null) {
            for (int i = 0; i < teachers.length; i++) {
                if (teachers[i] != null) {
                    this.teachers[i] = teachers[i];
                    teacherCount++;
                }
            }
        }
    }

    /**
     * Adds a student to the array if not already present.
     * Resizes the array if needed.
     */
    public void addStudent(Student student) {
        if (student == null) {
            return;
        }

        // Check if student already exists
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null && students[i].equals(student)) {
                System.out.println("Student already exists: " + student.getName());
                return;
            }
        }

        // Resize array if needed
        if (studentCount >= students.length) {
            Student[] newStudents = new Student[students.length * 2];
            System.arraycopy(students, 0, newStudents, 0, students.length);
            students = newStudents;
        }

        // Add the student
        students[studentCount] = student;
        studentCount++;
        System.out.println("Student added: " + student.getName());
    }

    /**
     * Adds a teacher to the array if not already present.
     * Resizes the array if needed.
     */
    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            return;
        }

        // Check if teacher already exists
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i] != null && teachers[i].equals(teacher)) {
                System.out.println("Teacher already exists: " + teacher.getName());
                return;
            }
        }

        // Resize array if needed
        if (teacherCount >= teachers.length) {
            Teacher[] newTeachers = new Teacher[teachers.length * 2];
            System.arraycopy(teachers, 0, newTeachers, 0, teachers.length);
            teachers = newTeachers;
        }

        // Add the teacher
        teachers[teacherCount] = teacher;
        teacherCount++;
        System.out.println("Teacher added: " + teacher.getName());
    }

    /**
     * Returns an array containing all persons (students and teachers).
     * Assumes Student and Teacher both extend Person.
     */
    public Person[] getAllPersons() {
        Person[] allPersons = new Person[studentCount + teacherCount];

        // Copy only the valid entries (not nulls)
        if (studentCount >= 0) System.arraycopy(students, 0, allPersons, 0, studentCount);

        if (teacherCount >= 0) System.arraycopy(teachers, 0, allPersons, studentCount, teacherCount);

        return allPersons;
    }

    /**
     * Allocates projects to students from teachers' proposed projects.
     * Each project can only be assigned once.
     */
    public void allocateProjects() {
        // Create a list of all available projects and their teachers
        int totalProjects = 0;
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i] != null) {
                Project[] projects = teachers[i].getProposedProjects();
                if (projects != null) {
                    totalProjects += projects.length;
                }
            }
        }

        Project[] allProjects = new Project[totalProjects];
        Teacher[] projectTeachers = new Teacher[totalProjects];
        boolean[] assignedProjects = new boolean[totalProjects];

        // Fill the projects array
        int projectIndex = 0;
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i] != null) {
                Project[] projects = teachers[i].getProposedProjects();
                if (projects != null) {
                    for (Project project : projects) {
                        if (project != null) {
                            allProjects[projectIndex] = project;
                            projectTeachers[projectIndex] = teachers[i];
                            projectIndex++;
                        }
                    }
                }
            }
        }

        // Assign projects to students
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null) {
                boolean assigned = false;
                for (int j = 0; j < projectIndex; j++) {
                    if (!assignedProjects[j]) {
                        System.out.println(students[i].getName() + " assigned to " +
                                allProjects[j].getName() + " (proposed by " +
                                projectTeachers[j].getName() + ")");
                        assignedProjects[j] = true;
                        assigned = true;
                        break;
                    }
                }

                if (!assigned) {
                    System.out.println("No available project for student: " + students[i].getName());
                }
            }
        }
    }

    /**
     * Returns the current number of students.
     */
    public int getStudentCount() {
        return studentCount;
    }

    /**
     * Returns the current number of teachers.
     */
    public int getTeacherCount() {
        return teacherCount;
    }
}