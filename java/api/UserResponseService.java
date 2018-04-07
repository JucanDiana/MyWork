package api;

import dto.UserResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by USER on 3/26/2018.
 */

public interface UserResponseService {

    @POST("/response")
    @Headers("Content-Type: application/json")
    Call<Void> saveUserResponse(@Body UserResponseDTO userResponseDTO);
}
