package dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 3/10/2018.
 */

public class ProfileResponse {

    @SerializedName("profileId")
    private int profileId;

    public ProfileResponse(){

    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
