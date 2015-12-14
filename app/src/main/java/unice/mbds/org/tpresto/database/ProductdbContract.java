package unice.mbds.org.tpresto.database;

import android.provider.BaseColumns;

/**
 * Created by Zac on 13/12/2015.
 */
public final class ProductdbContract {
    public ProductdbContract(){}
    public static abstract class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME= "ProduitdbHelper";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_PRIX = "prix";
        public static final String COLUMN_DISCOUNT = "discount";
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_CREATEDAT = "createdAt" ;
        public static final String COLUMN_UPDATEDAT = "updateAt";
    }
}
