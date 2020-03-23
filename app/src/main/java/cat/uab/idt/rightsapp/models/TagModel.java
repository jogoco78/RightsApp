package cat.uab.idt.rightsapp.models;

public class TagModel {
    private int id_tag;
    private String description;

    /**
     * Constructor
     */
    public TagModel(){
        this.initialize();
    }

    public TagModel(int id_tag, String description){
        this.id_tag = id_tag;
        this.description = description;
    }

    private void initialize(){
        this.id_tag = 0;
        this.description = null;
    }

    public int getId_tag() {
        return id_tag;
    }

    public void setId_tag(int id_tag) {
        this.id_tag = id_tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
