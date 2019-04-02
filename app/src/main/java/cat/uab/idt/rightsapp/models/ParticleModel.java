package cat.uab.idt.rightsapp.models;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.Constants;

public class ParticleModel {

    private int id;
    private String text;
    private int id_subject;
    private String language;

    public ParticleModel(){
        this.initialize();
    }

    public ParticleModel(int id, String text, int id_subject, String language){
        this.id = id;
        this.text = text;
        this.id_subject = id_subject;
        this.language = language;
    }

    public void initialize(){
        this.id = 0;
        this.language = null;
        this.text = null;
        this.id_subject = 0;
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

    public ArrayList<String> getTextArray(){
        ArrayList<String> results = new ArrayList<>();

        String[] splited = text.split(Constants.SEPARATOR_TEXT);

        for (int i = 0; i < splited.length; i++){
            results.add(splited[i]);
        }

        return(results);
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId_subject() {
        return id_subject;
    }

    public void setId_subject(int id_subject) {
        this.id_subject = id_subject;
    }
}
