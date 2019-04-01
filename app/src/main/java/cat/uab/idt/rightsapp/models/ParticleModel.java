package cat.uab.idt.rightsapp.models;

import java.util.ArrayList;

public class ParticleModel {

    private int id;
    private String language;
    private String text;
    private int id_subject;

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

        results.add(text);

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
