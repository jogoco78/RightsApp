package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class QuestionnaireActivity extends AppCompatActivity {

    private static Context mContext;
    private static String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";
    private int mCurrentQuestionID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.questionnaire_activity_framelayout, new QuestionnaireFragment(), String.valueOf(mCurrentQuestionID));
            fragmentTransaction.commit();
        }
        else mCurrentQuestionID = savedInstanceState.getInt(CURRENT_QUESTION_ID);
    }

    public int getmCurrentQuestionID(){
        return mCurrentQuestionID;
    }

    public void setCurrentQuestionId(int _id){
        mCurrentQuestionID = _id;
    }

}
