package cat.uab.idt.rightsapp.models;

public class EntityModel {

    private int id;
    private String entity_name;
    private String entity_description;
    private String address;
    private double latitude;
    private double longitude;
    private int id_city;
    private int id_country;
    private int id_category;
    private String language;
    private String phone_number;
    private double distance;

    public EntityModel(){
        init();
    }

    public void init(){
        id = 0;
        entity_name = null;
        entity_description = null;
        address = null;
        latitude = 0.0;
        longitude = 0.0;
        id_city = 0;
        id_country = 0;
        id_category = 0;
        phone_number = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getEntity_description() {
        return entity_description;
    }

    public void setEntity_description(String entity_description) {
        this.entity_description = entity_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
