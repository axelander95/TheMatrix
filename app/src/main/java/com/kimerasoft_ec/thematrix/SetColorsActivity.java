package com.kimerasoft_ec.thematrix;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SetColorsActivity extends AppCompatActivity {
    private Button btnAccept, btnCancel;
    private Spinner spnBackground, spnText;
    private RadioButton rbtRow, rbtColumn, rbtCell;
    public static final String RESULT_TYPE = "type";
    public static final String BACKGROUND_COLOR = "bg";
    public static final String TEXT_COLOR = "txt";
    public static final int ROW = 0;
    public static final int COLUMN = 1;
    public static final int CELL = 2;
    private static final ArrayList<Integer> AVAILABLE_COLORS = new ArrayList<Integer>(){
        {
            add(Color.BLACK);
            add(Color.BLUE);
            add(Color.GRAY);
            add(Color.GREEN);
            add(Color.MAGENTA);
            add(Color.RED);
            add(Color.WHITE);
            add(Color.TRANSPARENT);
            add(Color.YELLOW);
        }
    };
    public static ArrayList<String> colorsNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_colors);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        spnBackground = (Spinner) findViewById(R.id.spnBackgroundColor);
        spnText = (Spinner) findViewById(R.id.spnTextColor);
        rbtRow = (RadioButton) findViewById(R.id.rbtRow);
        rbtColumn = (RadioButton) findViewById(R.id.rbtColumn);
        rbtCell = (RadioButton) findViewById(R.id.rbtCell);
        colorsNames = new ArrayList<String>(){{
            add(getResources().getString(R.string.black));
            add(getResources().getString(R.string.blue));
            add(getResources().getString(R.string.gray));
            add(getResources().getString(R.string.green));
            add(getResources().getString(R.string.magenta));
            add(getResources().getString(R.string.red));
            add(getResources().getString(R.string.white));
            add(getResources().getString(R.string.transparent));
            add(getResources().getString(R.string.yellow));
        }};
        spnBackground.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                colorsNames));
        spnText.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                colorsNames));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formIsValid())
                {
                    Intent intent = getIntent();
                    intent.putExtra(RESULT_TYPE, (rbtRow.isChecked())?ROW:((rbtColumn.isChecked())?COLUMN:CELL) );
                    intent.putExtra(BACKGROUND_COLOR, AVAILABLE_COLORS.get(spnBackground.getSelectedItemPosition()));
                    intent.putExtra(TEXT_COLOR, AVAILABLE_COLORS.get(spnText.getSelectedItemPosition()));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    private boolean formIsValid()
    {
        if (spnBackground.getSelectedItem() == null)
        {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.background_color_required),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (spnText.getSelectedItem() == null)
        {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_color_required),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
