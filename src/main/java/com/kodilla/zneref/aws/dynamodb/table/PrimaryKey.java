package com.kodilla.zneref.aws.dynamodb.table;

public class PrimaryKey {
    private final int year; // hash key
    private final String title; // range key

    public PrimaryKey(int year, String title) {
        this.year = year;
        this.title = title;
    }

    public int year() {
        return year;
    }

    public String title() {
        return title;
    }
}
