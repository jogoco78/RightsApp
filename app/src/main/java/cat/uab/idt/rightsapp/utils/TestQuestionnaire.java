package cat.uab.idt.rightsapp.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import cat.uab.idt.rightsapp.models.QuestionnaireTestModel;

public class TestQuestionnaire {

    private static String FILE_NAME = "testDecisionTree.txt";
    private static String ASSETS_PATH = "tests/";


    private int key = 0;
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

        myInputStream.close();

        //Compares the results
        boolean questionsOK = false;
        boolean answersOK = false;
        boolean tagsOK = false;
        ArrayList<Integer> candidatePositions = new ArrayList<>();

        for(int i = 0; i < testKey.size(); i++) {
            if (par_questionsID.equals(testKey.get(i).getQuestionsID())) {
                candidatePositions.add(i);
                questionsOK = true;
                System.out.println("Questions OK");
                /*System.out.println("I:" + i);
                System.out.println("ID: " + testKey.get(i).getId());
                System.out.println("PAR Q:" + par_questionsID);
                System.out.println("QT:" + testKey.get(i).getQuestionsID());*/
            }
        }
        for(int cd : candidatePositions){
            System.out.println("POS: " + cd);
        }
        System.out.println(" ");
        System.out.println(" ");

        /*if(questionsOK){
            for(int cd : candidatePositions){
                if (par_answersID.equals(testKey.get(cd).getAnswersID())) {
                    answersOK = true;
                    System.out.println("Answers OK");
                    System.out.println("I:" + cd);
                    System.out.println("ID: " + testKey.get(cd).getId());
                    System.out.println("PAR A:" + par_answersID);
                    System.out.println("AT:" + testKey.get(cd).getAnswersID());
                }else{
                    System.out.println("I REMOVE:" + cd);
                    candidatePositions.remove(cd);
                }
            }
        }*/

        if(questionsOK){
            for(int i = 0; i < candidatePositions.size(); i++) {
                if (par_answersID.equals(testKey.get(candidatePositions.get(i)).getAnswersID())) {
                    answersOK = true;
                    key = candidatePositions.get(i);
                    candidatePositions.clear();
                    System.out.println("Answers OK");
                    System.out.println("I: " + i);
                    System.out.println("POS:" + key);
                    System.out.println("ID: " + testKey.get(key).getId());
                    System.out.println("PAR A:" + par_answersID);
                    System.out.println("AT:" + testKey.get(key).getAnswersID());
                    break;
                }
            }
        }
        System.out.println(" ");

        if(questionsOK && answersOK){
            if (par_tags.equals(testKey.get(key).getTagsID())) {
                tagsOK = true;
                System.out.println("Tags OK");
            }
        }
        System.out.println(" ");

        return questionsOK & answersOK & tagsOK;
    }


}
