package cat.uab.idt.rightsapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.QuestionnaireActivity;

public class QuestionnaireFragment extends Fragment {

    private static String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";
    private static String CURRENT_SELECTION_ID = "CURRENT_SELECTION_ID";

    private int currentQuestionID;
    private int currentSelectionID = -1;
    private DataBaseHelper db;
    private TextView mQuestionTextView;
    private RadioGroup mAnswersRadioGroup;
    private RadioGroup.LayoutParams mRadioGroupParams;
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
        mRadioGroupParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        mRadioGroupParams.setMargins(0, 0, 0, marginInPixels);

        mQuestionTextView = (TextView)fragmentView.findViewById(R.id.questionnaire_fragment_question);
        mAnswersRadioGroup = (RadioGroup)fragmentView.findViewById(R.id.questionnaire_radio_group);

        Button mButton = (Button) fragmentView.findViewById(R.id.button_questionnaire_fragment);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mAnswersRadioGroup.getCheckedRadioButtonId();

                if(db == null){
                    db = new DataBaseHelper(parentActivity);
                    db.createDatabase();
                    db.openDataBase();
                }




            }
        });

        return fragmentView;
    }

}
