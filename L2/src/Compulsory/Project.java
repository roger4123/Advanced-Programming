package Compulsory;

public class Project {
    public enum ProjectType {
        theoretical, practical
    }
    private String name;
    private ProjectType type;


    public Project(String title, ProjectType type) {
        this.name = title;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ProjectType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Projects{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

