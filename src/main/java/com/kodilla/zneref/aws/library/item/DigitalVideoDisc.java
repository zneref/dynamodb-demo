package com.kodilla.zneref.aws.library.item;

public class DigitalVideoDisc extends Disc {

    public DigitalVideoDisc(int year, String title, String director, int ranking) {
        super(year, title, director, ranking);
    }

    @Override
    public String toString() {
        return "DVD{tile=\"" + getTitle() + "\"" +
                ", director=" + getAuthor() +
                ", year=" + getYear() +
                ", ranking=" + getRanking() + "}";
    }
}
