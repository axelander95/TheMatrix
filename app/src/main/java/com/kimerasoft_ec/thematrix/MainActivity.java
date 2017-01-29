package com.kimerasoft_ec.thematrix;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {
    private static final int MATRIX_CONFIGURATION = 1;
    private static final int INTERCHANGE = 2;
    private static final int COLOR = 3;
    private int[][] matrix;
    private int rows, columns;
    private GridView grvMatrix;
    public static List<MatrixItem> numbers;
    private ItemAdapter adapter;
    private int currentPosition;
    private EditText etYourText;
    private GestureLibrary gestureLibrary;
    private GestureOverlayView vMatrix;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grvMatrix = (GridView) findViewById(R.id.grvMatrix);
        etYourText = (EditText) findViewById(R.id.etYourText);
        gestureLibrary = GestureLibraries.fromRawResource(getApplicationContext(), R.raw.gestures);
        vMatrix = (GestureOverlayView) findViewById(R.id.vMatrix);
        vMatrix.addOnGesturePerformedListener(this);
        rows = 4;
        columns = 4;
        matrix = new int[rows][columns];
        numbers = new ArrayList<>();
        generateRandomMatrix();
        currentPosition = -1;
        if (!gestureLibrary.load())
            finish();
    }

    private void generateRandomMatrix()
    {
        Random random = new Random();
        numbers.clear();
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                matrix[i][j] = random.nextInt(100);
                numbers.add(new MatrixItem(matrix[i][j], i, j));
            }
        }
        grvMatrix.setNumColumns(columns);
        adapter = new ItemAdapter(getApplicationContext(), numbers);
        grvMatrix.setAdapter(adapter);
        grvMatrix.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.inflate(R.menu.matrix_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Intent intent;
                        switch (id)
                        {
                            case R.id.interchange:
                                intent = new Intent(MainActivity.this, InterchangeActivity.class);
                                intent.putExtra(InterchangeActivity.INDEX_PARAM, currentPosition);
                                intent.putExtra(InterchangeActivity.ROWS_PARAM, rows);
                                intent.putExtra(InterchangeActivity.COLUMNS_PARAM, columns);
                                startActivityForResult(intent, INTERCHANGE);
                                break;
                            case R.id.colors:
                                intent = new Intent(MainActivity.this, SetColorsActivity.class);
                                startActivityForResult(intent, COLOR);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });
    }

    private void openConfiguration()
    {
        Intent intent = new Intent(MainActivity.this, MatrixConfiguration.class);
        intent.putExtra(MatrixConfiguration.ROW_DATA, rows);
        intent.putExtra(MatrixConfiguration.COLUMN_DATA, columns);
        startActivityForResult(intent, MATRIX_CONFIGURATION);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.new_matrix), Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.item_exit:
                finish();
                break;
            case R.id.item_matrix_configuration:
                openConfiguration();
                break;
        }
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            int type;
            switch(requestCode)
            {
                case MATRIX_CONFIGURATION:
                    rows = data.getIntExtra(MatrixConfiguration.ROW_DATA, 0);
                    columns = data.getIntExtra(MatrixConfiguration.COLUMN_DATA, 0);
                    matrix = new int[rows][columns];
                    generateRandomMatrix();
                    break;
                case INTERCHANGE:
                    type = data.getIntExtra(InterchangeActivity.TYPE_RESULT, 0);
                    int newIndex = data.getIntExtra(InterchangeActivity.INDEX_PARAM, 0);
                    int pastValue;
                    if (type == InterchangeActivity.ROW)
                    {
                        for (int i = 0; i < rows; i++)
                        {
                            if (i == numbers.get(currentPosition).getRow())
                            {
                                for (int j = 0; j < columns; j++)
                                {
                                    pastValue = matrix[newIndex][j];
                                    matrix[newIndex][j] = matrix[i][j];
                                    matrix[i][j] = pastValue;
                                }
                                break;
                            }
                        }
                    }
                    else
                    {
                        for (int i = 0; i < columns; i++)
                        {
                            if (i == numbers.get(currentPosition).getColumn())
                            {
                                for (int j = 0; j < rows; j++)
                                {
                                    pastValue = matrix[j][newIndex];
                                    matrix[j][newIndex] = matrix[j][i];
                                    matrix[j][i] = pastValue;
                                }
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < rows; i++)
                        for (int j = 0; j < columns; j++)
                        {
                            MatrixItem item = getItem(i, j);
                            if (item != null)
                                item.setValue(matrix[i][j]);
                        }
                    adapter.notifyDataSetChanged();
                    break;
                case COLOR:
                    type = data.getIntExtra(SetColorsActivity.RESULT_TYPE, 0);
                    int backgroundColor = data.getIntExtra(SetColorsActivity.BACKGROUND_COLOR, Color.BLACK);
                    int textColor = data.getIntExtra(SetColorsActivity.TEXT_COLOR, Color.WHITE);
                    if (type == SetColorsActivity.CELL)
                    {
                        numbers.get(currentPosition).setBackgroundColor(backgroundColor);
                        numbers.get(currentPosition).setTextColor(textColor);
                    }
                    else if (type == SetColorsActivity.ROW)
                    {
                        for (int i = 0; i < rows; i++)
                            if (i == numbers.get(currentPosition).getRow())
                            {
                                for (int j = 0; j < columns; j++)
                                {
                                    MatrixItem item = getItem(i, j);
                                    if (item != null)
                                    {
                                        item.setBackgroundColor(backgroundColor);
                                        item.setTextColor(textColor);
                                    }
                                }
                                break;
                            }
                    }
                    else
                    {
                        for (int i = 0; i < columns; i++)
                            if (i == numbers.get(currentPosition).getColumn())
                            {
                                for (int j = 0; j < rows; j++)
                                {
                                    MatrixItem item = getItem(j, i);
                                    if (item != null)
                                    {
                                        item.setBackgroundColor(backgroundColor);
                                        item.setTextColor(textColor);
                                    }
                                }
                                break;
                            }
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private MatrixItem getItem(int row, int column)
    {
        for (MatrixItem item:numbers)
            if (row == item.getRow() && column == item.getColumn())
                return item;
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);

        if (predictions != null && predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String result = predictions.get(0).name;
            etYourText.setText(result);
        }
    }
}
