package cat.uab.idt.rightsapp.models;

public class QuestionnaireTestModel {
    private String id;
    private String questionsID;
    private String answersID;
    private String tagsID;

    public QuestionnaireTestModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionsID() {
        return questionsID;
    }

    public void setQuestionsID(String questionsID) {
        this.questionsID = questionsID;
    }

    public String getAnswersID() {
        return answersID;
    }

    public void setAnswersID(String answersID) {
        this.answersID = answersID;
    }

    public String getTagsID() {
        return tagsID;
    }

    public void setTagsID(String tagsID) {
        this.tagsID = tagsID;
    }
}
