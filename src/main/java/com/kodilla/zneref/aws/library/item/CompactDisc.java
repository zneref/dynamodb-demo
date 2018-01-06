package com.kodilla.zneref.aws.library.item;

public class CompactDisc extends Disc {
    private final int tracksNum;

    public CompactDisc(int year, String title, String author, int ranking, int tracksNum) {
        super(year, title, author, ranking);
        this.tracksNum = tracksNum;
    }

    public int getTracksNum() {
        return tracksNum;
    }

    @Override
    public String toString() {
        return "CD{tile=\"" + getTitle() + "\"" +
                ", author=" + getAuthor() +
                ", year=" + getYear() +
                ", tracks=" + getTracksNum() +
                ", ranking=" + getRanking() + "}";
    }
}
