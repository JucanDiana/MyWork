package api;

import java.util.List;

import entity.Questionnaire;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by USER on 3/21/2018.
 */

public interface QuestionnaireService {

    @GET("/questionnaires/")
    @Headers("Content-Type: application/json")
    Call<List<Questionnaire>> getQuestionnaires(@Query("profileId") int profileId);
}
