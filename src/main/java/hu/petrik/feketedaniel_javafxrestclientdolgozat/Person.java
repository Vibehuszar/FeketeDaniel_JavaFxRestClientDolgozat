package hu.petrik.feketedaniel_javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
    private int id;
    @Expose
    @SerializedName("nev")
    private String name;
    @Expose
    @SerializedName("kor")
    private int age;
    @Expose
    @SerializedName("foglalkoztatott")

    private boolean employed;

    public Person(int id, String name, int age, boolean employed) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.employed = employed;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }
}
