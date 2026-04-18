package group02.filmout.entity;

import java.util.ArrayList;

public class Movie {
  // Attributes
  private boolean adult, video;
  private String backdrop_path, original_language, original_title, overview, poster_path, release_date, title;
  private ArrayList<String> genre_names;
  private int id_api, popularity, vote_count, id_hashmap;
  private float vote_average;

  // Constructor
  public Movie() {};
  public Movie(boolean adult, String backdrop_path, ArrayList<String> genre_names, int id_api,
                String original_language, String original_title, String overview, 
                int popularity, String poster_path, String release_date, String title,
                boolean video, float vote_average, int vote_count, int id_hashmap
              ) {
    
      this.adult = adult;
      this.backdrop_path = backdrop_path;
      this.genre_names = new ArrayList<>(genre_names);
      this.id_api = id_api;
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

  public ArrayList<String> getGenre_names() {
    return new ArrayList<>(genre_names);
  }

  public int getId() {
    return id_api;
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

  public void setGenre_names(ArrayList<String> genre_names) {
    this.genre_names = new ArrayList<>(genre_names);
  }

  public void setId_api(int id_api) {
    this.id_api = id_api;
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

  public String getYear() {
    if (release_date != null && release_date.length() >= 4) {
      return release_date.substring(0, 4);
    }
    return "";
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
    return id_api == movie.id_api;
  }
}
