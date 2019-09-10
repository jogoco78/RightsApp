package cat.uab.idt.rightsapp.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cat.uab.idt.rightsapp.models.QuestionnaireTestModel;

public class TestQuestionnaire {

    private static String FILE_NAME = "testDecisionTree.txt";
    private static String ASSETS_PATH = "tests/";


    private Context myContext;
    private String par_questionsID;
    private String par_answersID;
    private String par_tags;
    private ArrayList<QuestionnaireTestModel> testKey = new ArrayList<>();

    public TestQuestionnaire(Context context, String par_questionsID, String par_answersID, String par_tags){
        this.par_questionsID = par_questionsID;
        this.par_answersID = par_answersID;
        this.par_tags = par_tags;
        this.myContext = context;
    }

    public boolean runTest() throws IOException {
        //Loads test key
        InputStream myInputStream = myContext.getAssets().open(ASSETS_PATH + FILE_NAME, AssetManager.ACCESS_STREAMING);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myInputStream));

        String lineID;

        do {
            QuestionnaireTestModel qm = new QuestionnaireTestModel();
            lineID = bufferedReader.readLine();
            if(lineID != null) {
                qm.setId(lineID);
                qm.setQuestionsID(bufferedReader.readLine());
                qm.setAnswersID(bufferedReader.readLine());
                qm.setTagsID(bufferedReader.readLine());

                testKey.add(qm);
            }
        } while (lineID != null);

        //Compares the results
        boolean questionsOK = false;
        boolean answersOK = false;
        boolean tagsOK = false;
        ArrayList<Integer> candidatePositions = new ArrayList<>();

        for(int i = 0; i < testKey.size(); i++) {
            if (par_questionsID.equals(testKey.get(i).getQuestionsID())) {
                candidatePositions.add(i);
                questionsOK = true;
            }
        }

        if(questionsOK){
            for(int i = 0; i < candidatePositions.size(); i++) {
                if (par_answersID.equals(testKey.get(candidatePositions.get(i)).getAnswersID())) {
                    answersOK = true;
                }else{
                    candidatePositions.remove(i);
                }
            }
        }

        if(questionsOK && answersOK){
            for(int i = 0; i < candidatePositions.size(); i++) {
                if (par_tags.equals(testKey.get(candidatePositions.get(i)).getTagsID())) {
                    tagsOK = true;
                }else{
                    candidatePositions.remove(i);
                }
            }
        }

        return questionsOK & answersOK & tagsOK;
    }


}
