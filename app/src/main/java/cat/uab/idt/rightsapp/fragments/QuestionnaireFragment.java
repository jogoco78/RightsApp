package cat.uab.idt.rightsapp.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.os.ConfigurationCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.QuestionnaireActivity;
import cat.uab.idt.rightsapp.models.AnswerModel;

public class QuestionnaireFragment extends Fragment {

    private static String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";
    private static String CURRENT_SELECTION_ID = "CURRENT_SELECTION_ID";

    private int currentQuestionID;
    private int currentSelectionID = -1;
    private DataBaseHelper db;
    private TextView tv_question;
    private RadioGroup rg_answers;
    private RadioGroup.LayoutParams rg_answersParams;
    private QuestionnaireActivity parentActivity;


    @Override
    public void onAttach(Activity _activity){
        super.onAttach(_activity);

        parentActivity = (QuestionnaireActivity) _activity;

    }

    @Override
    public void onSaveInstanceState(Bundle _outState){
        super.onSaveInstanceState(_outState);

        _outState.putInt(CURRENT_QUESTION_ID, currentQuestionID);
        _outState.putInt(CURRENT_SELECTION_ID, currentSelectionID);

    }

    @Override
    public void onCreate(Bundle _savedInstanceState){
        super.onCreate(_savedInstanceState);

        if(_savedInstanceState == null){
            currentQuestionID = parentActivity.getCurrentQuestionID();
        }else{
            currentQuestionID = _savedInstanceState.getInt(CURRENT_QUESTION_ID);
            currentSelectionID = _savedInstanceState.getInt(CURRENT_SELECTION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState){
        View fragmentView = _inflater.inflate(R.layout.fragment_questionnaire, _container, false);

        int marginInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, parentActivity.getResources().getDisplayMetrics());
        rg_answersParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg_answersParams.setMargins(0, 0, 0, marginInPixels);

        tv_question = (TextView)fragmentView.findViewById(R.id.questionnaire_fragment_question);
        rg_answers = (RadioGroup)fragmentView.findViewById(R.id.questionnaire_radio_group);

        Button mButton = (Button) fragmentView.findViewById(R.id.button_questionnaire_fragment);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_answer = rg_answers.getCheckedRadioButtonId();

                if(db == null){
                    //Opens DB
                    db = new DataBaseHelper(parentActivity);
                    //db.createDatabase();
                    db.openDataBase();
                }

                int id_next_question = db.getNextQuestionID(currentQuestionID, id_answer);
                if(id_next_question == 0){
                    //the questionnaire is over - loads the next activity

                }else{
                    //updates the fragment to the new question
                    parentActivity.setCurrentQuestionID(id_next_question);
                    parentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.questionnaire_activity_framelayout, new QuestionnaireFragment(), String.valueOf(id_answer))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState){
        super.onActivityCreated(_savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(db == null){
            //Opens DB
            db = new DataBaseHelper(parentActivity);
            db.openDataBase();
        }

        //TODO: Select correct locale
        tv_question.setText(db.getQuestions(currentQuestionID).getText_es());

        rg_answers.clearCheck();
        rg_answers.removeAllViews();
        ArrayList<AnswerModel> answers = db.getAnswersForQuestion(currentQuestionID);

        for(int i=0; i<answers.size(); i++){
            RadioButton rb = new RadioButton(parentActivity);
            rb.setText(answers.get(i).getText_es());
            rb.setLayoutParams(rg_answersParams);
            rb.setId(answers.get(i).getId());
            rg_answers.addView(rb);
        }

        if(currentSelectionID != -1){
            rg_answers.check(currentSelectionID);
        }

        //Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();

        currentSelectionID = rg_answers.getCheckedRadioButtonId();

        if(db != null){
            db.close();
            db = null;
        }
    }



}
