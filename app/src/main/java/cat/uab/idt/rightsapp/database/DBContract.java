package cat.uab.idt.rightsapp.database;

import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    /*Inner class that defines the table questions*/
    public static class Questions implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT_ES = "text_es";
        public static final String COLUMN_NAME_TEXT_EN = "text_en";
        public static final String COLUMN_NAME_TEXT_PT= "text_por";
        public static final String COLUMN_NAME_TEXT_IT = "text_it";
    }

    /*Inner class that defines the answers table*/
    public static class Answers implements BaseColumns{
        public static final String TABLE_NAME = "answers";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT_ES = "text_es";
        public static final String COLUMN_NAME_TEXT_EN = "text_en";
        public static final String COLUMN_NAME_TEXT_PT = "text_por";
        public static final String COLUMN_NAME_TEXT_IT = "text_it";
    }

    /*Inner class that defines the questions_answers table */
    public static class Questions_Answers implements BaseColumns{
        public static final String TABLE_NAME = "questions_answers";
        public static final String COLUMN_NAME_ID_QUESTION = "id_question";
        public static final String COLUMN_NAME_ID_ANSWER = "id_answer";
        public static final String COLUMN_NAME_ID_NEXT_QUESTION = "id_next_question";
        public static final String COLUMN_NAME_ID_TAG_RAISED = "id_tag_raised";

    }

    /*Inner class that defines the particles table*/
    public static class Particles implements BaseColumns{
        public static final String TABLE_NAME = "particles";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT_ES = "text";
        public static final String COLUMN_NAME_ID_SUBJEECT = "id_subject";
    }

    public static class Subjects implements BaseColumns{
        public static final String TABLE_NAME="subjects";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_GROUP = "group";
        public static final String COLUMN_NAME_PRIORITY = "priority";
    }

    /*Inner class that defines the tags table*/
    public static class Tags implements BaseColumns{
        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_NAME_DESCRIPTION_EN = "description_en";
    }

    /*Inner class that defines the particles_tags table */
    public static class Particles_Tags implements BaseColumns{
        public static final String TABLE_NAME = "particles_tags";
        public static final String COLUMN_NAME_ID_PARTICLE = "id_particle";
        public static final String COLUMN_NAME_ID_TAG = "id_tag";
    }

    /*Inner class that defines the entities table*/
    public static class Entities implements BaseColumns{
        public static final String TABLE_NAME = "entities";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION_ES = "description_es";
        public static final String COLUMN_NAME_DESCRIPTION_EN = "description_en";
        public static final String COLUMN_NAME_DESCRIPTION_PT = "description_por";
        public static final String COLUMN_NAME_DESCRIPTION_IT = "description_it";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_ID_CITY = "id_city";
        public static final String COLUMN_NAME_ID_COUNTRY = "id_country";
        public static final String COLUMN_NAME_ID_CATEGORY = "id_category";
        public static final String COLUMN_NAME_PHONE = "phone_number";
        public static final String COLUMN_NAME_PHONE2 = "phone_number2";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_ENMAIL = "email";
    }

    /* Inner class that defines the cities table */
    public static class Cities implements BaseColumns{
        public static final String TABLE_NAME = "cities";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CITY_ES = "city_es";
        public static final String COLUMN_NAME_CITY_EN = "city_en";
        public static final String COLUMN_NAME_CITY_PT = "city_por";
        public static final String COLUMN_NAME_CITY_IT = "city_it";
        public static final String COLUMN_NAME_ID_COUNTRY = "id_country";
    }

    public static class Countries implements BaseColumns {
        public static final String TABLE_NAME = "countries";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_COUNTRY_ES = "country_es";
        public static final String COLUMN_NAME_COUNTRY_EN = "country_en";
        public static final String COLUMN_NAME_COUNTRY_PT = "country_por";
        public static final String COLUMN_NAME_COUNTRY_IT = "country_it";
    }

    /*Inner class that defines the categories table*/
    public static class Categories implements BaseColumns{
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CATEGORY_ES = "category_es";
        public static final String COLUMN_NAME_CATEGORY_EN = "category_en";
        public static final String COLUMN_NAME_CATEGORY_PT = "category_por";
        public static final String COLUMN_NAME_CATEGORY_IT = "category_it";
    }

    /*Inner class that defines the entities_categories table */
    public static class Entities_Categories implements BaseColumns{
        public static final String TABLE_NAME = "entities_categories";
        public static final String COLUMN_NAME_ID_ENTITY = "id_entity";
        public static final String COLUMN_NAME_ID_CATEGORY = "id_category";
    }
}
