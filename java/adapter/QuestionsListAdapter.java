package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.questionnaire.R;

import java.util.List;

import entity.Answer;
import entity.Question;

/**
 * Created by Student on 22.11.2017.
 */
public class QuestionsListAdapter extends BaseAdapter {
    private List<Question> questions;
    private Context context;
    private final static int START_FOR_ID = 1000;

    public QuestionsListAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {

        return questions.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.question_layout, parent, false);
        }

        // get item at position
        Question question = (Question) getItem(position);
        List<Answer> answers = question.getAnswers();

        // set values for elements from list row
        TextView questionTitle = (TextView) convertView.findViewById(R.id.question_title);
        questionTitle.setText(question.getText());

        LinearLayout relativelayout = (LinearLayout) convertView.findViewById(R.id.linearlayout);
        if(answers!= null) {
            if (question.isHasMultipleAnswers() == false) {
                RadioGroup dynamicRadiogroup = new RadioGroup(convertView.getContext());
                dynamicRadiogroup.setId(question.getId());
                dynamicRadiogroup.setTag(question.getId());
                for (Answer answer : answers) {
                    RadioButton radiobutton = new RadioButton(convertView.getContext());
                    radiobutton.setText(answer.getText());
                    radiobutton.setId(START_FOR_ID + answer.getId());
                    radiobutton.setTag(answer.getId());
                    dynamicRadiogroup.addView(radiobutton);
                }
                relativelayout.addView(dynamicRadiogroup);
            } else {
                for (Answer answer : answers) {
                    CheckBox checkbox = new CheckBox(convertView.getContext());
                    checkbox.setText(answer.getText());
                    checkbox.setId(START_FOR_ID + answer.getId());
                    checkbox.setTag(answer.getId());
                    relativelayout.addView(checkbox);
                }
            }
        }
        return convertView;
    }
}
