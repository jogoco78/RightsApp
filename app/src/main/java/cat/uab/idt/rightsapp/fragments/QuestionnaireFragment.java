package cat.uab.idt.rightsapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import cat.uab.idt.rightsapp.Constants;
import cat.uab.idt.rightsapp.ExplanationActivity;
import cat.uab.idt.rightsapp.LanguageActivity;
import cat.uab.idt.rightsapp.ParticlesActivity;
import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.RightsAppActivity;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.QuestionnaireActivity;
import cat.uab.idt.rightsapp.models.AnswerModel;
import cat.uab.idt.rightsapp.utils.TestQuestionnaire;

public class QuestionnaireFragment extends Fragment {

    private static String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";
    private static String CURRENT_SELECTION_ID = "CURRENT_SELECTION_ID";

    private int currentQuestionID;
    private int currentSelectionID = -1;
    private String language;
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

        language = parentActivity.getLanguage();
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState){
        View fragmentView = _inflater.inflate(R.layout.fragment_questionnaire, _container, false);

        int marginInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, parentActivity.getResources().getDisplayMetrics());
        rg_answersParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg_answersParams.setMargins(0, 0, 0, marginInPixels);

        tv_question = fragmentView.findViewById(R.id.questionnaire_fragment_question);
        rg_answers = fragmentView.findViewById(R.id.questionnaire_radio_group);

        Button mButton = fragmentView.findViewById(R.id.button_questionnaire_fragment);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_answer = rg_answers.getCheckedRadioButtonId();

                if(id_answer == -1) {
                    //No answer is set by the user
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                    builder.setMessage(R.string.no_answer);

                    // Add the buttons
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button - Do nothing

                        }
                    });
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //One answer is set by the user
                    if (db == null) {
                        //Opens DB
                        db = new DataBaseHelper(parentActivity);
                        db.openDataBase();
                    }

                    //Gets preferences file
                    Context context = parentActivity.getApplicationContext();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                    //Gets current questions, answers and tags parameters
                    String par_questionID = sharedPreferences.getString(Constants.PAR_QUESTIONS, null);
                    String par_answersID = sharedPreferences.getString(Constants.PAR_ANSWERS, null);
                    String par_tag = sharedPreferences.getString(Constants.PAR_TAGS, null);

                    if (par_questionID == null) {
                        par_questionID = String.valueOf(currentQuestionID);
                    } else {
                        par_questionID = par_questionID + "," + String.valueOf(currentQuestionID);
                    }

                    if (par_answersID == null) {
                        par_answersID = String.valueOf(id_answer);
                    } else {
                        par_answersID = par_answersID + "," + id_answer;
                    }

                    //Raise the tag, if any, in preferences
                    int id_tag_raised = db.getTagRaisedID(currentQuestionID, id_answer);
                    if (id_tag_raised != 0) {
                        if (par_tag == null) {
                            par_tag = String.valueOf(id_tag_raised);
                        } else {
                            par_tag = par_tag + "," + id_tag_raised;
                        }
                    }

                    //Stores the parameters
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.PAR_QUESTIONS, par_questionID);
                    editor.putString(Constants.PAR_ANSWERS, par_answersID);
                    if (id_tag_raised != 0) editor.putString(Constants.PAR_TAGS, par_tag);
                    editor.apply();

                    if(currentQuestionID == 1){
                        //Shows a message for the list of crimes
                        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                        builder.setMessage(R.string.crime_list_message);

                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });

                        // Create the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        // Sets text button color to black
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    }

                    //Gets the next question ID and updates the fragment
                    int id_next_question = db.getNextQuestionID(currentQuestionID, id_answer);
                    if (id_next_question == 0) {
                        //the questionnaire is over - loads the next activity
                        //System.out.println("TEST: Questions " + sharedPreferences.getString(Constants.PAR_QUESTIONS, null));
                        //System.out.println("TEST: Answers " + sharedPreferences.getString(Constants.PAR_ANSWERS, null));
                        //System.out.println("TEST: Tags " + sharedPreferences.getString(Constants.PAR_TAGS, null));

                        TestQuestionnaire tq = new TestQuestionnaire(parentActivity, par_questionID, par_answersID, par_tag);
                        try{
                            if(tq.runTest()){
                                System.out.println("TEST: OK!");
                            }else {
                                Log.d("Test", "Fail");
                                Log.d("Test", par_questionID);
                                Log.d("Test", par_answersID);
                                Log.d("Test", par_tag);
                            }

                        }catch (IOException e){
                            e.printStackTrace();
                            System.exit(0);
                        }

                        Intent intent = new Intent(parentActivity.getApplicationContext(), ParticlesActivity.class);
                        startActivity(intent);
                    } else {
                        //updates the fragment to the new question
                        parentActivity.setCurrentQuestionID(id_next_question);
                        parentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.questionnaire_activity_framelayout, new QuestionnaireFragment(), String.valueOf(id_answer))
                                .addToBackStack(null)
                                .commit();
                    }
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

        //Sets the question text
        tv_question.setText(db.getQuestionText(currentQuestionID, language));

        //Clears the answers Radio Group
        rg_answers.clearCheck();
        rg_answers.removeAllViews();

        //Sets the answers texts
        int[] answersID = db.getAnswersIDForQuestion(currentQuestionID);
        String[] answersText = db.getAnswersText(answersID, language);

        for(int i=0; i<answersID.length; i++){
            RadioButton rb = new RadioButton(parentActivity);
            rb.setText(answersText[i]);
            rb.setHorizontallyScrolling(false);

            //rb.setLayoutParams(rg_answersParams);
            rb.setId(answersID[i]);
            rg_answers.addView(rb);
        }

        if(currentSelectionID != -1){
            rg_answers.check(currentSelectionID);
        }
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
