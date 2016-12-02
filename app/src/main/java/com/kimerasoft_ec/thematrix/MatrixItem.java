package com.kimerasoft_ec.thematrix;

import android.graphics.Color;

public class MatrixItem {
    private int value, row, column, backgroundColor, textColor;

    public MatrixItem(int value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
        backgroundColor = Color.GRAY;
        textColor = Color.WHITE;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
