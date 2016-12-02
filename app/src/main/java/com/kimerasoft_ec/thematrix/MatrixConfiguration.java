package com.kimerasoft_ec.thematrix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MatrixConfiguration extends AppCompatActivity {
    private EditText etRows, etColumns;
    private Button btnAccept, btnCancel;
    public static final String ROW_DATA = "row";
    public static final String COLUMN_DATA = "column";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_configuration);
        etRows = (EditText) findViewById(R.id.etRowsNumber);
        etColumns = (EditText) findViewById(R.id.etColumnsNumber);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        Intent intent = getIntent();
        int rows = intent.getIntExtra(ROW_DATA, 0);
        int columns = intent.getIntExtra(COLUMN_DATA, 0);
        etRows.setText(String.valueOf(rows));
        etColumns.setText(String.valueOf(columns));
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formIsValid())
                {
                    int rows = Integer.parseInt(etRows.getText().toString());
                    int columns = Integer.parseInt(etColumns.getText().toString());
                    if (columns <=10)
                    {
                        Intent intent = getIntent();
                        intent.putExtra(ROW_DATA, rows);
                        intent.putExtra(COLUMN_DATA, columns);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.limit), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private boolean formIsValid()
    {
        if (etRows.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rows_required),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (etColumns.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.columns_required),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Integer.parseInt(etRows.getText().toString()) == 0 || Integer.parseInt(etColumns.getText().toString()) == 0)
        {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
