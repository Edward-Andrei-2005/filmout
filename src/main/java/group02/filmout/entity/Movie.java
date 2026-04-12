package group02.filmout.entity;

public class Movie {
    // Attributes
    private int id, duration, year, grade;
    private String title, gender, description, cover;
    // ****eventList****

    // Constructor
    public Movie(int id, int duration, int year, int grade, String title, String gender, 
        String description, String cover) {
        this.id = id;
        this.duration = duration;
        this.year = year;
        this.grade = grade;
        this.title = title;
        this.gender = gender;
        this.description = description; 
        this.cover = cover;
    }

    // Methods
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", duration=" + duration +
                ", year=" + year +
                ", grade=" + grade +
                ", title='" + title + '\'' +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;
        return id == movie.id;
    }
}
