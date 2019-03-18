package cat.uab.idt.rightsapp.models;

public class CountryModel {

    private int id;
    private String country_name;
    private String language;

    public CountryModel(){
        id = 0;
        country_name = null;
        language = null;
    }

    public CountryModel(int _id, String _country_name, String _language){
        id = _id;
        country_name = _country_name;
        language = _language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString(){
        return country_name;
    }
}
