package group02.filmout.entity;

import java.util.ArrayList;

public class Movie {
  // Attributes
  private boolean adult, video;
  private String backdrop_path, original_language, original_title, overview, poster_path, release_date, title;
  private ArrayList<Integer> genre_ids;
  private int id, popularity, vote_count, id_hashmap;
  private float vote_average;

  // Constructor
  public Movie() {};
  public Movie(boolean adult, String backdrop_path, ArrayList<Integer> genre_ids, int id,
                String original_language, String original_title, String overview, 
                int popularity, String poster_path, String release_date, String title,
                boolean video, float vote_average, int vote_count, int id_hashmap
              ) {
    
      this.adult = adult;
      this.backdrop_path = backdrop_path;
      this.genre_ids = new ArrayList<>(genre_ids);
      this.id = id;
      this.original_language = original_language;
      this.original_title = original_title;
      this.overview = overview;
      this. popularity = popularity;
      this.poster_path = poster_path;
      this.release_date = release_date;
      this.title = title;
      this.video = video;
      this.vote_average = vote_average;
      this.vote_count = vote_count;
      this.id_hashmap = id_hashmap;
  }

  // Methods
  public boolean isAdult() {
    return adult;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public ArrayList<Integer> getGenre_ids() {
    return new ArrayList<>(genre_ids);
  }

  public int getId() {
    return id;
  }

  public String getOriginal_language() {
    return original_language;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public String getOverview() {
    return overview;
  }

  public int getPopularity() {
    return popularity;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public String getRelease_date() {
    return release_date;
  }

  public String getTitle() {
    return title;
  }

  public boolean isVideo() {
    return video;
  }

  public float getVote_average() {
    return vote_average;
  }

  public int getVote_count() {
    return vote_count;
  }

  public int getId_hashmap() {
    return id_hashmap;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }

  public void setGenre_ids(ArrayList<Integer> genre_ids) {
    this.genre_ids = new ArrayList<>(genre_ids);
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOriginal_language(String original_language) {
    this.original_language = original_language;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public void setPopularity(int popularity) {
    this.popularity = popularity;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public void setVote_average(float vote_average) {
    this.vote_average = vote_average;
  }

  public void setVote_count(int vote_count) {
    this.vote_count = vote_count;
  }

  public void setId_hashmap(int id_hashmap) {
    this.id_hashmap = id_hashmap;
  }


  /* 
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
  */

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Movie movie = (Movie) o;
    return id == movie.id;
  }
}
