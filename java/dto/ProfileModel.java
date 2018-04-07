package dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 3/10/2018.
 */

public class ProfileModel {

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("degreeOfEducation")
    @Expose
    private String degreeOfEducation;

    @SerializedName("hasChildren")
    @Expose
    private String hasChildren;

    @SerializedName("smoker")
    @Expose
    private String isSmoker;

    @SerializedName("transportIDs")
    @Expose
    private List<Integer> transportIDs;

    @SerializedName("problemsIDs")
    @Expose
    private List<Integer> problemsIDs;

    public ProfileModel() {

    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDegreeOfEducation() {
        return degreeOfEducation;
    }

    public void setDegreeOfEducation(String degreeOfEducation) {
        this.degreeOfEducation = degreeOfEducation;
    }

    public String getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(String isSmoker) {
        this.isSmoker = isSmoker;
    }

    public List<Integer> getTransportIDs() {
        return transportIDs;
    }

    public void setTransportIDs(List<Integer> transportIDs) {
        this.transportIDs = transportIDs;
    }

    public List<Integer> getProblemsIDs() {
        return problemsIDs;
    }

    public void setProblemsIDs(List<Integer> problemsIDs) {
        this.problemsIDs = problemsIDs;
    }
}
