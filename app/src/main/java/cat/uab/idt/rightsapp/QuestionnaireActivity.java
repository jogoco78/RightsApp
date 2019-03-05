package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cat.uab.idt.rightsapp.fragments.QuestionnaireFragment;

public class QuestionnaireActivity extends AppCompatActivity {

    protected static Context context;
    private static String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";

    private String language;
    private int currentQuestionID = 1;

    private TextView tv_title;

    @Override
    public void onCreate(Bundle _savedInstanceState){
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        context = getApplicationContext();

        //Gets preference file
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        if(_savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.questionnaire_activity_framelayout, new QuestionnaireFragment(), String.valueOf(currentQuestionID));
            fragmentTransaction.commit();
        }else{
            currentQuestionID = _savedInstanceState.getInt(CURRENT_QUESTION_ID);
        }

        tv_title = (TextView) findViewById(R.id.questionnaire_activity_title);
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Remove previous questions, answers and tags parameters
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.PAR_QUESTIONS);
        editor.remove(Constants.PAR_ANSWERS);
        editor.remove(Constants.PAR_TAGS);
        editor.apply();

        tv_title.setText(R.string.questionnaire_title);
    }

    @Override
    protected void onSaveInstanceState(Bundle _outState){
        super.onSaveInstanceState(_outState);

        _outState.putInt(CURRENT_QUESTION_ID, currentQuestionID);
    }

    public void onBackPressed(){
        super.onBackPressed();

        if(currentQuestionID > 1){
           // currentQuestionID = ((QuestionnaireFragment)getSupportFragmentManager().findFragmentById(R.id.questionnaire_activity_framelayout)).getCurrentQuestionID();
        }
    }

    public int getCurrentQuestionID() {
        return currentQuestionID;
    }

    public String getLanguage(){
        return language;
    }

    public void setCurrentQuestionID(int _currentQuestionID) {
        currentQuestionID = _currentQuestionID;
    }



}
