package es.cheste.UD1.practica;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Personaje implements Comparable {
    private String name;
    private String height;
    private String mass;
    @SerializedName("hair_color")
    private String hairColor;
    @SerializedName("skin_color")
    private String skinColor;
    @SerializedName("eye_color")
    private String eyeColor;
    @SerializedName("birth_year")
    private String birthYear;
    private String gender;
    private String homeworld;

    private List<String> films;
    private List<String> species;
    private List<String> vehicles;
    private List<String> starships;

    private String created;
    private String edited;
    private String url;


    public Personaje() {
        super();
    }

    public Personaje(String name, String height, String mass, String hairColor, String skinColor, String eyeColor,
                     String birthYear, String gender, String homeworld, List<String> films, List<String> species,
                     List<String> vehicles, List<String> starships, String created, String edited, String url) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.hairColor = hairColor;
        this.skinColor = skinColor;
        this.eyeColor = eyeColor;
        this.birthYear = birthYear;
        this.gender = gender;
        this.homeworld = homeworld;
        this.films = films;
        this.species = species;
        this.vehicles = vehicles;
        this.starships = starships;
        this.created = created;
        this.edited = edited;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hair_color) {
        this.hairColor = hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

   public void setSkinColor(String skin_color) {
        this.skinColor = skinColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getStarships() {
        return starships;
    }

    public void setStarships(List<String> starships) {
        this.starships = starships;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public boolean noHaConducidoVehiculo() {

        return vehicles.isEmpty();

    }

    public int cantidadPeliculas(){

        return films.size();
    }

    private int cantidadVehiculos(){

        if(!noHaConducidoVehiculo()){
            return vehicles.size();
        }
        return 0;
    }

    public String[] devolverElementos(){

        return new String[]{name,mass,url};
    }

    public String[] devolverAtributos(){

        return new String[]{String.valueOf(cantidadPeliculas()),String.valueOf(cantidadVehiculos())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personaje personaje = (Personaje) o;
        return Objects.equals(name, personaje.name) && Objects.equals(height, personaje.height) && Objects.equals(mass, personaje.mass) && Objects.equals(hairColor, personaje.hairColor) && Objects.equals(skinColor, personaje.skinColor) && Objects.equals(eyeColor, personaje.eyeColor) && Objects.equals(birthYear, personaje.birthYear) && Objects.equals(gender, personaje.gender) && Objects.equals(homeworld, personaje.homeworld) && Objects.equals(created, personaje.created) && Objects.equals(edited, personaje.edited) && Objects.equals(url, personaje.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, mass, hairColor, skinColor, eyeColor, birthYear, gender, homeworld,
                created, edited, url);
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "name='" + name + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + mass + '\'' +
                ", hair_color='" + hairColor + '\'' +
                ", skin_color='" + skinColor + '\'' +
                ", eye_color='" + eyeColor + '\'' +
                ", birth_year='" + birthYear + '\'' +
                ", gender='" + gender + '\'' +
                ", homeworld='" + homeworld + '\'' +
                ", created='" + created + '\'' +
                ", edited='" + edited + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Personaje otro = (Personaje) o;
        return Integer.compare(otro.cantidadPeliculas(),this.cantidadPeliculas());
    }
}
