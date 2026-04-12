package group02.filmout.entity;

public class Review {
  // Attributes
  private int id;
  private User user;
  private Movie movie;
  private float grade;

  // Constructor
  public Review(int id, User user, Movie movie, float grade) {
    this.id = id;
    this.user = user;
    this.movie = movie;
    this.grade = grade;
  }

  // Methods
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public float getGrade() {
    return grade;
  }

  public void setGrade(float grade) {
    this.grade = grade;
  }

  @Override
  public String toString() {
    return "Review{" +
        "id=" + id +
        ", user=" + user +
        ", movie=" + movie +
        ", grade=" + grade +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Review review = (Review) o;
    return id == review.id;
  }
}
