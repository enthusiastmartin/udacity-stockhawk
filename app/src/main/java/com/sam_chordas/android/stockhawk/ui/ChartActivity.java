package com.sam_chordas.android.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.historyData.HistoryContract;
import com.sam_chordas.android.stockhawk.historyData.HistoryDbHelper;

import java.util.ArrayList;

/**
 * Created by martin on 6/9/16.
 */
public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String symbol = getIntent().getExtras().getString("symbol");

        setContentView(R.layout.activity_graph);

        HistoryDbHelper dbHelper = new HistoryDbHelper(getApplicationContext());

        String[] projection = {
            HistoryContract.QuoteEntry.COLUMN_QUoTE_VALUE,
            HistoryContract.QuoteEntry.COLUMN_QUoTE_TIMESTAMP};

        String sortOrder =
            HistoryContract.QuoteEntry.COLUMN_QUoTE_TIMESTAMP+ " ASC";


        Cursor c = dbHelper.getReadableDatabase().query(HistoryContract.QuoteEntry.TABLE_NAME,
            projection,
            HistoryContract.QuoteEntry.COLUMN_QUoTE_SYMBOL+"=?",
            new String[]{symbol},null,null,sortOrder);

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        c.moveToFirst();
        int idx = 0;
        while (c.moveToNext()){
            float val = c.getFloat(c.getColumnIndex(HistoryContract.QuoteEntry.COLUMN_QUoTE_VALUE));
            String str = c.getString(c.getColumnIndex(HistoryContract.QuoteEntry
                .COLUMN_QUoTE_TIMESTAMP));
            entries.add(new Entry(val, idx));
            labels.add(str.substring(0,11));
            ++idx;
        }

        LineChart lineChart = (LineChart) findViewById(R.id.graph);
        LineDataSet dataset = new LineDataSet(entries, symbol);
        dataset.setDrawCubic(true);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        LineData data = new LineData(labels, dataset);
        lineChart.setData(data);
    }
}
