package cat.uab.idt.rightsapp.models;

public class CategoryModel {

    private int id;
    private String category_name;
    private String language;

    public CategoryModel(){
        init();
    }

    public void init(){
        id = 0;
        category_name = null;
        language = null;
    }

    public CategoryModel(int _id, String _category_name, String _language){
        id = _id;
        category_name = _category_name;
        language = _language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString(){
        return category_name;
    }
}
