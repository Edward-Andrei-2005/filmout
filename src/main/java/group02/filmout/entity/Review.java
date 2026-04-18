package group02.filmout.entity;

public class Review {
  // Attributes
  private int id;
  private User user;
  private Movie movie;
  private int movieApiId;
  private float grade;
  private String comment;

  // Constructor
  public Review() {};
  public Review(User user, Movie movie, float grade) {
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

  public int getMovieApiId() {
    return movieApiId;
  }

  public void setMovieApiId(int movieApiId) {
    this.movieApiId = movieApiId;
  }

  public float getGrade() {
    return grade;
  }

  public void setGrade(float grade) {
    this.grade = grade;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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
