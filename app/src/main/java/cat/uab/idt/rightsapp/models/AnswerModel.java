package cat.uab.idt.rightsapp.models;

public class AnswerModel {
    private int id;
    private String text_es;
    private String text_en;
    private String text_fr;
    private String text_it;
    private int next_question_id = 0;

    /**
     * Constructor
     */
    public AnswerModel(){
        this.initialize();
    }

    public void initialize(){
        this.id = 0;
        this.text_es = null;
        this.text_en = null;
        this.text_fr = null;
        this.text_it = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText_es() {
        return text_es;
    }

    public void setText_es(String text_es) {
        this.text_es = text_es;
    }

    public String getText_en() {
        return text_en;
    }

    public void setText_en(String text_en) {
        this.text_en = text_en;
    }

    public String getText_fr() {
        return text_fr;
    }

    public void setText_fr(String text_fr) {
        this.text_fr = text_fr;
    }

    public String getText_it() {
        return text_it;
    }

    public void setText_it(String text_it) {
        this.text_it = text_it;
    }

    public int getNext_question_id() {
        return next_question_id;
    }

    public void setNext_question_id(int next_question_id) {
        this.next_question_id = next_question_id;
    }

}
