package com.example.task_typical_design;

import android.net.Uri;
import android.provider.BaseColumns;

public class product_contract  {
    public static final String CONTENT_AUTHORITY ="com.example.task_typical_design";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCT = "product";
    public static final class ProductyEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCT)
                .build();
        public static final String TABLE_NAME = "product";
        public static final String PRPODUCT_ID = "id";
        public static final String PRODUCT_NAME = "name";
        public static final String PRPODUCT_PRICE = "price";
        public static final String PRPODUCT_IMAGE = "image";
        public static final String PRPODUCT_QUANTITY = "q";
    }

}
