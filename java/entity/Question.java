package entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("text")
    private String text;

    @SerializedName("hasMultipleAnswers")
    private boolean hasMultipleAnswers;

    @SerializedName("answers")
    private List<Answer> answers;

    public Question() {

    }

    public Question(int id, String text, List<Answer> answers) {
        super();
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public Question(int id, String text, boolean hasMultipleAnswers, List<Answer> answers) {
        super();
        this.id = id;
        this.text = text;
        this.hasMultipleAnswers = hasMultipleAnswers;
        this.answers = answers;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isHasMultipleAnswers() {
        return hasMultipleAnswers;
    }

    public void setHasMultipleAnswers(boolean hasMultipleAnswers) {
        this.hasMultipleAnswers = hasMultipleAnswers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", hasMultipleAnswers=" + hasMultipleAnswers +
                ", answers=" + answers +
                '}';
    }
}
