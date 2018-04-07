package com.example.user.questionnaire;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import api.QuestionnaireService;
import constant.SharedVariable;
import entity.Questionnaire;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button userProfileButton;
    private Button notificationButton;
    private Button questionnaireButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        userProfileButton = findViewById(R.id.button_go_to_profile);
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUserProfileActivity();
            }
        });

        int profileId = SharedVariable.getProfileId(context);
        if(profileId !=-1){
            userProfileButton.setVisibility(View.GONE);
        }

        questionnaireButton = findViewById(R.id.button_questionnaires);
        questionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callGetQuestionnairesAPI(SharedVariable.getProfileId(context));
            }
        });

        notificationButton = findViewById(R.id.button_notification);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
    }

    private void goToUserProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void callGetQuestionnairesAPI(int profileId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.226.5.241:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QuestionnaireService questionnaireService = retrofit.create(QuestionnaireService.class);
        questionnaireService.getQuestionnaires(profileId).enqueue(new Callback<List<Questionnaire>>() {

            @Override
            public void onResponse(Call<List<Questionnaire>> call, Response<List<Questionnaire>> response) {
                if (response.isSuccessful()) {
                    List<Questionnaire> questionnaires = response.body();
                    goToQuestionnaireActivity(questionnaires);
                } else {
                    Toast.makeText(MainActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Questionnaire>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToQuestionnaireActivity(List<Questionnaire> questionnaires) {
        if(questionnaires.size() == 0 ){
            Toast.makeText(MainActivity.this, "There are no new questionnaires to be completed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, QuestionnaireActivity.class);
            for(Questionnaire questionnaire: questionnaires){
                intent.putExtra("questionnaire", questionnaire);
                startActivity(intent);
            }
        }
    }

    private void sendNotification() {
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Rain questionnaire")
                .setContentText("You have a new questionnaire. Please complete it").setSmallIcon(R.drawable.notification)
                .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }


}
