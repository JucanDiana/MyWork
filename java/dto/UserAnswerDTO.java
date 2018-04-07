package dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 3/26/2018.
 */

public class UserAnswerDTO {

    @SerializedName("questionId")
    private int questionId;

    @SerializedName("answerId")
    private int answerId;

    public UserAnswerDTO() {

    }

    public UserAnswerDTO(int questionId, int answerId) {
        super();
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

}
