package dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 3/26/2018.
 */

public class UserResponseDTO {

    @SerializedName("profileId")
    private int profileId;

    @SerializedName("questionnaireId")
    private int questionnaireId;

    @SerializedName("answers")
    private List<UserAnswerDTO> answers;

    public UserResponseDTO() {

    }

    public UserResponseDTO(int profileId, int questionnaireId, List<UserAnswerDTO> answers) {
        super();
        this.profileId = profileId;
        this.questionnaireId = questionnaireId;
        this.answers = answers;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public List<UserAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerDTO> answers) {
        this.answers = answers;
    }

}
