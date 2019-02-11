package cat.uab.idt.rightsapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.QuestionnaireActivity;

public class QuestionnaireFragment extends Fragment {

    private static String CURRENT_QUESTION_ID_KEY = "CURRENT_QUESTION_ID_KEY";
    private static String CURRENT_SELECTION_KEY = "CURRENT_SELECTION_KEY";

    /**
     * ID of the current question
     */
    private int mCurrentQuestionID;

    /**
     * Selection of the answer in the RadioGroup
     */
    private int mCurrentSelection = -1;

    /**
     * RadioGroup for the answers
     */
    private RadioGroup mAnswersRadioGroup;

    /**
     * Textview of the current question in the fragment
     */
    private TextView mQuestionTextView;

    /**
     * Reference to the parent activity of the fragment
     */
    private QuestionnaireActivity mParentActivity;

    /**
     * Database adapter for database operations
     */
    private DataBaseHelper mDataBaseHelper;


    }
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState){
        View fragmentView = _inflater.inflate(R.layout.fragment_questionnaire, _container, false);


        return fragmentView;
    }

}
