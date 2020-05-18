package cat.uab.idt.rightsapp.models;

public class WhatsNextModel {

    private int id;
    private String message;

    /**
     * Constructor
     */
    public WhatsNextModel(){
        this.initialize();
    }

    public WhatsNextModel(int id, String message){
        this.id = id;
        this.message = message;
    }

    private void initialize(){
        this.id = 0;
        this.message = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
