package com.example.user.questionnaire;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import api.ProfileService;
import constant.SharedVariable;
import dto.ProfileModel;
import dto.ProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private Button saveProfileButton;
    private TextView userProfileErrorMessage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;

        saveProfileButton = (Button) findViewById(R.id.button_save_profile);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserProfile();
            }
        });
    }

    private String getSelectedValueFromRadioGroup(int radioGroupID) {
        RadioGroup radioGroup = (RadioGroup) findViewById(radioGroupID);
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            // No item selected
            return null;
        } else {
            View radioButton = radioGroup.findViewById(id);
            int radioId = radioGroup.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
            return (String) btn.getText();
        }
    }

    private List<Integer> getHealthProblemsIDs() {
        List<Integer> healthProblemsIDs = new ArrayList<>();
        CheckBox checkBox;
        int[] healthProblemsOptionsID = {R.id.checkboxMedical1, R.id.checkboxMedical2, R.id.checkboxMedical3,
                R.id.checkboxMedical4, R.id.checkboxMedical5, R.id.checkboxMedical6};

        for (int i = 0; i < healthProblemsOptionsID.length; i++) {
            checkBox = (CheckBox) findViewById(healthProblemsOptionsID[i]);
            if (checkBox.isChecked()) {
                healthProblemsIDs.add(i + 1);
            }
        }
        return healthProblemsIDs;
    }

    private List<Integer> getModeOfTransportIDs() {
        List<Integer> modeOfTransportIDs = new ArrayList<>();
        CheckBox checkBox;
        int[] transportOptionsID = {R.id.checkboxTransport1, R.id.checkboxTransport2, R.id.checkboxTransport3, R.id.checkboxTransport4};

        // in database, transport 1 has id 1, transport 2 has id 2
        for (int i = 0; i < transportOptionsID.length; i++) {
            checkBox = (CheckBox) findViewById(transportOptionsID[i]);
            if (checkBox.isChecked()) {
                modeOfTransportIDs.add(i + 1);
            }
        }
        return modeOfTransportIDs;
    }

    private void callSaveProfileAPI(ProfileModel profileModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.226.5.241:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileService profileService = retrofit.create(ProfileService.class);
        profileService.saveProfile(profileModel).enqueue(new Callback<ProfileResponse>() {

            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    ProfileResponse profileResponse = response.body();
                    SharedVariable.setProfileId(context, profileResponse.getProfileId());
                    Toast.makeText(ProfileActivity.this, "Your profile have been successfully saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                //    Toast.makeText(ProfileActivity.this,  response.errorBody().ge, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addErrorMessageForCheckboxes(int healthProblemsListSize, int transportModeListSize) {
        TextView healthProblemsErrorMessage = (TextView) findViewById(R.id.error_health_problems);
        TextView transportModeErrorMessage = (TextView) findViewById(R.id.error_transport_mode);

        if (healthProblemsListSize != 0) {
            healthProblemsErrorMessage.setText("");
        } else {
            healthProblemsErrorMessage.setText("Please select at least 1 option!");
            healthProblemsErrorMessage.setTextColor(Color.RED);
        }

        if (transportModeListSize != 0) {
            transportModeErrorMessage.setText("");
        } else {
            transportModeErrorMessage.setText("Please select at least 1 option!");
            transportModeErrorMessage.setTextColor(Color.RED);
        }
    }

    private ProfileModel getProfile() {
        String age = getSelectedValueFromRadioGroup(R.id.radioAge);
        String genre = getSelectedValueFromRadioGroup(R.id.radioGenre);
        String state = getSelectedValueFromRadioGroup(R.id.radioState);
        String education = getSelectedValueFromRadioGroup(R.id.radioEducation);
        String hasChildren = getSelectedValueFromRadioGroup(R.id.radioHavingChildren);
        String isSmoker = getSelectedValueFromRadioGroup(R.id.radioSmoker);
        List<Integer> healthProblemsIDs = getHealthProblemsIDs();
        List<Integer> transportModeIDs = getModeOfTransportIDs();
        int healthProblemsListSize = healthProblemsIDs.size();
        int transportModeListSize = transportModeIDs.size();

        addErrorMessageForCheckboxes(healthProblemsListSize, transportModeListSize);
        if (age == null || genre == null || state == null || education == null || hasChildren == null || isSmoker == null
                || healthProblemsIDs.size() == 0 || transportModeIDs.size() == 0) {
            userProfileErrorMessage = (TextView) findViewById(R.id.user_profile_error_message);
            userProfileErrorMessage.setText("All fields are required!");
            userProfileErrorMessage.setTextColor(Color.RED);
            return null;
        } else {
            ProfileModel profileModel = new ProfileModel();
            profileModel.setAge(age);
            profileModel.setDegreeOfEducation(education);
            profileModel.setGenre(genre);
            profileModel.setHasChildren(hasChildren);
            profileModel.setProblemsIDs(healthProblemsIDs);
            profileModel.setState(state);
            profileModel.setTransportIDs(transportModeIDs);
            profileModel.setIsSmoker(isSmoker);
            return profileModel;
        }
    }

    private void saveUserProfile() {
        ProfileModel profileModel = getProfile();
        if (profileModel != null) {
            callSaveProfileAPI(profileModel);
        }
    }


}
