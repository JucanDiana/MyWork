package api;

import dto.ProfileModel;
import dto.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ProfileService {

    @POST("/profile/save")
    @Headers("Content-Type: application/json")
    Call<ProfileResponse> saveProfile(@Body ProfileModel profileModel);
}