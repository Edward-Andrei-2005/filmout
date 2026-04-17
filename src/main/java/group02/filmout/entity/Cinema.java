package group02.filmout.entity;

public class Cinema {
  // Attributes
  private int id;
  private String name, latitude, longitude;

  // Constructor
  public Cinema() {};
  public Cinema(String name, String latitude, String longitude) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  // Methods
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return "Cinema{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", latitude='" + latitude + '\'' +
        ", longitude='" + longitude + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Cinema cinema = (Cinema) o;
    return id == cinema.id;
  }
}
