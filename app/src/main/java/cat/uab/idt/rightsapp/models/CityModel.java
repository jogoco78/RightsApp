package cat.uab.idt.rightsapp.models;

public class CityModel {
     private int id;
    private String city_name;
    private int id_country;
    private String language;

    public CityModel(){
        id = 0;
        city_name = null;
        id_country = 0;
        language = null;
    }

    public CityModel(int _id, String _city_name, int _id_country, String _language){
        id = _id;
        city_name = _city_name;
        id_country = _id_country;
        language = _language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString(){
        return city_name;
    }
}
