package entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("text")
    private String text;

    public Answer() {

    }

    public Answer(int id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}