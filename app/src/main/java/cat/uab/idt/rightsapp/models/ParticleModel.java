package cat.uab.idt.rightsapp.models;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.Constants;

public class ParticleModel {

    private int id;
    private String text;
    private int id_subject;
    private String subjectText;
    private String tagCode;
    private String language;

    public ParticleModel(){
        this.initialize();
    }

    public ParticleModel(int id, String text, int id_subject, String subjectText, String tagCode, String language){
        this.id = id;
        this.text = text;
        this.id_subject = id_subject;
        this.subjectText = subjectText;
        this.tagCode = tagCode;
        this.language = language;
    }

    public void initialize(){
        this.id = 0;
        this.language = null;
        this.text = null;
        this.id_subject = 0;
        this.tagCode = null;
    }

    public int getMainTag(){
        //first digit
        return Integer.parseInt(tagCode.substring(0,1));
    }

    public int getResidenceTag(){
        //second digit
        return Integer.parseInt(tagCode.substring(1,2));
    }

    public int getCluster(){
        //third digit
        return Integer.parseInt(tagCode.substring(2,3));
    }

    public int getOrder(){
        //fourth and fifth digit
        return Integer.parseInt(tagCode.substring(3,5));
    }

    public void setTagCode(String tagCode){
        this.tagCode = tagCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public String[] getTextSplit(){
        return text.split(Constants.NEW_LINE_PARAGRAPH_SEPARATOR);
    }

   /* public ArrayList<String> getTextArray(){
        ArrayList<String> results = new ArrayList<>();
        //String t = text.replaceAll(Constants.NEW_LINE_SEPARATOR, "\n");
        String[] split = text.split(Constants.NEW_LINE_PARAGRAPH_SEPARATOR);

        for (String text : split){
            results.add(text.replaceAll(Constants.NEW_LINE_SEPARATOR, "\n"));
        }

        return(results);
    }*/

    public void setText(String text) {
        this.text = text;
    }

    public int getId_subject() {
        return id_subject;
    }

    public void setId_subject(int id_subject) {
        this.id_subject = id_subject;
    }

    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }


}
