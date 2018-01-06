package com.kodilla.zneref.aws.library.item;

import com.kodilla.zneref.aws.utils.console.Print;

public class LibItem {
    private final int year;
    private final String title;
    private final String author;
    private int ranking;

    public LibItem(int year, String title, String author, int ranking) {
        this.year = year;
        this.title = title;
        this.author = author;
        this.ranking = ranking;
    }

    public void info() { Print.ln(this); }

    public int getYear() { return year; }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public int getRanking() { return ranking; }

    public void setRanking(int stars) { ranking = stars; }
}
