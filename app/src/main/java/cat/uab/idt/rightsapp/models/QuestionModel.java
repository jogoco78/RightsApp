package cat.uab.idt.rightsapp.models;

public class QuestionModel {
    private int id;
    private String text_es;
    private String text_en;
    private String text_pt;
    private String text_it;
    private boolean statistical;

    /**
     * Constructor
     */
    public QuestionModel(){
        this.initialize();
    }

    public void initialize(){
        this.id = 0;
        this.text_es = null;
        this.text_en = null;
        this.text_pt = null;
        this.text_it = null;
        this.statistical = false;
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

    public String getText_pt() {
        return text_pt;
    }

    public void setText_pt(String text_pt) {
        this.text_pt = text_pt;
    }

    public String getText_it() {
        return text_it;
    }

    public void setText_it(String text_it) {
        this.text_it = text_it;
    }

    public boolean isStatistical() {
        return statistical;
    }

    public void setStatistical(boolean statistical){
        this.statistical = statistical;
    }
}
