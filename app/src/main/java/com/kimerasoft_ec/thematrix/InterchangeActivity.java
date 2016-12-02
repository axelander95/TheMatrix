package com.kimerasoft_ec.thematrix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InterchangeActivity extends AppCompatActivity {
    private RadioButton rbtRow, rbtColumn;
    private Button btnAccept, btnCancel;
    private Spinner spnIndex;
    private int index, rows, columns;
    public static final String ROWS_PARAM = "rows";
    public static final String COLUMNS_PARAM = "columns";
    public static final String INDEX_PARAM = "index";
    public static final String TYPE_RESULT = "type";
    public static final int ROW = 0;
    public static final int COLUMN = 1;
    private ArrayAdapter<Integer> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interchange);
        rbtRow = (RadioButton) findViewById(R.id.rbtRow);
        rbtColumn = (RadioButton) findViewById(R.id.rbtColumn);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        spnIndex = (Spinner) findViewById(R.id.spnIndex);
        Intent intent = getIntent();
        index = intent.getIntExtra(INDEX_PARAM, 0);
        rows = intent.getIntExtra(ROWS_PARAM, 0);
        columns = intent.getIntExtra(COLUMNS_PARAM, 0);
        setSpinner();
        rbtRow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSpinner();
            }
        });
        rbtColumn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSpinner();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spnIndex.getSelectedItem() != null)
                {
                    int index = (int) spnIndex.getSelectedItem();
                    Intent intent = getIntent();
                    intent.putExtra(INDEX_PARAM, index);
                    intent.putExtra(TYPE_RESULT, (rbtRow.isChecked())?ROW:COLUMN);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_index),
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setSpinner()
    {
        MatrixItem item = MainActivity.numbers.get(index);
        int limit = (rbtColumn.isChecked())?columns:rows;
        spnIndex.setAdapter(null);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < limit; i++)
            if ((rbtColumn.isChecked() && i != item.getColumn()) || (rbtRow.isChecked()) && i != item.getRow())
                list.add(i);
        spnIndex.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,
                list));
    }
}
