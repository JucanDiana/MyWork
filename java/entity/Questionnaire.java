package entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Questionnaire implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("questions")
    private List<Question> questions;

    public Questionnaire() {

    }

    public Questionnaire(int id, String title, List<Question> questions) {
        super();
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                '}';
    }
}
