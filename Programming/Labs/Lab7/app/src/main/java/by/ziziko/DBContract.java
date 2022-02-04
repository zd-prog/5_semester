package by.ziziko;

import android.provider.BaseColumns;

public class DBContract {
    public DBContract(){}

    public static abstract class DBEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_RECIPE = "recipe";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
