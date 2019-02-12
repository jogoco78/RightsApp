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
        public static final String COLUMN_NAME_TEXT_PT= "text_pt";
        public static final String COLUMN_NAME_TEXT_IT = "text_it";
        public static final String COLUMN_NAME_STATISTICAL = "statistical";
    }

    /*Inner class that defines the answers table*/
    public static class Answers implements BaseColumns{
        public static final String TABLE_NAME = "answers";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT_ES = "text_es";
        public static final String COLUMN_NAME_TEXT_EN = "text_en";
        public static final String COLUMN_NAME_TEXT_PT = "text_pt";
        public static final String COLUMN_NAME_TEXT_IT = "text_it";
    }

    /*Inner class that defines the questions_answers table */
    public static class Questions_Answers implements BaseColumns{
        public static final String TABLE_NAME = "questions_answers";
        public static final String COLUMN_NAME_ID_QUESTION = "id_question";
        public static final String COLUMN_NAME_ID_ANSWER = "id_answer";
        public static final String COLUMN_NAME_ID_NEXT_QUESTION = "id_next_question";
        public static final String COLUMN_NAME_ID_NEXT_QUESTION2 = "id_next_question2";
        public static final String COLUMN_NAME_ID_TAG_RAISED = "id_tag_raised";

    }

    /*Inner class that defines the particles table*/
    public static class Particles implements BaseColumns{
        public static final String TABLE_NAME = "particles";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT_ES = "text_es";
        public static final String COLUMN_NAME_TEXT_EN = "text_en";
        public static final String COLUMN_NAME_TEXT_PT = "text_pt";
        public static final String COLUMN_NAME_TEXT_IT = "text_it";
        public static final String COLUMN_NAME_SUBJECT_ES = "subject_es";
        public static final String COLUMN_NAME_SUBJECT_EN = "subject_en";
        public static final String COLUMN_NAME_SUBJECT_PT = "subject_pt";
        public static final String COLUMN_NAME_SUBJECT_IT = "subject_it";
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
        public static final String COLUMN_NAME_DESCRIPTION_PT = "description_pt";
        public static final String COLUMN_NAME_DESCRIPTION_IT = "description_it";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
    }

    /*Inner class that defines the categories table*/
    public static class Categories implements BaseColumns{
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CATEGORY_ES = "category_es";
        public static final String COLUMN_NAME_CATEGORY_EN = "category_en";
        public static final String COLUMN_NAME_CATEGORY_PT = "category_pt";
        public static final String COLUMN_NAME_CATEGORY_IT = "category_it";
    }

    /*Inner class that defines the entities_categories table */
    public static class Entities_Categories implements BaseColumns{
        public static final String TABLE_NAME = "entities_categories";
        public static final String COLUMN_NAME_ID_ENTITY = "id_entity";
        public static final String COLUMN_NAME_ID_CATEGORY = "id_category";
    }
}
