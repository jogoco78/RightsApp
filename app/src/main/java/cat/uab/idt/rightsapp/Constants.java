package cat.uab.idt.rightsapp;

public abstract class Constants {
    //Text constants
    public static final String NEW_LINE_SEPARATOR = "--";
    public static final String NEW_LINE_BULLET_SEPARATOR = "|";

    //Initial explanation and terms of use
    public static final String AGREED = "agreed";
    public static final String SHOW_EXPLANATION = "show_explanation";

    //Language
    public static final String DEVICE_LANGUAGE = "device_language";
    public static final String PREF_LANGUAGE = "pref_language";
    public static final String[] LANGUAGE_CODES = {"es","en","por","it"};
    public static final String[] REGIONS = {"ES","EN","PT","IT"};

    //First runs
    public static final String FIRST_RUN_CRIME_LIST = "first_run_crime_list";
    public static final String FIRST_RUN_BACK_CLUSTER = "first_run_back_cluster";

    //Parameters
    public static final String PAR_QUESTIONS = "par_questions";
    public static final String PAR_ANSWERS = "par_answers";
    public static final String PAR_TAGS = "par_tags";

    //Tags
    public static final int TAG_COMMON_CRIME = 1;
    public static final int TAG_TERRORISM = 2;
    public static final int TAG_VIOLENCE_AGAINST_WOMEN = 3;
    public static final int TAG_DOMESTIC_VIOLENCE = 4;
    public static final int TAG_VIOLENT_CRIME = 5;
    public static final int TAG_SEXUAL_ATTACK = 6;
    public static final int TAG_UE_RESIDENT = 7;
    public static final int TAG_NON_EU_RESIDENT = 8;
    public static final int TAG_SPANISH_RESIDENT = 9;

    //Clusters
    public static final int TAG_CLUSTER_TERRORISM = 21;

    //Entities search criteria
    public static final String SEARCH_ENTITY_CRITERIA = "search_entity_criteria";

    //Entity selected information
    public static final String ENTITY_NAME = "entity_name";
    public static final String ENTITY_DESCRIPTION = "entity_description";
    public static final String ENTITY_ADDRESS = "entity_address";
    public static final String ENTITY_PHONE = "entity_phone";
    public static final String ENTITY_PHONE2 = "entity_phone2";
    public static final String ENTITY_POSITION = "entity_position";
    public static final String ENTITY_CITY = "entity_city";
    public static final String ENTITY_COUNTRY = "entity_country";
    public static final String ENTITY_LINK = "entity_link";

    //Phone numbers
    public static final String PHONE_EMERGENCIES = "112";
    public static final String PHONE_NUMBER_KEY = "phone_number";
    public static final String PHONE_VAW = "016";

    //Information in intents extras
    public static final String MAIN_TAG = "main_tag";
    public static final String SIDE_TAG = "side_tag";
    public static final String RESIDENCE_TAG = "residence_tag";
    public static final String SELECTED_TAG = "selected_tag";
    public static final String SELECTED_PARTICLE = "selected_particle";






}
