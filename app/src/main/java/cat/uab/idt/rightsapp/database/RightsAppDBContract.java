package cat.uab.idt.rightsapp.database;

import android.provider.BaseColumns;

public final class RightsAppDBContract {

    private RightsAppDBContract() {}

    //Inner class that defines the table questions
    public static class Questions implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_ID = "id";

    }
}
