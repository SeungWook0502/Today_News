package com.example.todaynews;

public class TextItem {
    private String value;
    private float index;
    private int frontSize;
    private int frontColor;

    public int getFrontColor() {
        return frontColor;
    }

    public void setFrontColor(int frontColor) {
        this.frontColor = frontColor;
    }

    public int getFrontSize() {
        return frontSize;
    }

    public void setFrontSize(int frontSize) {
        this.frontSize = frontSize;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }
}
