package com.kodilla.zneref.aws.library.item;

import java.util.Objects;

public class Book extends LibItem {
    private final int pages;

    public Book(int year, String title, String author, int ranking, int pages) {
        super(year, title, author, ranking);
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPages());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return getPages() == book.getPages();
    }

    @Override
    public String toString() {
        return "BOOK{" +
                "title=" + getTitle() +
                ", author=" + getAuthor() +
                ", pages=" + pages +
                ", ranking=" + getRanking() +
                '}';
    }
}
