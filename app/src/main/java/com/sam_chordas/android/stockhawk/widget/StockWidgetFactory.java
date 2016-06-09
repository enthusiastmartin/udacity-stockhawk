package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by martin on 6/9/16.
 */
public class StockWidgetFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private Cursor mDataCursor = null;

    public StockWidgetFactory(Context context, Intent intent) {
        mContext = context;
    }

    public void onCreate() {
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String stockSymbol = "";
        String stockQuote ="";
        String stockChang ="";
        String isUp = "1";

        if (mDataCursor.moveToPosition(position)) {
            stockSymbol = mDataCursor.getString(mDataCursor.getColumnIndex(QuoteColumns.SYMBOL));
            stockQuote = mDataCursor.getString(mDataCursor.getColumnIndex(QuoteColumns.BIDPRICE));
            stockChang= mDataCursor.getString(mDataCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE));
            isUp = mDataCursor.getString(mDataCursor.getColumnIndex(QuoteColumns.ISUP));
        }

        rv.setTextViewText(R.id.widget_stock_symbol, stockSymbol);
        rv.setTextViewText(R.id.widget_stock_quote, stockQuote);
        rv.setTextViewText(R.id.widget_stock_change, stockChang);

        if ( isUp.equals("0") ){
            rv.setInt(R.id.widget_stock_change, "setBackgroundResource", R.drawable.percent_change_pill_red);
        }
        return rv;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getCount() {
        return (mDataCursor == null) ? 0 : mDataCursor.getCount();
    }

    @Override
    public void onDestroy() {
        if ( mDataCursor != null){
            mDataCursor.close();
        }
    }

    @Override
    public void onDataSetChanged() {
        if (mDataCursor!= null) {
            mDataCursor.close();
        }

        final long token = Binder.clearCallingIdentity();
        try {

            mDataCursor = mContext.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                null,
                QuoteColumns.ISCURRENT + " = 1",
                null, null);

        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }
}
