package com.sam_chordas.android.stockhawk.historyData;

import android.provider.BaseColumns;

/**
 * Created by martin on 6/9/16.
 */
public final class HistoryContract {

    public HistoryContract() {}

    public static abstract class QuoteEntry implements BaseColumns{
        public static final String TABLE_NAME = "quotes";

        public static final String COLUMN_QUoTE_SYMBOL = "symbol";
        public static final String COLUMN_QUoTE_VALUE = "value";
        public static final String COLUMN_QUoTE_TIMESTAMP = "moment";
    }
}
