package com.example.user.questionnaire;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.QuestionsListAdapter;
import api.UserResponseService;
import constant.SharedVariable;
import dto.UserAnswerDTO;
import dto.UserResponseDTO;
import entity.Answer;
import entity.Question;
import entity.Questionnaire;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class QuestionnaireActivity extends AppCompatActivity {
    private Button sendButton;
    private QuestionsListAdapter adapter;
    private ListView itemsListView;
    private Context context;
    private final static int START_FOR_ID = 1000;
    private TextView errorMessage;
    private Questionnaire questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        context = this;

        Intent intent = getIntent();
        questionnaire = (Questionnaire) intent.getSerializableExtra("questionnaire");

        adapter = new QuestionsListAdapter(this, questionnaire.getQuestions());
        itemsListView = (ListView) findViewById(R.id.list_questions);
        itemsListView.setAdapter(adapter);

        TextView questionnaireTitle = (TextView) findViewById(R.id.questionaire_title);
        questionnaireTitle.setText(questionnaire.getTitle());

        // Add a footer to the ListView
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.questionnaire_footer, itemsListView, false);
        itemsListView.addFooterView(footer, null, false);

        errorMessage = (TextView) findViewById(R.id.questionnaire_error_message);

        sendButton = (Button) findViewById(R.id.button_send_questionnaire);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResponse(adapter);
            }
        });
    }

    private void sendResponse(QuestionsListAdapter adapter) {
        errorMessage.setText("");
        int size = adapter.getCount();
        List<UserAnswerDTO> answersDTO = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Question question = (Question) adapter.getItem(i);
            List<Integer> answersIDs = getSelectedAnswer(question);
            for (Integer id : answersIDs) {
                UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
                userAnswerDTO.setAnswerId(id);
                userAnswerDTO.setQuestionId(question.getId());
                answersDTO.add(userAnswerDTO);
            }
        }

        if (errorMessage.getText().length() == 0) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setAnswers(answersDTO);
            userResponseDTO.setProfileId(SharedVariable.getProfileId(context));
            userResponseDTO.setQuestionnaireId(questionnaire.getId());
            callSaveUserResponseAPI(userResponseDTO);
        }
    }

    private void callSaveUserResponseAPI(UserResponseDTO userResponseDTO) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.226.5.241:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserResponseService userResponseService = retrofit.create(UserResponseService.class);
        userResponseService.saveUserResponse(userResponseDTO).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(QuestionnaireActivity.this, "Thanks for your response!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(QuestionnaireActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(QuestionnaireActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<Integer> getSelectedAnswer(Question question) {
        List<Integer> answersIDs = new ArrayList<>();
        if (!question.isHasMultipleAnswers()) {
            int id = getSelectedValueFromRadioGroup(question.getId());
            if (id != -1) {
                answersIDs.add(id);
            }
        } else {
            answersIDs = getSelectedValueFromCheckbox(question.getAnswers());
        }
        if (answersIDs.size() == 0) {
            errorMessage.setText("All fields are required");
            errorMessage.setTextColor(Color.RED);
        }
        return answersIDs;
    }

    private List<Integer> getSelectedValueFromCheckbox(List<Answer> answers) {
        List<Integer> IDs = new ArrayList<>();
        CheckBox checkBox;

        for (Answer answer : answers) {
            checkBox = (CheckBox) findViewById(START_FOR_ID + answer.getId());
            if (checkBox.isChecked()) {
                IDs.add(answer.getId());
            }
        }
        return IDs;
    }

    private int getSelectedValueFromRadioGroup(int radioGroupID) {
        RadioGroup radioGroup = (RadioGroup) findViewById(radioGroupID);
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            return -1;
        } else {
            View radioButton = radioGroup.findViewById(id);
            int radioId = radioGroup.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
            return (Integer) btn.getTag();
        }
    }

}
