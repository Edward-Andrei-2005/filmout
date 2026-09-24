package group02.filmout.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Movie {

    private boolean adult;
    private boolean video;
    private String backdropPath;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private String title;
    private List<String> genreNames;
    private int apiId;
    private int popularity;
    private int voteCount;
    private int hashmapId;
    private float voteAverage;
    private List<Map<String, Object>> reviews = new ArrayList<>();

    public Movie() {}

    public Movie(boolean adult, String backdropPath, List<String> genreNames, int apiId,
                 String originalLanguage, String originalTitle, String overview,
                 int popularity, String posterPath, String releaseDate, String title,
                 boolean video, float voteAverage, int voteCount, int hashmapId) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.genreNames = genreNames != null ? new ArrayList<>(genreNames) : new ArrayList<>();
        this.apiId = apiId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.hashmapId = hashmapId;
    }

    public void setAdult(boolean adult) { this.adult = adult; }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public List<String> getGenreNames() { return genreNames != null ? new ArrayList<>(genreNames) : new ArrayList<>(); }
    public void setGenreNames(List<String> genreNames) { this.genreNames = genreNames != null ? new ArrayList<>(genreNames) : new ArrayList<>(); }

    public int getApiId() { return apiId; }
    public void setApiId(int apiId) { this.apiId = apiId; }

    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }

    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }

    public void setOverview(String overview) { this.overview = overview; }

    public void setPopularity(int popularity) { this.popularity = popularity; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public void setVideo(boolean video) { this.video = video; }

    public void setVoteAverage(float voteAverage) { this.voteAverage = voteAverage; }

    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }

    public int getHashmapId() { return hashmapId; }
    public void setHashmapId(int hashmapId) { this.hashmapId = hashmapId; }

    public void setReviews(List<Map<String, Object>> reviews) { this.reviews = reviews; }

    public String getYear() {
        if (releaseDate != null && releaseDate.length() >= 4) {
            return releaseDate.substring(0, 4);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return apiId == movie.apiId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiId);
    }
}